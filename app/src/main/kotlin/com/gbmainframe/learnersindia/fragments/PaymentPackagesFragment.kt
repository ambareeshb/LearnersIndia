package com.gbmainframe.learnersindia.fragments

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.gbmainframe.learnersindia.R
import com.gbmainframe.learnersindia.adapters.PaymentPackagesAdapter
import com.gbmainframe.learnersindia.models.PaymentPackage
import com.gbmainframe.learnersindia.utils.ApiInterface
import com.gbmainframe.learnersindia.utils.RetrofitUtils
import com.gbmainframe.learnersindia.utils.sharedPrefManager
import com.payumoney.core.PayUmoneySdkInitializer
import com.payumoney.sdkui.ui.utils.PayUmoneyFlowManager
import kotlinx.android.synthetic.main.payment_package_list.*
import kotlinx.android.synthetic.main.simple_toolbar.*
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

/**
 * Created by ambareeshb on 13/04/18.
 */
class PaymentPackagesFragment : Fragment() {
    companion object {
        const val TAG = "PAYMENT_PACKAGE"
        var selectedPackage : PaymentPackage? = null
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
            inflater.inflate(R.layout.payment_package_list, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        backButton.visibility = View.GONE

        progress.visibility = View.VISIBLE
        RetrofitUtils.initRetrofit(ApiInterface::class.java)
                .getPackages()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    progress.visibility = View.GONE
                    recyclerPaymentPackages.layoutManager =
                            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                    recyclerPaymentPackages.adapter = PaymentPackagesAdapter(it) {

                        paymentPackage ->
                        activity?.let {
                            val user = sharedPrefManager.getUser(it)
                            progress.visibility = View.VISIBLE
                            RetrofitUtils.initRetrofit(ApiInterface::class.java).generatePayuHash(user.tocken, paymentPackage.package_id)
                                    .subscribeOn(Schedulers.io())
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .subscribe({
                                        progress.visibility = View.GONE
                                        if (it.response_type == getString(R.string.response_type_error)) {
                                            Snackbar.make(view, it.response_text, Snackbar.LENGTH_SHORT).show()
                                            return@subscribe
                                        }
                                        val paymentParam = PayUmoneySdkInitializer.PaymentParam.Builder()
                                                .setTxnId(it.txnId)
                                                .setPhone(it.phone)
                                                .setProductName(it.productName)
                                                .setFirstName(it.firstName)
                                                .setAmount(it.amount.toDouble())
                                                .setEmail(it.email)
                                                .setsUrl("https://www.payumoney.com/mobileapp/payumoney/success.php")
                                                .setfUrl("https://www.payumoney.com/mobileapp/payumoney/failure.php")
                                                .setUdf1(it.udf1)
                                                .setUdf2(it.udf2)
                                                .setUdf3(it.udf3)
                                                .setUdf4(it.udf4)
                                                .setUdf5(it.udf5)
                                                .setIsDebug(false)
                                                .setKey(it.key)
                                                .setMerchantId(getString(R.string.merchant_id))
                                                .build()

                                        //Caching selected package statically.
                                        selectedPackage = paymentPackage
                                        paymentParam.setMerchantHash(it.response_data.result)
                                        PayUmoneyFlowManager.startPayUMoneyFlow(paymentParam,
                                                activity, R.style.PaymentGatewayTheme, false)
                                    },
                                            {
                                                progress.visibility = View.GONE
                                                it.printStackTrace()
                                            })
                        }

                    }
                }, {
                    Snackbar.make(view, getString(R.string.something_went_wrong), Snackbar.LENGTH_SHORT).show()
                    it.printStackTrace()
                })
    }
}
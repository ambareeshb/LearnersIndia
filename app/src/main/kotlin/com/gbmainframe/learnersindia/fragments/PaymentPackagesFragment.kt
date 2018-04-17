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
import com.gbmainframe.learnersindia.utils.ApiInterface
import com.gbmainframe.learnersindia.utils.RetrofitUtils
import kotlinx.android.synthetic.main.payment_package_list.*
import kotlinx.android.synthetic.main.simple_toolbar.*
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

/**
 * Created by ambareeshb on 13/04/18.
 */
class PaymentPackagesFragment : Fragment() {

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
                    recyclerPaymentPackages.adapter = PaymentPackagesAdapter(it)
                }, {
                    Snackbar.make(view, getString(R.string.something_went_wrong), Snackbar.LENGTH_SHORT).show()
                    it.printStackTrace()
                })
    }
}
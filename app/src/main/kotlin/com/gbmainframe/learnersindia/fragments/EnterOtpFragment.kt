package com.gbmainframe.learnersindia.fragments

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.gbmainframe.learnersindia.R
import com.gbmainframe.learnersindia.activities.Home
import com.gbmainframe.learnersindia.activities.SignIn
import com.gbmainframe.learnersindia.utils.ApiInterface
import com.gbmainframe.learnersindia.utils.RetrofitUtils
import com.gbmainframe.learnersindia.utils.sharedPrefManager
import kotlinx.android.synthetic.main.enter_otp_fragment.*
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

/**
 * Created by ambareeshb on 15/04/18.
 */
class EnterOtpFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
            inflater.inflate(R.layout.enter_otp_fragment, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        otpView.requestFocus()
        otpView.setListener {}


        otpSubmitOtpButton.setOnClickListener {
            activity?.let { activity ->
                val user = sharedPrefManager.getUser(activity)
                RetrofitUtils.initRetrofit(ApiInterface::class.java).submitOtp(user.tocken, otpView.otp)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                {
                                    if (it.response_type == getString(R.string.response_type_error)) {
                                        Snackbar.make(view, it.response_text, Snackbar.LENGTH_SHORT).show()
                                        return@subscribe
                                    }
                                    startActivity(Intent(activity, Home::class.java))
                                    activity.finish()
                                },
                                {
                                    it.printStackTrace()
                                    Snackbar.make(view, "Something went wrong", Snackbar.LENGTH_SHORT).show()
                                })

            }

            resendOtp.setOnClickListener {
                activity?.let { activity ->
                    val user = sharedPrefManager.getUser(activity)
                    RetrofitUtils.initRetrofit(ApiInterface::class.java).resendOtp(user.tocken)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(
                                    {
                                        if (it.response_type == getString(R.string.response_type_error)) {
                                            Snackbar.make(view, it.response_text, Snackbar.LENGTH_SHORT).show()
                                            return@subscribe
                                        }
                                        startActivity(Intent(activity, Home::class.java))
                                        activity.finish()
                                    },
                                    {
                                        it.printStackTrace()
                                        Snackbar.make(view, "Something went wrong", Snackbar.LENGTH_SHORT).show()
                                    })

                }
            }

        }

    }
}

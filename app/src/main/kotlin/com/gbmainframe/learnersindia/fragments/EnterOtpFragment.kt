package com.gbmainframe.learnersindia.fragments

import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.gbmainframe.learnersindia.R
import com.gbmainframe.learnersindia.activities.Home
import com.gbmainframe.learnersindia.activities.SignIn
import com.gbmainframe.learnersindia.models.UserData
import com.gbmainframe.learnersindia.utils.ApiInterface
import com.gbmainframe.learnersindia.utils.RetrofitUtils
import com.gbmainframe.learnersindia.utils.sharedPrefManager
import com.vimeo.networking.model.User
import kotlinx.android.synthetic.main.enter_otp_fragment.*
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

/**
 * Created by ambareeshb on 15/04/18.
 */
class EnterOtpFragment : Fragment() {
    companion object {
        const val RESEND_TIMEOUT = 20000L
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
            inflater.inflate(R.layout.enter_otp_fragment, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        otpView.setListener {}

        otpView.setFocus()


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
                                    activity?.let {
                                        val user = sharedPrefManager.getUser(it)
                                        val verifiedUser = UserData(user.full_name, user.email_id, user.phoneno, user.cls_id,
                                                1, user.syl_id, user.profile_pic ?: "", user.verification_key, user.tocken,
                                                user.address, user.city, user.state, user.dob, user.otpVerified, user.gender, user.paidstatus)
                                        sharedPrefManager.putUserInfo(it, verifiedUser)

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

        resendOtp.setOnClickListener {
            startTimer()
            activity?.let { activity ->
                val user = sharedPrefManager.getUser(activity)
                otpView.otp = ""
                otpView.setFocus()
                RetrofitUtils.initRetrofit(ApiInterface::class.java).resendOtp(user.tocken)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                {
                                    if (it.response_type == getString(R.string.response_type_error)) {
                                        Snackbar.make(view, it.response_text, Snackbar.LENGTH_SHORT).show()
                                        return@subscribe
                                    }
                                },
                                {
                                    it.printStackTrace()
                                    Snackbar.make(view, "Something went wrong", Snackbar.LENGTH_SHORT).show()
                                })

            }
        }

    }

    fun startTimer() {
        resendOtp.setTextColor(ContextCompat.getColor(context!!, android.R.color.darker_gray))
        resendOtp.isEnabled = false
        timer.start()
        timerOtp.visibility = View.VISIBLE
    }


    /**
     * Resend password timer.
     */
    private val timer = object : CountDownTimer(RESEND_TIMEOUT, 1000) {
        override fun onFinish() {
            timerOtp.visibility = View.GONE
            resendOtp.setTextColor(ContextCompat.getColor(context!!, R.color.colorPrimary))
            resendOtp.isEnabled = true
        }

        override fun onTick(millisUntilFinished: Long) {
            timerOtp.text = "${millisUntilFinished / 1000}"
        }
    }

}

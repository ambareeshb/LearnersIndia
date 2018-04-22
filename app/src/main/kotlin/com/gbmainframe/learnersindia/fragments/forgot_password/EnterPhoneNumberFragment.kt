package com.gbmainframe.learnersindia.fragments.forgot_password

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import com.gbmainframe.learnersindia.R
import com.gbmainframe.learnersindia.activities.SignIn
import com.gbmainframe.learnersindia.fragments.EnterOtpFragment
import com.gbmainframe.learnersindia.utils.ApiInterface
import com.gbmainframe.learnersindia.utils.RetrofitUtils
import com.gbmainframe.learnersindia.utils.sharedPrefManager
import kotlinx.android.synthetic.main.enter_phone_number_fragment.*
import kotlinx.android.synthetic.main.toolbar.*
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

class EnterPhoneNumberFragment : Fragment() {
    companion object {
        fun newInstance(): EnterPhoneNumberFragment {
            return EnterPhoneNumberFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
            inflater.inflate(R.layout.enter_phone_number_fragment, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        backButton.setOnClickListener {
            (activity as SignIn).loadSignInFragment()
        }
        toolbarButton.setOnClickListener { (activity as SignIn).loadSignInFragment() }
        buttonSubmit.setOnClickListener { sendForgotPasswordOtp() }
    }

    /**
     * On clicking forgot password send OTP to user phone number.
     */
    private fun sendForgotPasswordOtp() {
        errorText.visibility = View.GONE
        progress.visibility = View.VISIBLE
        activity?.let { activity ->
            val user = sharedPrefManager.getUser(activity)
            RetrofitUtils.initRetrofit(ApiInterface::class.java).sendOtpForgotPassWord(user.tocken, textPhoneNumber.textString())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                            {

                                progress.visibility = View.GONE
                                if (it.response_type == getString(R.string.response_type_error)) {
                                    errorText.visibility = View.VISIBLE
                                    errorText.text = it.response_text
                                    return@subscribe
                                }
                                (activity as SignIn).loadOtpFragment(EnterOtpFragment.Companion.CHOICE.SIGN_IN)
                            },
                            {
                                errorText.visibility = View.VISIBLE
                                errorText.text = "Something went wrong"
                                progress.visibility = View.GONE
                                it.printStackTrace()
                            })
        }
    }

    private fun EditText.textString(): String = this.text.toString()
}

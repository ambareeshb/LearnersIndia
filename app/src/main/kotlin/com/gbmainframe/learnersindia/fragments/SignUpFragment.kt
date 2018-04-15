package com.gbmainframe.learnersindia.fragments

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import com.gbmainframe.learnersindia.R
import com.gbmainframe.learnersindia.activities.SignIn
import com.gbmainframe.learnersindia.models.Board
import com.gbmainframe.learnersindia.models.ClassInfo
import com.gbmainframe.learnersindia.utils.ApiInterface
import com.gbmainframe.learnersindia.utils.RetrofitUtils
import com.gbmainframe.learnersindia.utils.sharedPrefManager
import kotlinx.android.synthetic.main.layout_sign_up.*
import kotlinx.android.synthetic.main.toolbar.*
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

/**
 * Created by ambareeshb on 25/03/18.
 */
class SignUpFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
            inflater.inflate(R.layout.layout_sign_up, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        backButton.setOnClickListener { activity?.onBackPressed() }
        toolbarButton.setOnClickListener { (activity as SignIn).loadSignInFragment() }
        arguments?.let {
            boardText.text = it.getParcelable<Board>(SignIn.BOARD_PARCEL).syllabus
            classText.text = it.getParcelable<ClassInfo>(SignIn.CLASS_PARCEL).grade
        }
        buttonSignUp.setOnClickListener { onSignUpClick() }

    }

    private fun onSignUpClick() {
        when {
            !hasAllFields(fullName.text, emailText.text, passwordText.text, passwordRetype.text, classText.text, boardText.text, textPhoneNumber.text) -> Snackbar.make(view!!, R.string.error_fields_empty, Snackbar.LENGTH_SHORT).show()
            (passwordText.text.toString() == passwordRetype.text.toString()).not() -> Snackbar.make(view!!, R.string.error_retype_password_again, Snackbar.LENGTH_SHORT).show()
            else -> {
                signUpErrorText.text = ""
                //signUpErrorText.visibility = View.GONE
                buttonSignUp.isEnabled = false
                progress.visibility = View.VISIBLE

                val countryCode = "+" + countryPhoneNumber.selectedCountryCode
                val apiInterface = RetrofitUtils.initRetrofit(ApiInterface::class.java)
                arguments?.let {
                    apiInterface.signUp("student",
                            fullName.textString(),
                            emailText.textString(),
                            textPhoneNumber.textString(),
                            passwordText.textString(),
                            it.getParcelable<Board>(SignIn.BOARD_PARCEL).syl_id,
                            it.getParcelable<ClassInfo>(SignIn.CLASS_PARCEL).cls_id,
                            countryCode)
                            .flatMap { data ->
                                if (data.response_type == "error") {
                                    signUpErrorText.post {
                                        signUpErrorText.text = data.response_text
                                        signUpErrorText.visibility = View.VISIBLE
                                    }
//                                    Snackbar.make(view!!, data.response_text, Snackbar.LENGTH_LONG).show()
                                }
                                apiInterface.signIn("student", data.email
                                        ?: "", passwordText.textString())
                            }
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe({ data ->
                                buttonSignUp.isEnabled = true
                                progress.visibility = View.GONE

                                if (data.response_type == "error") {
//                                    Snackbar.make(view!!, data.response_text, Snackbar.LENGTH_SHORT).show()
                                    return@subscribe
                                }
                                activity?.let {
                                    sharedPrefManager.putUserInfo(it, data.user_data)
                                    (activity as SignIn).loadOtpFragment()
                                }
                            }, { error ->
                                buttonSignUp.isEnabled = true
                                progress.visibility = View.GONE
                                error.printStackTrace()
                                Snackbar.make(view!!, "Something went wrong", Snackbar.LENGTH_SHORT).show()
                            })
                }
            }

        }


    }

    private fun EditText.textString(): String = this.text.toString()

    private fun hasAllFields(vararg text: CharSequence) = text.none {
        it.isEmpty()
    }

}
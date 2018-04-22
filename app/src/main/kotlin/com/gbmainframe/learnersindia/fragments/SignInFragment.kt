package com.gbmainframe.learnersindia.fragments

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import com.gbmainframe.learnersindia.R
import com.gbmainframe.learnersindia.activities.Home
import com.gbmainframe.learnersindia.activities.SignIn
import com.gbmainframe.learnersindia.utils.ApiInterface
import com.gbmainframe.learnersindia.utils.RetrofitUtils
import com.gbmainframe.learnersindia.utils.sharedPrefManager
import kotlinx.android.synthetic.main.layout_sign_in.*
import kotlinx.android.synthetic.main.toolbar.*
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

/**
 * Created by ambareeshb on 25/03/18.
 */
class SignInFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
            inflater.inflate(R.layout.layout_sign_in, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        toolbarButton.apply {
            toolbarButton.setText(R.string.sign_up)
            setOnClickListener { (activity as SignIn).loadSelectBoardFragment() }
        }
        forgotPassword.setOnClickListener {
            (activity as SignIn).loadEnterPhoneNumberFragment()
        }
        password.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
        backButton.visibility = View.GONE

        buttonSignIn.setOnClickListener {
            it.isEnabled = false
            progress.visibility = View.VISIBLE

            loginError.text = ""
            RetrofitUtils.initRetrofit(ApiInterface::class.java).signIn("student",
                    userName.textString(),
                    password.textString())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({ data ->
                        it.isEnabled = true
                        progress.visibility = View.GONE

                        if (data.response_type == "error") {
                            loginError.text = data.response_text
                            return@subscribe
                        }
                        activity?.let {
                            sharedPrefManager.putUserInfo(it, data.user_data)
                            if (data.user_data.verified == 0) {
                                (activity as SignIn).loadOtpFragment(EnterOtpFragment.Companion.CHOICE.HOME)
                                return@subscribe
                            }
                            startActivity(Intent(activity, Home::class.java))
                            activity?.finish()
                        }
                    }, { error ->
                        it.isEnabled = true
                        progress.visibility = View.GONE

                        error.printStackTrace()
                        Snackbar.make(view, "something went wrong", Snackbar.LENGTH_SHORT).show()
                    })
        }

    }

    private fun EditText.textString(): String = this.text.toString()
}
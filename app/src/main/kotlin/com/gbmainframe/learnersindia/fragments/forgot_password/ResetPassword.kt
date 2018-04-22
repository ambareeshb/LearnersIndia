package com.gbmainframe.learnersindia.fragments.forgot_password

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import com.gbmainframe.learnersindia.R
import com.gbmainframe.learnersindia.activities.SignIn
import com.gbmainframe.learnersindia.utils.ApiInterface
import com.gbmainframe.learnersindia.utils.RetrofitUtils
import com.gbmainframe.learnersindia.utils.sharedPrefManager
import kotlinx.android.synthetic.main.layout_reset_password.*
import kotlinx.android.synthetic.main.toolbar.*
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers


class ResetPassword : Fragment() {
    companion object {

        fun newInstance(): ResetPassword {
            return ResetPassword()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
            inflater.inflate(R.layout.layout_reset_password, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        backButton.setOnClickListener {
            (activity as SignIn).loadSignInFragment()
        }
        toolbarButton.setOnClickListener { (activity as SignIn).loadSignInFragment() }

        resetPassword.setOnClickListener {

            when {
                !hasAllFields(passwordRetype.textString(), passwordText.toString()) ->
                    Snackbar.make(view, "Please type the password", Snackbar.LENGTH_SHORT).show()
                passwordRetype.textString() != passwordText.textString() ->
                    Snackbar.make(view, "The password do not match", Snackbar.LENGTH_SHORT).show()
                else ->
                    resetPassword.setOnClickListener {

                        progress.visibility = View.VISIBLE
                        activity?.let { activity ->
                            val user = sharedPrefManager.getUser(activity)
                            RetrofitUtils.initRetrofit(ApiInterface::class.java).resetPassword(user.tocken, passwordText.text.toString())
                                    .subscribeOn(Schedulers.io())
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .subscribe(
                                            {
                                                progress.visibility = View.GONE
                                                if (it.response_type == getString(R.string.response_type_error)) {
                                                    Snackbar.make(view!!, it.response_text, Snackbar.LENGTH_SHORT).show()
                                                    return@subscribe
                                                }

                                                Toast.makeText(context, it.response_text, Toast.LENGTH_SHORT).show()
                                                startActivity(Intent(activity, SignIn::class.java))
                                                activity.finish()
                                            },
                                            {
                                                progress.visibility = View.GONE
                                                it.printStackTrace()
                                                Snackbar.make(view!!, "Something went wrong", Snackbar.LENGTH_SHORT).show()
                                            })
                        }
                    }
            }
        }

    }

    private fun EditText.textString(): String = this.text.toString()
    private fun hasAllFields(vararg text: CharSequence) = text.none {
        it.isEmpty()
    }
}
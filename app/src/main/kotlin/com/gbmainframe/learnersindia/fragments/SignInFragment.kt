package com.gbmainframe.learnersindia.fragments

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import com.gbmainframe.learnersindia.R
import com.gbmainframe.learnersindia.activities.Home
import com.gbmainframe.learnersindia.activities.SignIn
import com.gbmainframe.learnersindia.utils.ApiInterface
import com.gbmainframe.learnersindia.utils.RetrofitUtils
import kotlinx.android.synthetic.main.layout_sign_in.*
import kotlinx.android.synthetic.main.toolbar.*
import rx.Scheduler
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

        buttonSignIn.setOnClickListener {
            RetrofitUtils.initRetrofit(ApiInterface::class.java).signIn("student",
                    userName.textString(),
                    password.textString())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({ data ->
                        if (data.response_type == "error") {
                            Snackbar.make(view, data.response_text, Snackbar.LENGTH_SHORT).show()
                            return@subscribe
                        }
                        startActivity(Intent(activity, Home::class.java))
                        activity?.finish()
                    }, { error ->
                        error.printStackTrace()
                        Snackbar.make(view, "something went wrong", Snackbar.LENGTH_SHORT).show()
                    })
        }

    }

    private fun EditText.textString(): String = this.text.toString()
}
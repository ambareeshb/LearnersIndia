package com.gbmainframe.learnersindia.fragments

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.gbmainframe.learnersindia.R
import com.gbmainframe.learnersindia.models.UserResponse
import com.gbmainframe.learnersindia.utils.ApiInterface
import com.gbmainframe.learnersindia.utils.RetrofitUtils
import com.gbmainframe.learnersindia.utils.sharedPrefManager
import kotlinx.android.synthetic.main.layout_view_profile.*
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

/**
 * Created by ambareeshb on 13/04/18.
 */
class ShowProfile : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
            inflater.inflate(R.layout.layout_view_profile, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        profileView.visibility = View.INVISIBLE
        progressProfile.visibility = View.VISIBLE
        activity?.let {
            val user = sharedPrefManager.getUser(it)
            RetrofitUtils.initRetrofit(ApiInterface::class.java)
                    .getUserProfile("student", user.tocken)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({
                        if (it.response_type == getString(R.string.response_type_error)) {
                            Snackbar.make(view, it.response_text, Snackbar.LENGTH_SHORT).show()
                            return@subscribe
                        }

                        updateProfile(it)
                        profileView.visibility = View.VISIBLE
                        progressProfile.visibility = View.GONE

                    }, {
                        it.printStackTrace()
                        Snackbar.make(view, "Something went wrong", Snackbar.LENGTH_SHORT).show()
                    }
                    )
        }
    }

    private fun updateProfile(user: UserResponse?) {
        user?.let {
            textProfileEmail.text = it.user_data.email_id
            textProfileAddress.text = "${it.user_data.address}\n${it.user_data.city}\n${it.user_data.state}"
            textProfilePhoneNumber.text = it.user_data.phoneno
            textProfileDob.text = it.user_data.dob
            name.text = it.user_data.full_name
            val board = when (it.user_data.syl_id.toInt()) {
                1 -> "CBSE"
                3 -> "Kerala"
                4 -> "TN"
                else -> "CBSE"
            }
            className.text = ("$board - Class ${it.user_data.cls_id}")
        }
    }
}
package com.gbmainframe.learnersindia.fragments.game

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.gbmainframe.learnersindia.R
import com.gbmainframe.learnersindia.activities.GameActivity
import com.gbmainframe.learnersindia.utils.ApiInterface
import com.gbmainframe.learnersindia.utils.RetrofitUtils
import com.gbmainframe.learnersindia.utils.sharedPrefManager
import kotlinx.android.synthetic.main.layout_game_start.*
import kotlinx.android.synthetic.main.simple_toolbar.*
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

/**
 * Created by ambareeshb on 10/04/18.
 */
class GameStartFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
            inflater.inflate(R.layout.layout_game_start, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        toolbarTitle.text = getString(R.string.game)
        backButton.setOnClickListener {
            (activity as GameActivity).onBackPressed()
        }
        buttonStartGame.setOnClickListener {
//            checkPaymentStatusAndProceed()
            (activity as GameActivity).loadGameQuestionFragment()
        }
    }

    fun checkPaymentStatusAndProceed() {
        activity?.let {
            val user = sharedPrefManager.getUser(it)
            RetrofitUtils.initRetrofit(ApiInterface::class.java)
                    .checkPaidStatus(user.tocken)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({
                        if (it.response_type == getString(R.string.response_type_error)) {
                            (activity as GameActivity).loadGoToPremiumFragment()
                            return@subscribe
                        }
                        if (it.payment_status == "unpaid")
                            (activity as GameActivity).loadGoToPremiumFragment()
                        else
                            (activity as GameActivity).loadGameQuestionFragment()
                    },
                            { error ->
                                error.printStackTrace()
                                Snackbar.make(view!!, "Something went wrong", Snackbar.LENGTH_SHORT).show()
                            }
                    )
        }
    }
}

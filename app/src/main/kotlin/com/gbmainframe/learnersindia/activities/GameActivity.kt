package com.gbmainframe.learnersindia.activities

import android.app.Activity
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.FragmentManager
import android.support.v7.app.AlertDialog
import android.util.Log
import com.gbmainframe.learnersindia.R
import com.gbmainframe.learnersindia.fragments.GoToPremiumFragment
import com.gbmainframe.learnersindia.fragments.PaymentPackagesFragment
import com.gbmainframe.learnersindia.fragments.game.GameFinishFragment
import com.gbmainframe.learnersindia.fragments.game.GameLevelFragment
import com.gbmainframe.learnersindia.fragments.game.GameQuestionFragment
import com.gbmainframe.learnersindia.fragments.game.GameStartFragment
import com.gbmainframe.learnersindia.models.PayUResponse
import com.gbmainframe.learnersindia.utils.FragmentUtils
import com.gbmainframe.learnersindia.utils.PaymentApiCall
import com.gbmainframe.learnersindia.utils.sharedPrefManager
import com.payumoney.core.entity.TransactionResponse
import com.payumoney.sdkui.ui.utils.PayUmoneyFlowManager

class GameActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)
        loadGameStartFragment()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == PayUmoneyFlowManager.REQUEST_CODE_PAYMENT && resultCode == Activity.RESULT_OK
                && data != null) {
            val transactionResponse = data.getParcelableExtra<TransactionResponse>(PayUmoneyFlowManager.INTENT_EXTRA_TRANSACTION_RESPONSE)
            transactionResponse?.let {
                if (it.payuResponse != null && it.transactionStatus == TransactionResponse.TransactionStatus.SUCCESSFUL) {
                    //Payment success
                    val user = sharedPrefManager.getUser(this)
                    PaymentPackagesFragment.selectedPackage?.let { paymentPackage ->
                        val response = PayUResponse(response_type = "success",
                                response_date = it.payuResponse,
                                token = user.tocken,
                                package_id = paymentPackage.package_id)

                        PaymentApiCall.submitPaymentResponse(this, response = response)
                    }
                    sharedPrefManager.paymentSuccess(this)
                    Log.d(PaymentPackagesFragment.TAG, "Transaction success ${it.getPayuResponse()}")

                } else {
                    //Payment failure
                    val user = sharedPrefManager.getUser(this)
                    PaymentPackagesFragment.selectedPackage?.let { paymentPackage ->
                        val response = PayUResponse(response_type = "success",
                                response_date = it.payuResponse,
                                token = user.tocken,
                                package_id = paymentPackage.package_id)
                        PaymentApiCall.submitPaymentResponse(this, response = response)
                    }
                    Log.d(PaymentPackagesFragment.TAG, "Transaction failure")
                }
            }

        }
    }

    fun loadGoToPremiumFragment() {
        FragmentUtils(supportFragmentManager).beginTransaction()
                .addToBackStack(true)
                .replace(R.id.fragmentContainer, GoToPremiumFragment()).commit()
    }

    fun loadGameFinish(level: Int) {
        FragmentUtils(supportFragmentManager).beginTransaction()
                .replace(R.id.fragmentContainer, GameFinishFragment.newInstance(level))
                .commit()
    }

    fun loadGameStartFragment() {
        FragmentUtils(supportFragmentManager).beginTransaction().replace(R.id.fragmentContainer, GameStartFragment())
                .commit()
    }

    fun loadGameLevelFragment() {
        FragmentUtils(supportFragmentManager).beginTransaction().replace(R.id.fragmentContainer, GameLevelFragment())
                .commit()
    }

    fun loadGameQuestionFragment() {
        FragmentUtils(supportFragmentManager).beginTransaction().replace(R.id.fragmentContainer, GameQuestionFragment())
                .commit()
    }


    override fun onBackPressed() {
        AlertDialog.Builder(this, R.style.Base_Theme_AppCompat_Light_Dialog).setTitle("Exit Game").setMessage(R.string.game_quit)
                .setPositiveButton(R.string.yes, { _, _ ->
                    this?.let {
                        sharedPrefManager.logoutUser(it)
                        super.onBackPressed()
                        this?.finish()
                    }
                })
                .setNegativeButton(R.string.no, { _, _ ->
                    //Do noting means to dismiss the dialog.
                }).show()
    }

}

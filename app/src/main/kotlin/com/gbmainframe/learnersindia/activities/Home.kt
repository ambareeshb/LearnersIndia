package com.gbmainframe.learnersindia.activities

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.FragmentManager
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.view.WindowManager
import com.gbmainframe.learnersindia.R
import com.gbmainframe.learnersindia.activities.SignIn.Companion.SIGN_UP_FLOW_INIT
import com.gbmainframe.learnersindia.fragments.*
import com.gbmainframe.learnersindia.models.PayUResponse
import com.gbmainframe.learnersindia.utils.FragmentUtils
import com.gbmainframe.learnersindia.utils.PaymentApiCall
import com.gbmainframe.learnersindia.utils.sharedPrefManager
import com.payumoney.core.entity.TransactionResponse
import com.payumoney.sdkui.ui.utils.PayUmoneyFlowManager
import kotlinx.android.synthetic.main.activity_home.*


class Home : AppCompatActivity() {

    companion object {
        const val TAG = "CommonFragmentTag"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE)
        setContentView(R.layout.activity_home)
        homeBottomNavigation.selectedItemId = R.id.feed

        if (sharedPrefManager.getUser(this).verified == 0) {
            homeBottomNavigation.visibility = View.GONE
            loadOtpFragment(EnterOtpFragment.Companion.CHOICE.HOME)
            return
        }
        loadHomeFeedFragment()

        /**
         * Bottom navigation click listeners.
         */
        homeBottomNavigation.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.feed -> {
                    loadHomeFeedFragment()
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.profile -> {
                    loadProfileFragment()
                    return@setOnNavigationItemSelectedListener true
                }
//                R.id.premium -> {
//                    loadPremiumFragment()
//                    return@setOnNavigationItemSelectedListener true
//                }
                R.id.ask -> {
                    loadQuestionsListFragment()
                    true
                }
                else -> {
                    Snackbar.make(layoutRelative, "Under construction", Snackbar.LENGTH_SHORT).show()
                    true
                }
            }
        }
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
                                response_data = it.payuResponse.replace("\\", ""),
                                token = user.tocken,
                                package_id = paymentPackage.package_id)

                        PaymentApiCall.submitPaymentResponse(this, response = response)
                    }
                    sharedPrefManager.paymentSuccess(this)

                    startActivity(Intent(this, Home::class.java))

                } else {
                    //Payment failure
                    val user = sharedPrefManager.getUser(this)
                    PaymentPackagesFragment.selectedPackage?.let { paymentPackage ->
                        val response = PayUResponse(response_type = "failure",
                                response_data = it.payuResponse,
                                token = user.tocken,
                                package_id = paymentPackage.package_id)
                        PaymentApiCall.submitPaymentResponse(this, response = response)
                    }
                    Log.d(TAG, "Transaction failure")
                }
            }

        }
    }

    /**
     * Fragment having search option and recommended search.
     */
    fun loadHomeFeedFragment() {
        if (supportFragmentManager.findFragmentByTag(HomeFragment.FRAGMENT_TAG) == null) {
            FragmentUtils(supportFragmentManager).beginTransaction().replaceWithTag(R.id.fragmentContainer, HomeFragment(), HomeFragment.FRAGMENT_TAG)
                    .commit()
        }
        supportFragmentManager.popBackStack(TAG, FragmentManager.POP_BACK_STACK_INCLUSIVE)
    }

    /**
     * Load user profile fragment.
     */
    private fun loadProfileFragment() {
        supportFragmentManager.popBackStack(TAG, FragmentManager.POP_BACK_STACK_INCLUSIVE)
        FragmentUtils(supportFragmentManager).beginTransaction().replace(R.id.fragmentContainer, ProfileFragment()).commit()
    }

    /**
     * Load package list fragment.
     */
    fun loadPackageListFragment() {
        supportFragmentManager.popBackStack(TAG, FragmentManager.POP_BACK_STACK_INCLUSIVE)
        FragmentUtils(supportFragmentManager).beginTransaction().replace(R.id.fragmentContainer, PaymentPackagesFragment()).commit()
    }

    /**
     * Load go to premium fragment.
     */
    fun loadGoToPremiumFragment() {
        supportFragmentManager.popBackStack(TAG, FragmentManager.POP_BACK_STACK_INCLUSIVE)
        FragmentUtils(supportFragmentManager).beginTransaction()
                .addToBackStack(true)
                .replace(R.id.fragmentContainer, GoToPremiumFragment()).commit()
    }

    /**
     * Load user profile fragment.
     */
    fun loadShowProfileFragment() {
//        supportFragmentManager.popBackStack(TAG, FragmentManager.POP_BACK_STACK_INCLUSIVE)
        FragmentUtils(supportFragmentManager).beginTransaction().replace(R.id.fragmentContainer, ShowProfile())
                .addToBackStack(true, TAG)
                .commit()
    }

    /**
     * Load user Premium features fragment.
     */
    private fun loadPremiumFragment() {
        supportFragmentManager.popBackStack(TAG, FragmentManager.POP_BACK_STACK_INCLUSIVE)
        FragmentUtils(supportFragmentManager).beginTransaction().replace(R.id.fragmentContainer, PremiumFragment()).commit()
    }

    /**
     * Load chapter list fragment.
     */
    fun loadChapterListFragment(chapterChoice: ChaptersFragment.Companion.CHAPTER) {
        supportFragmentManager.popBackStack(TAG, FragmentManager.POP_BACK_STACK_INCLUSIVE)
        val bundle = Bundle()
        bundle.putInt(ChaptersFragment.CHAPTER_BUNDLE, chapterChoice.ordinal)
        val fragment = ChaptersFragment()
        fragment.arguments = bundle
        FragmentUtils(supportFragmentManager).beginTransaction()
                .replace(R.id.fragmentContainer, fragment)
                .addToBackStack(true, TAG)
                .commit()
    }

    /**
     * Load sign up OTP fragment.
     */
    fun loadOtpFragment(choice: EnterOtpFragment.Companion.CHOICE) {
        supportFragmentManager.popBackStack(SIGN_UP_FLOW_INIT, FragmentManager.POP_BACK_STACK_INCLUSIVE)
        FragmentUtils(supportFragmentManager).beginTransaction()
                .replace(R.id.fragmentContainer, EnterOtpFragment.newInstance(choice))
                .commit()
    }

    /**
     * Add asks question fragment to container.
     */
    fun loadAskQuestionFragment(addToBackStack: Boolean) {
        supportFragmentManager.popBackStack(TAG, FragmentManager.POP_BACK_STACK_INCLUSIVE)
        FragmentUtils(supportFragmentManager).beginTransaction()
                .replace(R.id.fragmentContainer, AskQuestionFragment())
                .addToBackStack(addToBackStack, TAG)
                .commit()
    }

    /**
     * Add asks question fragment to container.
     */
    fun loadVideoListFragment(chapterTitle: String, chapterId: Int) {
//        supportFragmentManager.popBackStack(TAG, FragmentManager.POP_BACK_STACK_INCLUSIVE)
        FragmentUtils(supportFragmentManager).beginTransaction()
                .replace(R.id.fragmentContainer, VideoListFragment.newInstance(chapterId, chapterTitle))
                .addToBackStack(true, TAG)
                .commit()
    }

    /**
     * Add asks question fragment to container.
     */
    fun loadExerciseListFragment(chapterTitle: String, chapterId: Int) {
//        supportFragmentManager.popBackStack(TAG, FragmentManager.POP_BACK_STACK_INCLUSIVE)
        FragmentUtils(supportFragmentManager).beginTransaction()
                .replace(R.id.fragmentContainer, ExerciseListFragment.newInstance(chapterTitle, chapterId))
                .addToBackStack(true, TAG)
                .commit()
    }

    /**
     * Load question list fragment.
     */
    fun loadQuestionsListFragment() {
        supportFragmentManager.popBackStack(TAG, FragmentManager.POP_BACK_STACK_INCLUSIVE)
        FragmentUtils(supportFragmentManager).beginTransaction()
                .replace(R.id.fragmentContainer, QuestionListFragment())
                .commit()
    }

    /**
     * Load answer list fragment.
     */
    fun loadAnswerListFragment(questionId: Int) {
        AnswerListFragment().apply {
            supportFragmentManager.popBackStack(TAG, FragmentManager.POP_BACK_STACK_INCLUSIVE)
            arguments = Bundle().apply {
                putInt(AnswerListFragment.ANSWER_BUNDLE_KEY, questionId)
            }
            FragmentUtils(supportFragmentManager).beginTransaction()
                    .replace(R.id.fragmentContainer, this)
                    .addToBackStack(true, TAG)
                    .commit()
        }
    }

    /**
     * Pop the latest entry from back stack.
     */
    fun popBackStack() {
        supportFragmentManager.popBackStack()
    }

}

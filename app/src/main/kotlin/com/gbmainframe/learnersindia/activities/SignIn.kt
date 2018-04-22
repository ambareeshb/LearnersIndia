package com.gbmainframe.learnersindia.activities

import android.app.FragmentManager
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.WindowManager
import com.gbmainframe.learnersindia.R
import com.gbmainframe.learnersindia.constants.Constants
import com.gbmainframe.learnersindia.fragments.*
import com.gbmainframe.learnersindia.fragments.forgot_password.EnterPhoneNumberFragment
import com.gbmainframe.learnersindia.fragments.forgot_password.ResetPassword
import com.gbmainframe.learnersindia.models.Board
import com.gbmainframe.learnersindia.models.ClassInfo
import com.gbmainframe.learnersindia.utils.FragmentUtils

class SignIn : AppCompatActivity() {
    companion object {
        const val LOGIN_DECISION_KEY = "LOGIN_DEC"
        const val BOARD_PARCEL = "BOARD_KEY"
        const val CLASS_PARCEL = "CLASS_KEY"
        const val SIGN_UP_FLOW_INIT = "SIGNUP_INIT"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE)
        setContentView(R.layout.activity_sign_in)
        loadTutorialView()
    }


    private fun loadTutorialView() {
        FragmentUtils(supportFragmentManager).beginTransaction().replace(R.id.fragmentContainer, TutorialFragment()).commit()
    }

    fun loadSelectRoleFragment(decision: Constants.LOGIN_DECISION) {
        val bundle = Bundle()
        bundle.putInt(LOGIN_DECISION_KEY, decision.ordinal)
        val fragment = SelectRoleFragment()
        fragment.arguments = bundle
        FragmentUtils(supportFragmentManager).beginTransaction().replace(R.id.fragmentContainer, fragment)
                .commit()
    }

    fun loadSignInFragment() {
        supportFragmentManager.popBackStack(SIGN_UP_FLOW_INIT, FragmentManager.POP_BACK_STACK_INCLUSIVE)
        FragmentUtils(supportFragmentManager).beginTransaction().replace(R.id.fragmentContainer, SignInFragment()).commit()
    }

    fun loadOtpFragment(choice: EnterOtpFragment.Companion.CHOICE) {
        supportFragmentManager.popBackStack(SIGN_UP_FLOW_INIT, FragmentManager.POP_BACK_STACK_INCLUSIVE)
        FragmentUtils(supportFragmentManager).beginTransaction()
                .replace(R.id.fragmentContainer, EnterOtpFragment.newInstance(choice))
                .commit()
    }

    fun loadEnterPhoneNumberFragment() {
        supportFragmentManager.popBackStack(SIGN_UP_FLOW_INIT, FragmentManager.POP_BACK_STACK_INCLUSIVE)
        FragmentUtils(supportFragmentManager).beginTransaction()
                .replace(R.id.fragmentContainer, EnterPhoneNumberFragment.newInstance())
                .addToBackStack(true)
                .commit()
    }

    fun loadResetPassword() {
        supportFragmentManager.popBackStack(SIGN_UP_FLOW_INIT, FragmentManager.POP_BACK_STACK_INCLUSIVE)
        FragmentUtils(supportFragmentManager).beginTransaction()
                .replace(R.id.fragmentContainer, ResetPassword.newInstance())
                .addToBackStack(true)
                .commit()
    }

    fun loadSelectBoardFragment() {
        supportFragmentManager.popBackStack(SIGN_UP_FLOW_INIT, FragmentManager.POP_BACK_STACK_INCLUSIVE)
        FragmentUtils(supportFragmentManager).beginTransaction().replace(R.id.fragmentContainer, SelectBoardFragment())
                .addToBackStack(true, SIGN_UP_FLOW_INIT)
                .commit()
    }

    fun loadClassListFragment(board: Board) {
        val bundle = Bundle()
        bundle.putParcelable(BOARD_PARCEL, board)
        val fragment = SelectClassFragment()
        fragment.arguments = bundle
        FragmentUtils(supportFragmentManager).beginTransaction().replace(R.id.fragmentContainer, fragment)
                .addToBackStack(true)
                .commit()

    }

    fun loadSignUpFragment(board: Board, classInfo: ClassInfo) {
        val bundle = Bundle()
        bundle.putParcelable(BOARD_PARCEL, board)
        bundle.putParcelable(CLASS_PARCEL, classInfo)
        val fragment = SignUpFragment()
        fragment.arguments = bundle
        FragmentUtils(supportFragmentManager).beginTransaction().replace(R.id.fragmentContainer, fragment)
                .addToBackStack(true)
                .commit()
    }


}

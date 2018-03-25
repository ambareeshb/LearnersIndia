package com.gbmainframe.learnersindia.activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.gbmainframe.learnersindia.R
import com.gbmainframe.learnersindia.constants.Constants
import com.gbmainframe.learnersindia.fragments.*
import com.gbmainframe.learnersindia.utils.FragmentUtils
import kotlinx.android.synthetic.main.toolbar.*

class SignIn : AppCompatActivity() {
    companion object {
       val LOGIN_DECISION_KEY = "LOGIN_DEC"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)
        loadTutorialView()
    }


    private fun loadTutorialView() {
        FragmentUtils(supportFragmentManager).beginTransaction().replace(R.id.fragmentContainer, TutorialFragment()).commit()
    }

    fun loadSelectRoleFragment(decision : Constants.LOGIN_DECISION) {
        val bundle = Bundle()
        bundle.putInt(LOGIN_DECISION_KEY,decision.ordinal)
        val fragment = SelectRoleFragment()
        fragment.arguments = bundle
        FragmentUtils(supportFragmentManager).beginTransaction().replace(R.id.fragmentContainer, fragment).commit()
    }

    fun loadSignInFragment() {
        FragmentUtils(supportFragmentManager).beginTransaction().replace(R.id.fragmentContainer, SignInFragment()).commit()
    }

    fun toSignUpFragment() {
        FragmentUtils(supportFragmentManager).beginTransaction().replace(R.id.fragmentContainer, SelectBoardFragment())
                .addToBackStack(true)
                .commit()
    }

}

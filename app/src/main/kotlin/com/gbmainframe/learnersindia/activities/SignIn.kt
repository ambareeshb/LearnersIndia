package com.gbmainframe.learnersindia.activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.gbmainframe.learnersindia.R
import com.gbmainframe.learnersindia.fragments.SelectRoleFragment
import com.gbmainframe.learnersindia.fragments.TutorialFragment
import com.gbmainframe.learnersindia.utils.FragmentUtils

class SignIn : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)
        loadTutorialView()
    }

    private fun loadTutorialView() {
        FragmentUtils(supportFragmentManager).beginTransaction().replace(R.id.fragmentContainer, TutorialFragment()).commit()
    }

    fun loadSelectRoleFragment() {
        FragmentUtils(supportFragmentManager).beginTransaction().replace(R.id.fragmentContainer, SelectRoleFragment()).commit()
    }
}

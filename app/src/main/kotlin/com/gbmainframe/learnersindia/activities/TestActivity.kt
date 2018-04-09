package com.gbmainframe.learnersindia.activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.gbmainframe.learnersindia.R
import com.gbmainframe.learnersindia.fragments.TestListFragment
import com.gbmainframe.learnersindia.fragments.TestStartFragment
import com.gbmainframe.learnersindia.models.TestModel
import com.gbmainframe.learnersindia.utils.FragmentUtils

class TestActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)
        loadTestListFragment()
    }

    fun loadTestStartFragment(test: TestModel) {
        FragmentUtils(supportFragmentManager).beginTransaction().replace(R.id.fragmentContainer,
                TestStartFragment.newInstance(test))
                .addToBackStack(true)
                .commit()
    }

    fun loadTestListFragment() {
        FragmentUtils(supportFragmentManager).beginTransaction().replace(R.id.fragmentContainer, TestListFragment())
                .addToBackStack(true)
                .commit()
    }

}

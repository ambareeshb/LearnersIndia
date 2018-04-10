package com.gbmainframe.learnersindia.activities

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v7.app.AppCompatActivity
import com.gbmainframe.learnersindia.R
import com.gbmainframe.learnersindia.fragments.TestFinishedFragment
import com.gbmainframe.learnersindia.fragments.TestListFragment
import com.gbmainframe.learnersindia.fragments.TestQuestionListFragment
import com.gbmainframe.learnersindia.fragments.TestStartFragment
import com.gbmainframe.learnersindia.models.TestModel
import com.gbmainframe.learnersindia.utils.FragmentUtils

class TestActivity : AppCompatActivity() {
    companion object {
        const val BASIC_TAG = "FRAGMENT_TAG"
    }

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
                .addToBackStack(true, BASIC_TAG)
                .commit()
    }

    fun loadTestFinishFragment() {
        popBackStack()
        FragmentUtils(supportFragmentManager).beginTransaction().replace(R.id.fragmentContainer, TestFinishedFragment())
                .addToBackStack(true, BASIC_TAG)
                .commit()
    }

    fun loadTestQuestionsFragment(test: TestModel) {
        FragmentUtils(supportFragmentManager).beginTransaction().replace(R.id.fragmentContainer,
                TestQuestionListFragment.newInstance(test))
                .addToBackStack(true)
                .commit()
    }

    fun popBackStack() {
        supportFragmentManager.popBackStack(BASIC_TAG, FragmentManager.POP_BACK_STACK_INCLUSIVE)
    }
}

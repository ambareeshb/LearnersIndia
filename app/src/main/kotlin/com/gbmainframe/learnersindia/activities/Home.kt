package com.gbmainframe.learnersindia.activities

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.FragmentManager
import android.support.v7.app.AppCompatActivity
import android.view.WindowManager
import com.gbmainframe.learnersindia.R
import com.gbmainframe.learnersindia.fragments.*
import com.gbmainframe.learnersindia.utils.FragmentUtils
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
                R.id.premium -> {
                    loadPremiumFragment()
                    return@setOnNavigationItemSelectedListener true
                }
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
     * Load user Premium features fragment.
     */
    private fun loadPremiumFragment() {
        supportFragmentManager.popBackStack(TAG, FragmentManager.POP_BACK_STACK_INCLUSIVE)
        FragmentUtils(supportFragmentManager).beginTransaction().replace(R.id.fragmentContainer, PremiumFragment()).commit()
    }

    /**
     * Load chapter list fragment.
     */
    fun loadChapterListFragment() {
        supportFragmentManager.popBackStack(TAG, FragmentManager.POP_BACK_STACK_INCLUSIVE)
        FragmentUtils(supportFragmentManager).beginTransaction()
                .replace(R.id.fragmentContainer, ChaptersFragment())
                .addToBackStack(true, TAG)
                .commit()
    }


    /**
     * Add asks question fragment to container.
     */
    fun loadAskQuestionFragment() {
        supportFragmentManager.popBackStack(TAG, FragmentManager.POP_BACK_STACK_INCLUSIVE)
        FragmentUtils(supportFragmentManager).beginTransaction()
                .replace(R.id.fragmentContainer, AskQuestionFragment())
                .addToBackStack(true, TAG)
                .commit()
    }

    /**
     * Load chapter list fragment.
     */
    fun loadQuestionsListFragment() {
        supportFragmentManager.popBackStack(TAG, FragmentManager.POP_BACK_STACK_INCLUSIVE)
        FragmentUtils(supportFragmentManager).beginTransaction()
                .replace(R.id.fragmentContainer, QuestionListFragment())
                .addToBackStack(true, TAG)
                .commit()
    }

    /**
     * Pop the latest entry from back stack.
     */
    fun popBackStack() {
        supportFragmentManager.popBackStack()
    }

}

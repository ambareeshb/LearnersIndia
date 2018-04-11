package com.gbmainframe.learnersindia.activities

import android.os.Bundle
import android.support.v4.app.FragmentManager
import android.support.v7.app.AppCompatActivity
import com.gbmainframe.learnersindia.R
import com.gbmainframe.learnersindia.fragments.*
import com.gbmainframe.learnersindia.models.ChapterModel
import com.gbmainframe.learnersindia.models.TestModel
import com.gbmainframe.learnersindia.utils.FragmentUtils

class TestActivity : AppCompatActivity() {
    companion object {
        const val BASIC_TAG = "FRAGMENT_TAG"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)
        loadChapterListFragment(ChaptersFragment.Companion.CHAPTER.TEST)
    }

    fun loadChapterListFragment(chapterChoice: ChaptersFragment.Companion.CHAPTER) {
        val bundle = Bundle()
        bundle.putInt(ChaptersFragment.CHAPTER_BUNDLE, chapterChoice.ordinal)
        val fragment = ChaptersFragment()
        fragment.arguments = bundle
        FragmentUtils(supportFragmentManager).beginTransaction()
                .replace(R.id.fragmentContainer, fragment)
                .commit()
    }

    fun loadTestStartFragment(chapter: ChapterModel) {
        FragmentUtils(supportFragmentManager).beginTransaction().replace(R.id.fragmentContainer,
                TestStartFragment.newInstance(chapter))
                .addToBackStack(true, BASIC_TAG)
                .commit()
    }

    fun loadTestListFragment() {
        FragmentUtils(supportFragmentManager).beginTransaction().replace(R.id.fragmentContainer, TestListFragment())
                .commit()
    }

    fun loadTestFinishFragment(totalMark: Int) {
        supportFragmentManager.popBackStack()
        FragmentUtils(supportFragmentManager).beginTransaction().replace(R.id.fragmentContainer,
                TestFinishedFragment.newInstance(totalMarks = totalMark))
                .addToBackStack(true)
                .commit()
    }

    fun loadTestQuestionsFragment(chapter: ChapterModel) {
        FragmentUtils(supportFragmentManager).beginTransaction().replace(R.id.fragmentContainer,
                TestQuestionListFragment.newInstance(chapter))
                .addToBackStack(true)
                .commit()
    }

    fun popBackStack() {
        supportFragmentManager.popBackStack(BASIC_TAG, FragmentManager.POP_BACK_STACK_INCLUSIVE)
    }
}

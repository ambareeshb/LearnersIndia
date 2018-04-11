package com.gbmainframe.learnersindia.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.os.bundleOf
import com.gbmainframe.learnersindia.R
import com.gbmainframe.learnersindia.activities.TestActivity
import com.gbmainframe.learnersindia.models.ChapterModel
import com.gbmainframe.learnersindia.models.TestModel
import kotlinx.android.synthetic.main.layout_test_start.*
import kotlinx.android.synthetic.main.simple_toolbar.*

/**
 * Created by ambareeshb on 09/04/18.
 */
class TestStartFragment : Fragment() {
    companion object {
        private const val BUNDLE_TEST_MODEL = "BUNDLE_TEST"
        fun newInstance(chapter: ChapterModel) =
                TestStartFragment().apply {
                    arguments = bundleOf(BUNDLE_TEST_MODEL to chapter)
                }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
            inflater.inflate(R.layout.layout_test_start, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        toolbarTitle.text = "Tests"
        backButton.setOnClickListener {
            (activity as TestActivity).onBackPressed()
        }

        val test = arguments?.getParcelable(BUNDLE_TEST_MODEL) as ChapterModel
//        numberOfQuestions.text = (String.format(getString(R.string.number_of_questions), test.test_total_questions))
        numberOfQuestions.text = ""
        chapterTitle.text = test.chapter

        buttonStartTest.setOnClickListener {
            (activity as TestActivity).loadTestQuestionsFragment(test)
        }
    }


}

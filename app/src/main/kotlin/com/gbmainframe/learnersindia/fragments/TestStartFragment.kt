package com.gbmainframe.learnersindia.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.os.bundleOf
import com.gbmainframe.learnersindia.R
import com.gbmainframe.learnersindia.models.TestModel
import kotlinx.android.synthetic.main.layout_test_start.*

/**
 * Created by ambareeshb on 09/04/18.
 */
class TestStartFragment : Fragment() {
    companion object {
        private const val BUNDLE_TEST_MODEL = "BUNDLE_TEST"
        fun newInstance(test: TestModel) =
                TestStartFragment().apply {
                    arguments = bundleOf(BUNDLE_TEST_MODEL to test)
                }
    }



    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
            inflater.inflate(R.layout.layout_test_start, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val test = arguments?.getParcelable(BUNDLE_TEST_MODEL) as TestModel
            numberOfQuestions.text = (String.format(getString(R.string.number_of_questions), test.test_total_questions))
        chapterTitle.text = test.chapter
    }


}

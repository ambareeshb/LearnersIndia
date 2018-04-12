package com.gbmainframe.learnersindia.fragments

import android.graphics.Color
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.os.bundleOf
import com.gbmainframe.learnersindia.R
import com.gbmainframe.learnersindia.activities.TestActivity
import kotlinx.android.synthetic.main.layout_test_finished.*
import nl.dionsegijn.konfetti.models.Shape
import nl.dionsegijn.konfetti.models.Size

/**
 * Created by ambareeshb on 09/04/18.
 */
class TestFinishedFragment : Fragment() {
    companion object {
        const val BUNDLE_TEST_TOTAL_ANSWER = "TEST_TOTAL_ANSWER"
        const val BUNDLE_TEST_RIGHT_ANSWER = "TEST_RIGHT_ANSWER"
        const val BUNDLE_TEST_WRONG_ANSWER = "TEST_WRONG_ANSWER"
        const val BUNDLE_TEST_SKIPPED_ANSWER = "TEST_SKIPPED_ANSWER"
        fun newInstance(totalMarks: Int, rightAnswer: Int, wrongAnswer: Int, skippedAnswer: Int) =
                TestFinishedFragment().apply {
                    arguments = bundleOf(BUNDLE_TEST_TOTAL_ANSWER to totalMarks,
                            BUNDLE_TEST_RIGHT_ANSWER to rightAnswer,
                            BUNDLE_TEST_WRONG_ANSWER to wrongAnswer,
                            BUNDLE_TEST_SKIPPED_ANSWER to skippedAnswer)
                }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
            inflater.inflate(R.layout.layout_test_finished, container, false)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        confettiView.build().addColors(Color.MAGENTA, Color.YELLOW, Color.GREEN)
                .setDirection(0.0, 359.0)
                .setSpeed(1f, 5f)
                .setFadeOutEnabled(true)
                .setTimeToLive(3000L)
                .addShapes(Shape.RECT, Shape.CIRCLE)
                .addSizes(Size(12))
                .setPosition(-50f, confettiView.width + 200f, -50f, -50f)
                .stream(300, 5000L)

        buttonTestFinished.setOnClickListener { (activity as TestActivity).finish() }
        arguments?.let {
            testTotalMark.text = String.format(getString(R.string.your_score), it.getInt(BUNDLE_TEST_TOTAL_ANSWER))
            rightAnswers.text = it.getInt(BUNDLE_TEST_RIGHT_ANSWER).toString()
            wrongAnswers.text = it.getInt(BUNDLE_TEST_WRONG_ANSWER).toString()
            skippedAnswers.text = it.getInt(BUNDLE_TEST_SKIPPED_ANSWER).toString()
        }
    }
}
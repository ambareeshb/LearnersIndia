package com.gbmainframe.learnersindia.fragments.game

import android.graphics.Color
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.os.bundleOf
import com.gbmainframe.learnersindia.R
import com.gbmainframe.learnersindia.activities.GameActivity
import com.gbmainframe.learnersindia.activities.TestActivity
import kotlinx.android.synthetic.main.layout_game_finished.*
import nl.dionsegijn.konfetti.models.Shape
import nl.dionsegijn.konfetti.models.Size

/**
 * Created by ambareeshb on 11/04/18.
 */
class GameFinishFragment : Fragment() {
    companion object {
        const val BUNDLE_TEST_TOTAL_ANSWER = "TEST_TOTAL_ANSWER"
        fun newInstance(totalMarks: Int) =
                GameFinishFragment().apply {
                    arguments = bundleOf(BUNDLE_TEST_TOTAL_ANSWER to totalMarks)
                }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
            inflater.inflate(R.layout.layout_game_finished, container, false)


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

        buttonTestFinished.setOnClickListener { (activity as GameActivity).loadGameStartFragment() }
        arguments?.let {
            gameTotalMark.text = String.format(getString(R.string.you_reached), it.getInt(BUNDLE_TEST_TOTAL_ANSWER))
        }
    }
}
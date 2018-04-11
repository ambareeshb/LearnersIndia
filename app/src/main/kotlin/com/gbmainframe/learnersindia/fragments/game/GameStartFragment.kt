package com.gbmainframe.learnersindia.fragments.game

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.gbmainframe.learnersindia.R
import com.gbmainframe.learnersindia.activities.GameActivity
import kotlinx.android.synthetic.main.layout_game_start.*
import kotlinx.android.synthetic.main.simple_toolbar.*

/**
 * Created by ambareeshb on 10/04/18.
 */
class GameStartFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
            inflater.inflate(R.layout.layout_game_start, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        toolbarTitle.text = getString(R.string.game)
        backButton.setOnClickListener {
            (activity as GameActivity).onBackPressed()
        }
        buttonStartGame.setOnClickListener {
            (activity as GameActivity).loadGameQuestionFragment()
        }
    }
}

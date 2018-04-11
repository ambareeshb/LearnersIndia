package com.gbmainframe.learnersindia.activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.gbmainframe.learnersindia.R
import com.gbmainframe.learnersindia.fragments.game.GameFinishFragment
import com.gbmainframe.learnersindia.fragments.game.GameLevelFragment
import com.gbmainframe.learnersindia.fragments.game.GameQuestionFragment
import com.gbmainframe.learnersindia.fragments.game.GameStartFragment
import com.gbmainframe.learnersindia.utils.FragmentUtils

class GameActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)
        loadGameStartFragment()
    }

    fun loadGameFinish(level:Int) {
        FragmentUtils(supportFragmentManager).beginTransaction()
                .replace(R.id.fragmentContainer, GameFinishFragment.newInstance(level))
                .commit()
    }

    fun loadGameStartFragment() {
        FragmentUtils(supportFragmentManager).beginTransaction().replace(R.id.fragmentContainer, GameStartFragment())
                .commit()
    }

    fun loadGameLevelFragment() {
        FragmentUtils(supportFragmentManager).beginTransaction().replace(R.id.fragmentContainer, GameLevelFragment())
                .commit()
    }

    fun loadGameQuestionFragment() {
        FragmentUtils(supportFragmentManager).beginTransaction().replace(R.id.fragmentContainer, GameQuestionFragment())
                .commit()
    }
}

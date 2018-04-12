package com.gbmainframe.learnersindia.activities

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AlertDialog
import com.gbmainframe.learnersindia.R
import com.gbmainframe.learnersindia.fragments.game.GameFinishFragment
import com.gbmainframe.learnersindia.fragments.game.GameLevelFragment
import com.gbmainframe.learnersindia.fragments.game.GameQuestionFragment
import com.gbmainframe.learnersindia.fragments.game.GameStartFragment
import com.gbmainframe.learnersindia.utils.FragmentUtils
import com.gbmainframe.learnersindia.utils.sharedPrefManager

class GameActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)
        loadGameStartFragment()
    }

    fun loadGameFinish(level: Int) {
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


    override fun onBackPressed() {
        AlertDialog.Builder(this, R.style.Base_Theme_AppCompat_Light_Dialog).setTitle("Exit Game").setMessage(R.string.game_quit)
                .setPositiveButton(R.string.yes, { _, _ ->
                    this?.let {
                        sharedPrefManager.logoutUser(it)
                        super.onBackPressed()
                        this?.finish()
                    }
                })
                .setNegativeButton(R.string.no, { _, _ ->
                    //Do noting means to dismiss the dialog.
                }).show()
    }

}

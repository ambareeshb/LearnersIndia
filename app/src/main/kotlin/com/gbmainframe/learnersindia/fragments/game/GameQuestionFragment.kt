package com.gbmainframe.learnersindia.fragments.game

import android.media.MediaPlayer
import android.os.Bundle
import android.os.CountDownTimer
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.gbmainframe.learnersindia.R
import com.gbmainframe.learnersindia.activities.GameActivity
import com.gbmainframe.learnersindia.adapters.GameQuestionAdapter
import com.gbmainframe.learnersindia.models.GameLevelModel
import com.gbmainframe.learnersindia.utils.ApiInterface
import com.gbmainframe.learnersindia.utils.RetrofitUtils
import com.gbmainframe.learnersindia.utils.sharedPrefManager
import kotlinx.android.synthetic.main.layout_game_question.*
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

/**
 * Created by ambareeshb on 11/04/18.
 */
class GameQuestionFragment : Fragment() {

    var mediaPlayer: MediaPlayer? = null

    companion object {
        const val GAME_TIMER: Long = 90 * 1000
        const val WEB_SITE = "https://www.learnersindia.com/"
    }

    private var levelValue = 1
    private lateinit var gameLevel: ArrayList<GameLevelModel>
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
            inflater.inflate(R.layout.layout_game_question, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val timer = object : CountDownTimer(GAME_TIMER, 1000) {
            override fun onFinish() {
                (activity as GameActivity).loadGameFinish(levelValue)
            }

            override fun onTick(millisUntilFinished: Long) {
                gameTimer.text = "${millisUntilFinished / 1000}"
            }

        }

        activity?.let {
            val user = sharedPrefManager.getUser(it)
            val apiInterface = RetrofitUtils.initRetrofit(ApiInterface::class.java)
            apiInterface.getGameLevel()
                    .flatMap {
                        gameLevel = it
                        apiInterface.getGameQuestion(user.tocken, levelValue)
                    }
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({
                        gameQuestion.loadDataWithBaseURL("", "<b>${it.question_data.question}</b>", "text/html",
                                "UTF-8", "")
//                        mediaPlayer.setOnCompletionListener { mediaPlayer ->
//                            mediaPlayer.stop()
//                            mediaPlayer.setDataSource(WEB_SITE + it.question_data.voice2)
//                            mediaPlayer.prepare()
//                            mediaPlayer.start()
//                        }
                        mediaPlayer = MediaPlayer()
                        val path = WEB_SITE + it.question_data.voice1
                        mediaPlayer?.setDataSource(path)
                        mediaPlayer?.prepare()
                        recyclerGame.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
                        recyclerGame.adapter = GameQuestionAdapter(it.question_data, {
                            levelValue++
                            if (levelValue >= gameLevel.size) {
                                (activity as GameActivity).loadGameFinish(levelValue)
                                return@GameQuestionAdapter
                            }
                            fetchQuestions(levelValue)
                        })
                        timer.start()
                        mediaPlayer?.start()
                    }, {
                        mediaPlayer?.stop()
                        mediaPlayer?.release()
                        mediaPlayer = null
                        it.printStackTrace()
                        (activity as GameActivity).loadGameFinish(levelValue)
                    })
        }

    }

    override fun onStop() {
        super.onStop()
        mediaPlayer?.release()
    }

    private fun fetchQuestions(level: Int) {
        mediaPlayer?.stop()
        mediaPlayer?.release()
        mediaPlayer = null
        activity?.let {
            val user = sharedPrefManager.getUser(it)
            RetrofitUtils.initRetrofit(ApiInterface::class.java).getGameQuestion(user.tocken,
                    level).subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({
                        //                        mediaPlayer.setOnCompletionListener { mediaPlayer ->
//                            mediaPlayer.stop()
//                            mediaPlayer.setDataSource(WEB_SITE + it.question_data.voice2)
//                            mediaPlayer.prepare()
//                            mediaPlayer.start()
//                        }
                        mediaPlayer = MediaPlayer()
                        mediaPlayer?.setDataSource(WEB_SITE + it.question_data.voice1)
                        mediaPlayer?.prepare()
                        gameQuestion.loadDataWithBaseURL("", "<b>${it.question_data.question}</b>", "text/html",
                                "UTF-8", "")
                        (recyclerGame.adapter as GameQuestionAdapter).setNextOptions(it.question_data)
                        mediaPlayer?.start()
                    }, {
                        mediaPlayer?.stop()
                        mediaPlayer?.release()
                        mediaPlayer = null
                        (activity as GameActivity).loadGameFinish(levelValue)
                        it.printStackTrace()
                    })
        }
    }

}
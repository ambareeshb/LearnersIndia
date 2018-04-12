package com.gbmainframe.learnersindia.fragments.game

import android.media.MediaPlayer
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.gbmainframe.learnersindia.R
import com.gbmainframe.learnersindia.activities.GameActivity
import com.gbmainframe.learnersindia.adapters.GameQuestionAdapter
import com.gbmainframe.learnersindia.models.GameLevelModel
import com.gbmainframe.learnersindia.models.apiresponses.GameQuestionResponse
import com.gbmainframe.learnersindia.utils.ApiInterface
import com.gbmainframe.learnersindia.utils.RetrofitUtils
import com.gbmainframe.learnersindia.utils.sharedPrefManager
import kotlinx.android.synthetic.main.layout_game_question.*
import rx.Single
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

/**
 * Created by ambareeshb on 11/04/18.
 */
class GameQuestionFragment : Fragment() {
    private var timer: CountDownTimer? = null
    private var fiftyFiftyAvailable = true
    private var extraLifeAvailable = true
    private var mediaPlayer: MediaPlayer? = null
    private var mediaPlayerAnswer: MediaPlayer? = null

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

        fiftyFifty.setOnClickListener {
            if (fiftyFiftyAvailable) {
                fiftyFiftyOverlay.visibility = View.VISIBLE
                (recyclerGame.adapter as GameQuestionAdapter).applyFiftyFifty()
            }
            fiftyFiftyAvailable = false
        }
        extraLife.setOnClickListener {
            if (extraLifeAvailable) {
                extraLifeOverlay.visibility = View.VISIBLE
                (recyclerGame.adapter as GameQuestionAdapter).applyExtraLife()
                Snackbar.make(view, R.string.extra_life_text, Snackbar.LENGTH_LONG).show()
            }
            extraLifeAvailable = false
        }

        mediaPlayer?.let {
            stopMediaPlayer(it)
            mediaPlayer = null
        }
        mediaPlayerAnswer?.let {
            stopMediaPlayer(it)
            mediaPlayerAnswer = null
        }
        progressGame.visibility = View.VISIBLE
        layoutGame.visibility = View.GONE
        showLevelLabel("Level $levelValue")
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
                        if (it.response_type == getString(R.string.response_type_error)) {
                            (activity as GameActivity).loadGameFinish(levelValue)
                            return@subscribe
                        }
                        Single.fromCallable {
                            loadQuestion(it)
                        }.subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe({

                                }, { it.printStackTrace() })

                        recyclerGame.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
                        recyclerGame.adapter = GameQuestionAdapter(it.question_data, { correctAnswer ->
                            levelValue++
                            if (levelValue >= gameLevel.size || !correctAnswer) {
                                gameLevel.forEach {
                                    if (it.level_id == levelValue - 1) {
                                        val finalLevel = it.level_id - (it.level_id % 5)
                                        if (finalLevel == 0) {
                                            (activity as GameActivity).loadGameFinish(0)
                                            return@GameQuestionAdapter
                                        }
                                        (activity as GameActivity).loadGameFinish(gameLevel[gameLevel.size - 1 - finalLevel].level_score)
                                        return@GameQuestionAdapter
                                    }
                                }
                                (activity as GameActivity).loadGameFinish(gameLevel.last().level_score)
                            }
                            fetchQuestions(levelValue)
                        }) {
                            Snackbar.make(view, R.string.extra_life_lost, Snackbar.LENGTH_LONG).show()
                        }

                    }, {
                        mediaPlayer?.let {
                            stopMediaPlayer(it)
                            mediaPlayer = null
                        }
                        mediaPlayerAnswer?.let {
                            stopMediaPlayer(it)
                            mediaPlayerAnswer = null
                        }
                        it.printStackTrace()
                        (activity as GameActivity).loadGameFinish(levelValue)
                        throw it
                    })
        }

    }

    override fun onStop() {
        mediaPlayer?.let {
            stopMediaPlayer(it)
            mediaPlayer = null
        }
        mediaPlayerAnswer?.let {
            stopMediaPlayer(it)
            mediaPlayerAnswer = null
        }
        super.onStop()
    }

    private fun fetchQuestions(level: Int) {
        mediaPlayer?.let {
            stopMediaPlayer(it)
            mediaPlayer = null
        }
        mediaPlayerAnswer?.let {
            stopMediaPlayer(it)
            mediaPlayerAnswer = null
        }
        progressGame.visibility = View.VISIBLE
        layoutGame.visibility = View.GONE
        showLevelLabel("Level $levelValue")
        activity?.let {
            val user = sharedPrefManager.getUser(it)
            RetrofitUtils.initRetrofit(ApiInterface::class.java).getGameQuestion(user.tocken,
                    level).subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({
                        if (it.response_type == getString(R.string.response_type_error)) {
                            (activity as GameActivity).loadGameFinish(levelValue)
                            return@subscribe
                        }
                        (recyclerGame.adapter as GameQuestionAdapter).setNextOptions(it.question_data)

                        Single.fromCallable {
                            loadQuestion(it)
                        }.subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe({
                                }, { it.printStackTrace() })
                    }, {
                        mediaPlayer?.let {
                            stopMediaPlayer(it)
                            mediaPlayer = null
                        }
                        mediaPlayerAnswer?.let {
                            stopMediaPlayer(it)
                            mediaPlayerAnswer = null
                        }
                        (activity as GameActivity).loadGameFinish(levelValue)
                        it.printStackTrace()
                        throw it
                    })
        }
    }

    /**
     * Load each question.
     */
    private fun loadQuestion(gameReponse: GameQuestionResponse) {
        gameQuestion.post {
            gameQuestion.loadDataWithBaseURL("", "<b>${gameReponse.question_data.question}</b>", "text/html",
                    "UTF-8", "")
            var questionEnded = false
            val mediaPlayer = MediaPlayer()
            this.mediaPlayer = mediaPlayer
            mediaPlayer.setOnPreparedListener {
                progressGame.visibility = View.GONE
                layoutGame.visibility = View.VISIBLE
                Handler().postDelayed({
                    startTimer(gameReponse.question_data.duration)
                    mediaPlayer.start()
                }, 500)
            }
            mediaPlayer.setDataSource(WEB_SITE + gameReponse.question_data.voice1)
            mediaPlayer.prepareAsync()

            val mediaPlayerAnswer = MediaPlayer()
            this.mediaPlayerAnswer = mediaPlayerAnswer
            mediaPlayerAnswer.setOnPreparedListener {
                if (questionEnded) {
                    mediaPlayerAnswer?.start()
                    questionEnded = false
                    return@setOnPreparedListener
                }
                questionEnded = true

            }
            mediaPlayerAnswer?.setDataSource(WEB_SITE + gameReponse.question_data.voice2)
            mediaPlayerAnswer?.prepareAsync()


            mediaPlayer?.setOnCompletionListener { mp ->
                mp?.stop()
                mp?.release()
                if (questionEnded) {
                    mediaPlayerAnswer?.start()
                    questionEnded = false
                    return@setOnCompletionListener
                }
                questionEnded = true
            }
        }
    }

    private fun stopMediaPlayer(mp: MediaPlayer) {
        try {
            if (mp.isPlaying) {
                mp.stop()
                mp.release()
            }
        } catch (exeception: IllegalStateException) {
            exeception.printStackTrace()
        }
    }

    private fun startTimer(seconds: Long) {
        timer?.cancel()
        timer = object : CountDownTimer(seconds * 1000, 1000) {
            override fun onFinish() {
                activity?.let {
                    gameLevel.forEach {
                        if (it.level_id == levelValue - 1) {
                            val finalLevel = it.level_id - (it.level_id % 5)
                            if (finalLevel == 0) {
                                (activity as GameActivity).loadGameFinish(0)
                                return
                            }
                            (activity as GameActivity).loadGameFinish(gameLevel[gameLevel.size - 1 - finalLevel].level_score)
                            return
                        }
                    }
                    (activity as GameActivity).loadGameFinish(gameLevel.last().level_score)
                }
            }

            override fun onTick(millisUntilFinished: Long) {
                gameTimer?.text = "${millisUntilFinished / 1000}"
            }

        }.start()
    }

    private fun showLevelLabel(label: String) {
        levelLabel.visibility = View.VISIBLE
        textLevelLabel.text = label
        Handler().postDelayed({ levelLabel?.visibility = View.GONE }, 2000)
    }
}
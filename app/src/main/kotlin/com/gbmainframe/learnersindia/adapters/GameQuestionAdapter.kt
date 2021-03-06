package com.gbmainframe.learnersindia.adapters

import android.os.Handler
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import com.gbmainframe.learnersindia.R
import com.gbmainframe.learnersindia.models.GameQuestionModel
import kotlinx.android.synthetic.main.game_single_qustin.view.*

/**
 * Created by ambareeshb on 11/04/18.
 */
class GameQuestionAdapter(private var question: GameQuestionModel,
                          private var nextQuestion: (Boolean) -> Unit,
                          private val extraLifeTryAgain: () -> Unit) : RecyclerView.Adapter<GameQuestionAdapter.ViewHolder>() {
    private var optionList: Array<String>
    private var answered: Boolean

    private var fiftyFifty = false
    private var fiftyFiftyCount = 0

    private var extraLife = false

    init {
        optionList = arrayOf(question.option1, question.option2, question.option3, question.option4)
        answered = false

    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder =
            ViewHolder(LayoutInflater.from(parent?.context).inflate(R.layout.game_single_qustin, parent, false))

    override fun getItemCount(): Int = optionList.size

    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        holder?.bindView(optionList[position], position)
    }

    fun applyFiftyFifty() {
        fiftyFifty = true
        notifyDataSetChanged()
    }

    fun setNextOptions(question: GameQuestionModel) {
        answered = false
        fiftyFifty = false
        this.question = question
        optionList = arrayOf(question.option1, question.option2, question.option3, question.option4)
        notifyDataSetChanged()
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bindView(option: String, position: Int) {
            val mimeType = "text/html"
            val encoding = "UTF-8"

            itemView.gameOption.loadDataWithBaseURL("", "<b>${option}</b>", mimeType, encoding, "")

            if (fiftyFifty) {
                if (position + 1 != question.answer && fiftyFiftyCount < 2) {
                    itemView.visibility = View.GONE
                    fiftyFiftyCount++
                }
            } else {
                itemView.visibility = View.VISIBLE
            }
            if (!answered) {
                itemView.gameCard.setCardBackgroundColor(ContextCompat.getColor(itemView.context, R.color.white))
                itemView.gameOption.settings
                itemView.gameOption.setBackgroundColor(ContextCompat.getColor(itemView.context, R.color.white))
            }
            if (answered && position + 1 == question.answer) {
                itemView.gameCard.setCardBackgroundColor(ContextCompat.getColor(itemView.context, android.R.color.holo_green_light))
                itemView.gameOption.settings
                itemView.gameOption.setBackgroundColor(ContextCompat.getColor(itemView.context, android.R.color.holo_green_light))
            }
            if (answered) {
                itemView.setOnClickListener { }
                itemView.gameOption.setOnTouchListener { v, event ->
                    if (event.action == MotionEvent.ACTION_DOWN) {
                    }
                    return@setOnTouchListener true
                }
            } else {
                itemView.setOnClickListener {
                    gameQuestionAnswered(position)
                }
                itemView.gameOption.setOnTouchListener { v, event ->
                    if (event.action == MotionEvent.ACTION_DOWN) {
                        gameQuestionAnswered(position)
                    }
                    return@setOnTouchListener true
                }
            }

        }

        private fun loadNextQuestion(nextQuestion: (Boolean) -> Unit, answerStatus: Boolean) {
            val handler = Handler()
            handler.postDelayed({ nextQuestion(answerStatus) }, 1000)
        }

        private fun gameQuestionAnswered(position: Int) {

            if (position + 1 != question.answer) {
                itemView.gameCard.setCardBackgroundColor(ContextCompat.getColor(itemView.context, android.R.color.holo_orange_light))
                itemView.gameOption.settings
                itemView.gameOption.setBackgroundColor(ContextCompat.getColor(itemView.context, android.R.color.holo_orange_light))
                if (extraLife) {
                    extraLifeTryAgain()
                    extraLife = false
                    Handler().postDelayed({ notifyDataSetChanged() }, 2000)
                    return
                }
                loadNextQuestion(nextQuestion, false)
            } else {
                loadNextQuestion(nextQuestion, true)
            }
            answered = true
            notifyDataSetChanged()
        }
    }

    fun applyExtraLife() {
        extraLife = true
    }

}

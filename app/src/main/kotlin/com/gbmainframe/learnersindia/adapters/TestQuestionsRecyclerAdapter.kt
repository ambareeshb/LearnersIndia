package com.gbmainframe.learnersindia.adapters

import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.gbmainframe.learnersindia.R
import com.gbmainframe.learnersindia.models.AnswerSelectModel
import com.gbmainframe.learnersindia.models.TestQuestionModel
import kotlinx.android.synthetic.main.test_question_single.view.*

/**
 * Created by ambareeshb on 09/04/18.
 */
class TestQuestionsRecyclerAdapter(private var question: TestQuestionModel) : RecyclerView.Adapter<TestQuestionsRecyclerAdapter.ViewHolder>() {
    private var optionList: Array<String>

    private var answered: Boolean = false

    init {
        optionList = arrayOf(question.option1, question.option2, question.option3, question.option3)

    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder =
            ViewHolder(LayoutInflater.from(parent?.context).inflate(R.layout.test_question_single, parent, false))

    override fun getItemCount(): Int = optionList.size

    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        holder?.bindView(optionList[position], position)
    }

    fun setNextOptions(question: TestQuestionModel) {
        this.question = question
        answered = false
        optionList = arrayOf(question.option1, question.option2, question.option3, question.option3)
        notifyDataSetChanged()
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bindView(option: String, position: Int) {
            val mimeType = "text/html"
            val encoding = "UTF-8"


            itemView.testQuestionOption.loadDataWithBaseURL("", "<b>${option}</b>", mimeType, encoding, "")
            if (!answered) {
                itemView.answerTick.setImageDrawable(null)
            }
            if (answered && position + 1 == question.correct_answer) {
                itemView.answerTick.setImageDrawable(ContextCompat.getDrawable(itemView.context, R.drawable.answer_correct))
            }

            if (answered) {
                itemView.setOnClickListener { null }
                itemView.testQuestionOption.setOnTouchListener { v, event ->
                    if (event.action == MotionEvent.ACTION_DOWN) {
                    }
                    return@setOnTouchListener true
                }
            } else {
                itemView.setOnClickListener {
                    answeredQuestion(position)
                }
                itemView.testQuestionOption.setOnTouchListener { v, event ->
                    if (event.action == MotionEvent.ACTION_DOWN) {
                        answeredQuestion(position)
                    }
                    return@setOnTouchListener true
                }
            }
        }

        /**
         * Function to answer question.
         */
        fun answeredQuestion(position: Int) {
            answered = true
            if (question.correct_answer != position + 1) {
                itemView.answerTick.setImageDrawable(ContextCompat.getDrawable(itemView.context, R.drawable.answer_wrong))
            }
            notifyDataSetChanged()
        }

    }

}
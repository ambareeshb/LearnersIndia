package com.gbmainframe.learnersindia.adapters

import android.support.design.widget.Snackbar
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.gbmainframe.learnersindia.R
import com.gbmainframe.learnersindia.models.RecommendedQuestions
import kotlinx.android.synthetic.main.recommended_question_single.view.*
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by ambareeshb on 27/03/18.
 */
class RecommendedQuestionsAdapter(private val recommendedQuestions: ArrayList<RecommendedQuestions>) : RecyclerView.Adapter<RecommendedQuestionsAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder =
            ViewHolder(LayoutInflater.from(parent?.context).inflate(R.layout.recommended_question_single, parent, false))


    override fun getItemCount(): Int = recommendedQuestions.size

    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        holder?.bindView(recommendedQuestions[position])
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bindView(recommendedQuestions: RecommendedQuestions) {
            itemView.recommendedQuestion.text = recommendedQuestions.qst_title
            itemView.recommendedQuestionTop.text = "asked in ${recommendedQuestions.subjectname}," +
                    " ${dateFromTimeStamp(recommendedQuestions.qst_timestamp.toLong())}"
            itemView.recommendedAddAnswer.setOnClickListener {
                Snackbar.make(itemView.rootView, "Answer not available now", Snackbar.LENGTH_SHORT).show()
            }
        }

        private fun dateFromTimeStamp(timeStamp: Long): String {
            val date = SimpleDateFormat("MM/DD/YY", Locale.US)
            return date.format(timeStamp)
        }

    }
}
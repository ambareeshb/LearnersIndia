package com.gbmainframe.learnersindia.adapters

import android.support.design.widget.Snackbar
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.gbmainframe.learnersindia.R
import com.gbmainframe.learnersindia.models.RecommendedQuestions
import kotlinx.android.synthetic.main.layout_home_fragment.view.*
import kotlinx.android.synthetic.main.recommended_question_single.view.*
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*
import java.util.regex.Pattern

/**
 * Created by ambareeshb on 27/03/18.
 */
class RecommendedQuestionsAdapter(private val recommendedQuestions: ArrayList<RecommendedQuestions>,
                                  private val questionClicked: (Int) -> Unit) : RecyclerView.Adapter<RecommendedQuestionsAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder =
            ViewHolder(LayoutInflater.from(parent?.context).inflate(R.layout.recommended_question_single, parent, false))


    override fun getItemCount(): Int = recommendedQuestions.size

    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        holder?.bindView(recommendedQuestions[position])
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bindView(recommendedQuestion: RecommendedQuestions) {
            itemView.recommendedQuestionUserName.text = recommendedQuestion.student
            itemView.recommendedQuestionTop.text = "asked in ${recommendedQuestion.subjectname}," +
                    " ${dateFromTimeStamp(recommendedQuestion.qst_timestamp.toLong())}"
//            itemView.recommendedQuestion.text = extractImageUrl(recommendedQuestion.qst_title)
            val answerString = " ${recommendedQuestion.Total_answers} ${itemView.context.resources.getQuantityString(R.plurals.question_answers, recommendedQuestion.Total_answers)}"
            itemView.recommendedQuestionCount.text = answerString

            if(recommendedQuestion.qst_title.startsWith("<")){
                itemView.recommendedQuestion.visibility = View.VISIBLE
                itemView.recommendedQuestionTitle.visibility = View.GONE

                Glide.with(itemView.context)
                        .load(extractImageUrl(recommendedQuestion.qst_title))
                        .into(itemView.recommendedQuestion)
            }
            else{
                itemView.recommendedQuestion.visibility = View.GONE
                itemView.recommendedQuestionTitle.visibility = View.VISIBLE

                itemView.recommendedQuestionTitle.text = recommendedQuestion.qst_title
            }


            itemView.recommendedAddAnswer.setOnClickListener {
                if (recommendedQuestion.Total_answers < 1) {
                    Snackbar.make(itemView.rootView, "Answer not available now", Snackbar.LENGTH_SHORT).show()
                    return@setOnClickListener
                }
                questionClicked(recommendedQuestion.qst_id)
            }
        }

        private fun dateFromTimeStamp(timeStamp: Long): String {
            val date = SimpleDateFormat("mm/dd/yy", Locale.US)
            return date.format(timeStamp)
        }

        private fun extractImageUrl(html: String): String {
            val pattern = Pattern.compile("src=.*?.png")
            val matcher = pattern.matcher(html)
            return try {
                if (matcher.find()) matcher.group(0).split("\\")[1].let {
                    it.substring(1, it.length)
                } else ""
            } catch (e: Exception) {
                ""
            }
        }

    }

}
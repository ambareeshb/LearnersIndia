package com.gbmainframe.learnersindia.adapters

import android.support.design.widget.Snackbar
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.gbmainframe.learnersindia.R
import com.gbmainframe.learnersindia.models.RecommendedQuestions
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
    companion object {
        const val BASE_URL = "http://learnersindia.com/"
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder =
            ViewHolder(LayoutInflater.from(parent?.context).inflate(R.layout.recommended_question_single, parent, false))


    override fun getItemCount(): Int = recommendedQuestions.size

    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        holder?.bindView(recommendedQuestions[position])
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        fun bindView(recommendedQuestion: RecommendedQuestions) {
            val imageUrl = BASE_URL + recommendedQuestion.profile_pic
            val requestOptions = RequestOptions()
            val placeholder = requestOptions.placeholder(ContextCompat
                    .getDrawable(itemView.context, R.drawable.avatar_common))

            Glide.with(itemView.context)
                    .setDefaultRequestOptions(placeholder)
                    .load(imageUrl)
                    .into(itemView.userImage)

            itemView.recommendedQuestionUserName.text = recommendedQuestion.student
            itemView.recommendedQuestionTop.text = "Asked in ${recommendedQuestion.subjectname} on " +
                    " ${dateFromTimeStamp(recommendedQuestion.qst_timestamp.toInt())}"
//            itemView.recommendedQuestion.text = extractImageUrl(recommendedQuestion.qst_title)
            if (recommendedQuestion.Total_answers < 1) {
                itemView.recommendedQuestionCount.text = "No answers"
                itemView.recommendedAddAnswer.visibility = View.GONE
            } else {
                itemView.recommendedAddAnswer.visibility = View.VISIBLE
                val answerString = " ${recommendedQuestion.Total_answers} ${itemView.context.resources.getQuantityString(R.plurals.question_answers, recommendedQuestion.Total_answers)}"
                itemView.recommendedQuestionCount.text = answerString
            }


            val questionTitle = "<font size = \"2\">${recommendedQuestion.qst_title}</font>".replace("\r\n", "").replace("\t", "").replace("\n", "").replace("\\", "")

            val mimeType = "text/html"
            val encoding = "UTF-8"
            itemView.recommendedQuestionTitle.loadDataWithBaseURL("",
                    questionTitle, mimeType, encoding, "")

            itemView.recommendedAddAnswer.setOnClickListener {
                questionClicked(recommendedQuestion.qst_id)
            }
        }

        private fun dateFromTimeStamp(timeStamp: Int): String {
            val date = SimpleDateFormat("dd/mm/yyy", Locale.US)
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
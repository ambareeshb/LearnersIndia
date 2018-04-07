package com.gbmainframe.learnersindia.adapters

import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.gbmainframe.learnersindia.R
import com.gbmainframe.learnersindia.models.AnswerModel
import kotlinx.android.synthetic.main.answer_layout.view.*
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by ambareeshb on 01/04/18.
 */
class AnswerAdapter(private val answerList: ArrayList<AnswerModel>) : RecyclerView.Adapter<AnswerAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder =
            ViewHolder(LayoutInflater.from(parent?.context).inflate(R.layout.answer_layout, parent, false))

    companion object {
        const val BASE_URL = "http://learnersindia.com/"
    }

    override fun getItemCount(): Int = answerList.size

    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        holder?.bindView(answerList[position])
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bindView(answer: AnswerModel) {
            val mimeType = "text/html"
            val encoding = "UTF-8"
            val answerText = "<font size = \"2\">${itemView.answerText}</font>".replace("\r\n", "").replace("\t", "").replace("\n", "").replace("\\", "")
            itemView.answerText.loadDataWithBaseURL("",
                    answerText, mimeType, encoding, "")
            itemView.answerUserName.text = answer.full_name
            itemView.answerTopText.text = "Posted in ${answer.full_name_sub}," +
                    " ${dateFromTimeStamp(answer.asw_timestamp.toLong())}"


            val imageUrl = BASE_URL + answer.asw_image
            val requestOptions = RequestOptions()
            val placeholder = requestOptions.placeholder(ContextCompat
                    .getDrawable(itemView.context, R.drawable.avatar_common))

            Glide.with(itemView.context)
                    .setDefaultRequestOptions(placeholder)
                    .load(imageUrl)
                    .into(itemView.userImage)
//            itemView.user
        }

        private fun dateFromTimeStamp(timeStamp: Long): String {
            val date = SimpleDateFormat("mm/dd/yyyy", Locale.US)
            return date.format(timeStamp)
        }
    }
}
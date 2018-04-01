package com.gbmainframe.learnersindia.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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

    override fun getItemCount(): Int = answerList.size

    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        holder?.bindView(answerList[position])
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bindView(answer: AnswerModel) {
            itemView.answerText.text = answer.qst_answer
            itemView.answerUserName.text = answer.full_name
            itemView.answerTopText.text = "poasted in ${answer.full_name_sub}," +
                    " ${dateFromTimeStamp(answer.asw_timestamp.toLong())}"
//            itemView.user
        }

        private fun dateFromTimeStamp(timeStamp: Long): String {
            val date = SimpleDateFormat("mm/dd/yy", Locale.US)
            return date.format(timeStamp)
        }
    }
}
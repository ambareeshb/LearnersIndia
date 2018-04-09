package com.gbmainframe.learnersindia.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.gbmainframe.learnersindia.R
import kotlinx.android.synthetic.main.test_question_single.view.*

/**
 * Created by ambareeshb on 09/04/18.
 */
class TestQuestionsRecyclerAdapter(private var optionList: ArrayList<String>,
                                   private val answerSelected: (Int, ImageView) -> Unit) : RecyclerView.Adapter<TestQuestionsRecyclerAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder =
            ViewHolder(LayoutInflater.from(parent?.context).inflate(R.layout.test_question_single, parent, false))

    override fun getItemCount(): Int = optionList.size

    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        holder?.bindView(optionList[position], position)
    }

    fun setNextOptions(optionList: ArrayList<String>) {
        this.optionList = optionList
        notifyDataSetChanged()
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bindView(option: String, position: Int) {
            val mimeType = "text/html"
            val encoding = "UTF-8"
            itemView.testQuestionOption.loadDataWithBaseURL("", "<b>$option</b>", mimeType, encoding, "")
            itemView.setOnClickListener { answerSelected(position + 1, itemView.answerTick) }
        }

    }

}
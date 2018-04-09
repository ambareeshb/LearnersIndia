package com.gbmainframe.learnersindia.adapters

import android.content.Intent
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.gbmainframe.learnersindia.R
import com.gbmainframe.learnersindia.models.apiresponses.ExerciseTopicResponse
import kotlinx.android.synthetic.main.exercise_list_topic_item.view.*

/**
 * Created by ambareeshb on 09/04/18.
 */
class ExerciseTopicAdapter(private val exerciseList: ArrayList<ExerciseTopicResponse>) : RecyclerView.Adapter<ExerciseTopicAdapter.ViewHolder>() {
    override fun getItemCount(): Int = exerciseList.size

    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        holder?.bindView(exerciseList[position])
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent?.context)
                .inflate(R.layout.exercise_list_topic_item, parent, false))
    }


    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bindView(topicResponse: ExerciseTopicResponse) {
            itemView.recyclerExercise.layoutManager = LinearLayoutManager(itemView.context, LinearLayoutManager.HORIZONTAL, false)
            itemView.recyclerExercise.adapter = ExerciseAdapter(exerciseList = topicResponse.exercises_data)
            itemView.topicName.text = topicResponse.topic_name
            if (topicResponse.exercises_data.size == 0) {
                itemView.textNoExerciseAvailable.visibility = View.VISIBLE
            } else {
                itemView.textNoExerciseAvailable.visibility = View.GONE
            }
        }

    }
}


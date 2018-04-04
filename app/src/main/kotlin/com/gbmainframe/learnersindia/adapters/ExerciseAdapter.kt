package com.gbmainframe.learnersindia.adapters

import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.gbmainframe.learnersindia.R
import com.gbmainframe.learnersindia.models.Exercise
import kotlinx.android.synthetic.main.exercise_single_item.view.*

/**
 * Created by ambareeshb on 05/04/18.
 */
class ExerciseAdapter(private val exerciseList: ArrayList<Exercise>) : RecyclerView.Adapter<ExerciseAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder =
            ViewHolder(LayoutInflater.from(parent?.context).inflate(R.layout.exercise_single_item, parent, false))

    override fun getItemCount(): Int = exerciseList.size

    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        holder?.bindView(exerciseList[position])
    }

    override fun getItemViewType(position: Int): Int {
        return position % 2
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindView(exercise: Exercise) {
//            itemView.exerciseTitle.text = exercise.exercise_title.convertHtml()
//            itemView.exerciseSolution.text = exercise.exercise_solution.convertHtml()
            if(getItemViewType() == 1){
                itemView.setBackgroundColor(ContextCompat.getColor(itemView.context,R.color.color_grey1))
            }
            else{
                itemView.setBackgroundColor(ContextCompat.getColor(itemView.context,R.color.color_grey2))
            }
            val mimeType = "text/html"
            val encoding = "UTF-8"
            itemView.exerciseTitle.loadDataWithBaseURL("", exercise.exercise_title, mimeType, encoding, "")
            itemView.exerciseSolution.loadDataWithBaseURL("", exercise.exercise_title, mimeType, encoding, "")
        }

        private fun String.convertHtml() = Html.fromHtml(this)

    }
}

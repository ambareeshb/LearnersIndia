package com.gbmainframe.learnersindia.adapters

import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.gbmainframe.learnersindia.R
import com.gbmainframe.learnersindia.models.ClassInfo
import kotlinx.android.synthetic.main.layout_class_single_item.view.*
import java.util.*

/**
 * Created by ambareeshb on 25/03/18.
 */
class ClassRecyclerAdapter(private val classList: ArrayList<ClassInfo>,
                           private val onClassSelect: (classInfo: ClassInfo) -> Unit) : RecyclerView.Adapter<ClassRecyclerAdapter.ViewHolder>() {

    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        holder?.bindView(classList[position])
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent?.context).inflate(R.layout.layout_class_single_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = classList.size

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val colors = arrayListOf(
                R.color.paleBrown, R.color.paleGreen,
                R.color.paleViolet, R.color.ivory,
                R.color.orange, R.color.palePink,
                R.color.yellow,R.color.paleYellow,
                R.color.skyBlue,R.color.parrotGreen,R.color.darkViolet)

        fun bindView(classInfo: ClassInfo) {
            itemView.pointerCircle.setColorFilter(ContextCompat.getColor(itemView.context, colors.random()!!))
            itemView.classText.text = classInfo.grade
            itemView.setOnClickListener {
                onClassSelect(classInfo)
            }
        }

        /**
         * Returns a random element.
         */
        fun <E> List<E>.random(): E? = if (size > 0) get(Random().nextInt(size)) else null
    }

}
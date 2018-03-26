package com.gbmainframe.learnersindia.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.gbmainframe.learnersindia.R
import com.gbmainframe.learnersindia.models.Board
import com.gbmainframe.learnersindia.models.ClassInfo
import kotlinx.android.synthetic.main.layout_class_single_item.view.*

/**
 * Created by ambareeshb on 25/03/18.
 */
class ClassRecyclerAdapter(private val classList: ArrayList<ClassInfo>,
                           private val onClassSelect:(classInfo: ClassInfo) -> Unit) : RecyclerView.Adapter<ClassRecyclerAdapter.ViewHolder>() {
    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        holder?.bindView(classList[position])
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent?.context).inflate(R.layout.layout_class_single_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = classList.size

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bindView(classInfo: ClassInfo) {
            itemView.classText.text = classInfo.grade
            itemView.setOnClickListener {
                onClassSelect(classInfo)
            }
        }
    }

}
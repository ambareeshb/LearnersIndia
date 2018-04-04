package com.gbmainframe.learnersindia.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.gbmainframe.learnersindia.R
import com.gbmainframe.learnersindia.models.ChapterModel
import kotlinx.android.synthetic.main.single_chapter.view.*

/**
 * Created by ambareeshb on 27/03/18.
 */
class ChaptersAdapter(private val singleChapter: ArrayList<ChapterModel>,
                      val chapterClicked:(String,Int) -> Unit) : RecyclerView.Adapter<ChaptersAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder =
            ViewHolder(LayoutInflater.from(parent?.context).inflate(R.layout.single_chapter, parent, false))


    override fun getItemCount(): Int = singleChapter.size

    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        holder?.bindView(singleChapter[position])
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bindView(chapter: ChapterModel) {
            itemView.chapterTitle.text = chapter.chapter
            itemView.setOnClickListener {
                chapterClicked(chapter.chapter,chapter.chp_id)
            }
        }
    }


}

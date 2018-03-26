package com.gbmainframe.learnersindia.adapters

import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.gbmainframe.learnersindia.R
import com.gbmainframe.learnersindia.models.Board
import kotlinx.android.synthetic.main.layout_board_single_item.view.*

/**
 * Created by ambareeshb on 25/03/18.
 */
class BoardsRecyclerAdapter(private val boards: ArrayList<Board>, private val listener: BoardSelectListener) : RecyclerView.Adapter<BoardsRecyclerAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent?.context).inflate(R.layout.layout_board_single_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int =
            boards.size

    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        holder?.bindView(boards[position])
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bindView(board: Board) {
            itemView.boardText.text = board.syllabus
            itemView.boardImage.setImageDrawable(when (board.syl_id.toInt()) {
                1 -> ContextCompat.getDrawable(itemView.context, R.drawable.cbse_board)
                3 -> ContextCompat.getDrawable(itemView.context, R.drawable.kerala_board)
                4 -> ContextCompat.getDrawable(itemView.context, R.drawable.tn_board)
                else -> ContextCompat.getDrawable(itemView.context, R.drawable.tn_board)
            })
            itemView.setOnClickListener { listener.boardSelected(board) }
        }
    }

    public interface BoardSelectListener {
        fun boardSelected(board: Board)
    }
}
package com.gbmainframe.learnersindia.adapters

import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.gbmainframe.learnersindia.R
import com.gbmainframe.learnersindia.models.GameLevelModel
import kotlinx.android.synthetic.main.layout_game_single_level.view.*

/**
 * Created by ambareeshb on 11/04/18.
 */
class GameLevelAdapter(private val gameLevel: ArrayList<GameLevelModel>) : RecyclerView.Adapter<GameLevelAdapter.GameLevelViewHolder>() {
    private var textSize: Float = 0f
    private var previousPosition = 0

    init {
        textSize = 40f
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): GameLevelViewHolder {
        return GameLevelViewHolder(LayoutInflater.from(parent?.context).inflate(R.layout.layout_game_single_level, parent, false))
    }

    override fun getItemCount(): Int = gameLevel.size

    override fun onBindViewHolder(holder: GameLevelViewHolder?, position: Int) {
        holder?.bindView(gameLevel[position], (position == gameLevel.size - 1),position)

    }

    inner class GameLevelViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bindView(game: GameLevelModel, changeColor: Boolean, position: Int) {
            if (changeColor) {
                itemView.gameQuestion.setTextColor(ContextCompat.getColor(itemView.context,
                        android.R.color.holo_orange_light))
            } else {
                itemView.gameQuestion.setTextColor(ContextCompat.getColor(itemView.context,
                        R.color.white))

            }
            itemView.gameQuestion.textSize = textSize
            if (previousPosition >= position) {
                textSize += 3f
            } else {
                textSize -= 3f
            }
            previousPosition = position
            itemView.gameQuestion.text = game.level_score.toString()
        }

    }
}
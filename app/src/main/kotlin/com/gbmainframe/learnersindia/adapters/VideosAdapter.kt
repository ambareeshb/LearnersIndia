package com.gbmainframe.learnersindia.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.gbmainframe.learnersindia.R
import com.gbmainframe.learnersindia.models.VideoModel
import kotlinx.android.synthetic.main.layout_video_preview.view.*

/**
 * Created by ambareeshb on 30/03/18.
 */
class VideosAdapter(private val videoList: ArrayList<VideoModel>) : RecyclerView.Adapter<VideosAdapter.VideoViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): VideoViewHolder =
            VideoViewHolder(LayoutInflater.from(parent?.context).inflate(R.layout.layout_video_preview, parent, false))

    override fun getItemCount(): Int = videoList.size

    override fun onBindViewHolder(holder: VideoViewHolder?, position: Int) {
        holder?.bindView(videoList[position])
    }

    inner class VideoViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bindView(video: VideoModel) {
            itemView.videoNameText.text = video.ved_title
            itemView.videoPreview.setImageBitmap(Util.createThumbNailFromVideoUrl(video.ved_url, 5))
        }
    }
}
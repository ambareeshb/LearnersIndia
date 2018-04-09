package com.gbmainframe.learnersindia.adapters

import android.content.Intent
import android.support.v4.app.FragmentActivity
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.gbmainframe.learnersindia.R
import com.gbmainframe.learnersindia.activities.VideoPlayerActivity
import com.gbmainframe.learnersindia.models.apiresponses.VideoTopicResponse
import kotlinx.android.synthetic.main.video_list_recycler_item.view.*

/**
 * Created by ambareeshb on 05/04/18.
 */
class VideoVerticalListAdapter(val activity: FragmentActivity, private val videoTopicList: ArrayList<VideoTopicResponse>) : RecyclerView.Adapter<VideoVerticalListAdapter.VideoViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): VideoViewHolder {
        return VideoViewHolder(LayoutInflater.from(parent?.context).inflate(R.layout.video_list_recycler_item,
                parent, false))
    }

    override fun getItemCount(): Int = videoTopicList.size


    override fun onBindViewHolder(holder: VideoViewHolder?, position: Int) {
        holder?.bindView(videoTopicList[position])
    }

    inner class VideoViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bindView(videoTopicResponse: VideoTopicResponse) {
            itemView.topicName.text = videoTopicResponse.topic_name
            itemView.recyclerVideo.layoutManager = LinearLayoutManager(itemView.context, LinearLayoutManager.HORIZONTAL, false)

            if (videoTopicResponse.video_data.size == 0) {
                itemView.textNoVideosAvailable.visibility = View.VISIBLE
            } else {
                itemView.textNoVideosAvailable.visibility = View.GONE
            }
            itemView.recyclerVideo.adapter = VideosAdapter(videoTopicResponse.video_data) { videoId ->
                Intent(activity, VideoPlayerActivity::class.java).apply {
                    putExtra(VideoPlayerActivity.VIDEO_URI_BUNDLE_ID, videoId)
                    activity.startActivity(this)
                }
            }
        }

    }
}
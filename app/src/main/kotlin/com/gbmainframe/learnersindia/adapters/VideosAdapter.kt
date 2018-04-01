package com.gbmainframe.learnersindia.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.gbmainframe.learnersindia.R
import com.gbmainframe.learnersindia.models.VideoModel
import com.gbmainframe.learnersindia.utils.ApiInterface
import com.gbmainframe.learnersindia.utils.RetrofitUtils
import kotlinx.android.synthetic.main.layout_video_preview.view.*
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

/**
 * Created by ambareeshb on 30/03/18.
 */
class VideosAdapter(private val videoList: ArrayList<VideoModel>,
                    private val videoClicked: (String) -> Unit) : RecyclerView.Adapter<VideosAdapter.VideoViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): VideoViewHolder =
            VideoViewHolder(LayoutInflater.from(parent?.context).inflate(R.layout.layout_video_preview, parent, false))

    override fun getItemCount(): Int = videoList.size

    override fun onBindViewHolder(holder: VideoViewHolder?, position: Int) {
        holder?.bindView(videoList[position])
    }

    inner class VideoViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bindView(video: VideoModel) {
            itemView.videoNameText.text = video.ved_title
            itemView.setOnClickListener {
                videoClicked(video.ved_url)
            }
            RetrofitUtils.initRetrofit(ApiInterface::class.java).getVimeoVideoMetadata(video.video_id + ".json")
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({
                        Glide.with(itemView.context)
                                .load(it.url)
                                .into(itemView.videoPreview)
                    }, {
                        it.printStackTrace()
                    })

        }
    }
}
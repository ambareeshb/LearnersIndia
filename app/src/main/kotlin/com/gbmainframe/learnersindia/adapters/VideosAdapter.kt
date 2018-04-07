package com.gbmainframe.learnersindia.adapters

import android.support.design.widget.Snackbar
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
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
            val videoId = video.ved_url.split("/").last()
            itemView.videoNameText.text = video.ved_title.replace("\\","")
            itemView.setOnClickListener {
//                videoClicked(video.ved_id)
                Toast.makeText(itemView.context,"Hold on loading video",Toast.LENGTH_SHORT).show()
            }
            val vimeoToken = "Bearer ${itemView.context.getString(R.string.vimeo_access_token)}"
            RetrofitUtils.initRetrofit(ApiInterface::class.java, "https://api.vimeo.com/")
                    .getVimeoVideoMetadata(vimeoToken,videoId)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({
                        video ->
                        itemView.setOnClickListener {
                            videoClicked(video.files[2].link)
                        }
                        if (video.pictures.sizes[4] != null)
                            Glide.with(itemView.context)
                                    .load(video.pictures.sizes[3].link)
                                    .into(itemView.videoPreview)
                    }, {
                        it.printStackTrace()
                    })

        }
    }
}
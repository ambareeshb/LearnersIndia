package com.gbmainframe.learnersindia.fragments

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.gbmainframe.learnersindia.R
import com.gbmainframe.learnersindia.activities.Home
import com.gbmainframe.learnersindia.activities.VideoPlayerActivity
import com.gbmainframe.learnersindia.adapters.VideoVerticalListAdapter
import com.gbmainframe.learnersindia.adapters.VideosAdapter
import com.gbmainframe.learnersindia.utils.ApiInterface
import com.gbmainframe.learnersindia.utils.RetrofitUtils
import com.gbmainframe.learnersindia.utils.sharedPrefManager
import kotlinx.android.synthetic.main.layout_video_list_fragment.*
import kotlinx.android.synthetic.main.simple_toolbar.*
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

/**
 * Created by ambareeshb on 05/04/18.
 */
class VideoListFragment : Fragment() {
    companion object {
        const val CHAPTER_BUNDLE_ID = "CHAPTER_BUNDLE"
        const val CHAPTER_TITLE = "CHAPTER_TITLE"
        fun newInstance(chapter: Int, chapterTitle: String): VideoListFragment {
            val fragment = VideoListFragment()
            val bundle = Bundle()
            bundle.putInt(CHAPTER_BUNDLE_ID, chapter)
            bundle.putString(CHAPTER_TITLE, chapterTitle)
            fragment.arguments = bundle
            return fragment
        }
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
            LayoutInflater.from(context).inflate(R.layout.layout_video_list_fragment, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        val chapterId = arguments?.getInt(CHAPTER_BUNDLE_ID)
        val chapterTitle = arguments?.getString(CHAPTER_TITLE)
        /**
         * Api for recommended video list.
         */
        progressVideoList.visibility = View.VISIBLE
        textNoVideosAvailable.visibility = View.GONE

        toolbarTitle.text = chapterTitle
        backButton.setOnClickListener { (activity as Home).popBackStack() }


        activity?.let {
            val user = sharedPrefManager.getUser(it)
            RetrofitUtils.initRetrofit(ApiInterface::class.java).getVideos(
                    sylId = user.syl_id.toInt(),
                    classId = user.cls_id.toInt(),
                    subId = 1,
                    chapId = chapterId!!,
                    token = user.tocken
            ).subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({ videoRespose ->
                        progressVideoList?.let {
                            progressVideoList.visibility = View.GONE
                            if (it == null || videoRespose.response_type == getString(R.string.response_type_error) || videoRespose.response_data == null) {
                                textNoVideosAvailable.visibility = View.VISIBLE
                                return@subscribe
                            }
                            videoListRecycler.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                            videoListRecycler.adapter = VideoVerticalListAdapter(activity = activity!!,
                                    videoTopicList = videoRespose.response_data,
                                    goToPremium = { videoUrl, videoCategory ->

                                        if (videoCategory == "free") {

                                            Intent(activity, VideoPlayerActivity::class.java).apply {
                                                putExtra(VideoPlayerActivity.VIDEO_URI_BUNDLE_ID, videoUrl)
                                                activity?.startActivity(this)
                                            }
                                            return@VideoVerticalListAdapter
                                        }

                                        RetrofitUtils.initRetrofit(ApiInterface::class.java)
                                                .checkPaidStatus(user.tocken)
                                                .subscribeOn(Schedulers.io())
                                                .observeOn(AndroidSchedulers.mainThread())
                                                .subscribe({
                                                    if (it.response_type == context?.getString(R.string.response_type_error)) {
                                                        Snackbar.make(view, it.response_text, Snackbar.LENGTH_SHORT).show()
                                                        return@subscribe
                                                    }
                                                    if (it.payment_status != "paid") {
                                                        (activity as Home).loadGoToPremiumFragment()
                                                        return@subscribe
                                                    }
                                                    Intent(activity, VideoPlayerActivity::class.java).apply {
                                                        putExtra(VideoPlayerActivity.VIDEO_URI_BUNDLE_ID, videoUrl)
                                                        activity?.startActivity(this)
                                                    }

                                                },
                                                        { error ->
                                                            error.printStackTrace()
                                                            Snackbar.make(view, "Something went wrong", Snackbar.LENGTH_SHORT).show()
                                                        }
                                                )
                                    })
                        }
                    }, { error ->
                        error.printStackTrace()
                    })
        }
    }
}

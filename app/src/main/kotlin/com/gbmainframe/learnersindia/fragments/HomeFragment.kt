package com.gbmainframe.learnersindia.fragments

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.gbmainframe.learnersindia.R
import com.gbmainframe.learnersindia.activities.Home
import com.gbmainframe.learnersindia.activities.SearchActivity
import com.gbmainframe.learnersindia.activities.VideoPlayerActivity
import com.gbmainframe.learnersindia.adapters.RecommendedQuestionsAdapter
import com.gbmainframe.learnersindia.adapters.VideosAdapter
import com.gbmainframe.learnersindia.utils.ApiInterface
import com.gbmainframe.learnersindia.utils.RetrofitUtils
import com.gbmainframe.learnersindia.utils.sharedPrefManager
import kotlinx.android.synthetic.main.home_toolbar.*
import kotlinx.android.synthetic.main.layout_home_fragment.*
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

/**
 * Created by ambareeshb on 26/03/18.
 */
class HomeFragment : Fragment() {
    companion object {
        const val FRAGMENT_TAG = "HomeFragment"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? = inflater.inflate(R.layout.layout_home_fragment, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //Set toolbar title
        activity?.let {
            val user = sharedPrefManager.getUser(it)
            val board = when (user.syl_id.toInt()) {
                1 -> "CBSE"
                3 -> "Kerala"
                4 -> "TN"
                else -> "CBSE"
            }
            toolbarTitle.text = "$board - Class ${user.cls_id}"
        }

        toolbarSearch.setOnClickListener {
            startActivity(Intent(activity, SearchActivity::class.java))
        }
        /**
         * Home item click listeners.
         */
        homeItemPractise.setOnClickListener {
            (activity as Home).loadChapterListFragment(ChaptersFragment.Companion.CHAPTER.EXERCISE)
        }
        homeItemTest.setOnClickListener {
            Snackbar.make(view, "Test session will be available soon", Snackbar.LENGTH_SHORT).show()
        }
        homeItemVideo.setOnClickListener {
            (activity as Home).loadChapterListFragment(ChaptersFragment.Companion.CHAPTER.VIDEO)
//            Snackbar.make(view, "Video session will be available soon", Snackbar.LENGTH_SHORT).show()
        }
        homeItemGame.setOnClickListener {
            Snackbar.make(view, "will be available soon", Snackbar.LENGTH_SHORT).show()
        }
        homeItemNotification.setOnClickListener {
            Snackbar.make(view, "will be available soon", Snackbar.LENGTH_SHORT).show()
        }
        homeItemAsk.setOnClickListener {
            (activity as Home).loadAskQuestionFragment(true)
        }


        activity?.let {

            val user = sharedPrefManager.getUser(it)
            /**
             * Call API for recommended questions.
             */
            progressHome.visibility = View.VISIBLE
            textNoQuestionAvailable.visibility = View.GONE

            RetrofitUtils.initRetrofit(ApiInterface::class.java).getRecommendedQuestions(
                    user.syl_id.toInt(),
                    1,
                    user.cls_id.toInt())
//                    sylId = 1,
//                    classId = 10,
//                    subId = 1)

                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({ questions ->
                        if (progressHome == null) {
                            return@subscribe
                        }
                        progressHome?.visibility = View.GONE
                        if (questions.response_type == getString(R.string.response_type_error)) {
                            textNoQuestionAvailable.visibility = View.VISIBLE
                            Snackbar.make(view, questions.response_text, Snackbar.LENGTH_SHORT).show()
                            return@subscribe
                        }
                        textNoQuestionAvailable.visibility = View.GONE
                        recyclerRecommended.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                        recyclerRecommended.addItemDecoration(DividerItemDecoration(context, LinearLayoutManager.VERTICAL))
                        recyclerRecommended.adapter = RecommendedQuestionsAdapter(questions.questions_data) { questionId ->
                            (activity as Home).loadAnswerListFragment(questionId)
                        }
                    }, { error ->
                        progressHome?.visibility = View.GONE
                        textNoQuestionAvailable.visibility = View.VISIBLE
                        Snackbar.make(view, R.string.something_went_wrong, Snackbar.LENGTH_SHORT).show()
                        error.printStackTrace()
                    })
            /**
             * Api for recommended video list.
             */
            progressVideos.visibility = View.VISIBLE
            textNoVideosAvailable.visibility = View.GONE

            RetrofitUtils.initRetrofit(ApiInterface::class.java).getFreeVideos(
                    sylId = user.syl_id.toInt(),
                    classId = user.cls_id.toInt(),
                    subId = 1
            ).subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({
                        progressVideos.visibility = View.GONE
                        if (it == null || it.response_type == getString(R.string.response_type_error) || it.video_data == null) {
                            textNoVideosAvailable.visibility = View.VISIBLE
                            return@subscribe
                        }
                        recyclerBestVideos.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                        recyclerBestVideos.adapter = VideosAdapter(it.video_data) { videoId ->
                            Intent(activity, VideoPlayerActivity::class.java).apply {
                                putExtra(VideoPlayerActivity.VIDEO_URI_BUNDLE_ID, videoId)
                                startActivity(this)
                            }
                        }
                    }, { error ->
                        error.printStackTrace()
                    })
        }
    }

}

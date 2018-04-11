package com.gbmainframe.learnersindia.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.gbmainframe.learnersindia.R
import com.gbmainframe.learnersindia.activities.Home
import com.gbmainframe.learnersindia.activities.TestActivity
import com.gbmainframe.learnersindia.adapters.ChaptersAdapter
import com.gbmainframe.learnersindia.utils.ApiInterface
import com.gbmainframe.learnersindia.utils.RetrofitUtils
import com.gbmainframe.learnersindia.utils.sharedPrefManager
import kotlinx.android.synthetic.main.layout_chapters.*
import kotlinx.android.synthetic.main.simple_toolbar.*
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

/**
 * Created by ambareeshb on 27/03/18.
 */
class ChaptersFragment : Fragment() {
    companion object {
        enum class CHAPTER { VIDEO, EXERCISE, TEST }

        const val CHAPTER_BUNDLE = "BUNDLE_CHAPTER_LIST"
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
            inflater.inflate(R.layout.layout_chapters, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        toolbarTitle.text = getString(R.string.chapters)
        backButton.setOnClickListener {
            when (activity) {
                is Home -> (activity as Home).onBackPressed()
                is TestActivity -> (activity as TestActivity).onBackPressed()
            }

        }
        //Api to list chapters
        progress.visibility = View.VISIBLE
        activity?.let {
            val user = sharedPrefManager.getUser(it)
            RetrofitUtils.initRetrofit(ApiInterface::class.java).getChapters(
                    user.tocken,
                    user.syl_id.toInt(),
                    user.cls_id.toInt()
            ).subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({
                        if (progress == null) {
                            return@subscribe
                        }
                        progress.visibility = View.GONE
                        if (it == null || it.response_type == getString(R.string.response_type_error)) {
                            textNoChaptersAvailable.visibility = View.VISIBLE
                            return@subscribe
                        }
                        textNoChaptersAvailable.visibility = View.GONE
                        recyclerChapters.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
//                        recyclerChapters.addItemDecoration(DividerItemDecoration(context, LinearLayoutManager.VERTICAL))
                        recyclerChapters.adapter = ChaptersAdapter(it.chapters_data, { chapter ->
                            when (arguments?.getInt(CHAPTER_BUNDLE)) {
                                CHAPTER.VIDEO.ordinal -> (activity as Home).loadVideoListFragment(chapter.chapter, chapter.chp_id)
                                CHAPTER.EXERCISE.ordinal -> (activity as Home).loadExerciseListFragment(chapter.chapter, chapter.chp_id)
                                CHAPTER.TEST.ordinal -> (activity as TestActivity).loadTestStartFragment(chapter)
                            }
                        })

                    }, {
                        progress.visibility = View.GONE
                        textNoChaptersAvailable.visibility = View.VISIBLE
                        it.printStackTrace()
                    })

        }
    }
}
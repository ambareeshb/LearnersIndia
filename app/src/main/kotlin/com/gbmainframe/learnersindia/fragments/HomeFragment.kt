package com.gbmainframe.learnersindia.fragments

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.speech.RecognizerIntent
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.gbmainframe.learnersindia.R
import com.gbmainframe.learnersindia.activities.Home
import com.gbmainframe.learnersindia.adapters.RecommendedQuestionsAdapter
import com.gbmainframe.learnersindia.utils.ApiInterface
import com.gbmainframe.learnersindia.utils.RetrofitUtils
import com.gbmainframe.learnersindia.utils.sharedPrefManager
import com.quinny898.library.persistentsearch.SearchBox
import com.quinny898.library.persistentsearch.SearchResult
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
            toolbarTitle.text = "$board ${user.cls_id}"
        }

        toolbarSearch.setOnClickListener {
            searchbox.visibility = View.VISIBLE
        }
        /**
         * Home item click listeners.
         */
        homeItemPractise.setOnClickListener {
            (activity as Home).loadChapterListFragment()
        }
        homeItemTest.setOnClickListener {
            Snackbar.make(view, "Test session will be available soon", Snackbar.LENGTH_SHORT).show()
        }
        homeItemVideo.setOnClickListener {
            Snackbar.make(view, "Video session will be available soon", Snackbar.LENGTH_SHORT).show()
        }

        //Voice search available !
        searchbox.enableVoiceRecognition(this)
        //Listener for search box
        searchbox.setSearchListener(object : SearchBox.SearchListener {
            override fun onSearchClosed() {
                toolbar.visibility = View.VISIBLE
                searchbox.visibility = View.GONE
            }

            override fun onSearch(p0: String?) {
                Log.d("adf", "adf")

            }

            override fun onResultClick(p0: SearchResult?) {
                Log.d("adf", "adf")
            }

            override fun onSearchCleared() {
                Log.d("adf", "adf")
            }

            override fun onSearchTermChanged(p0: String?) {
                Log.d("adf", "adf")
            }

            override fun onSearchOpened() {
                searchbox.addAllSearchables(arrayListOf(SearchResult("n th degree polynomial"),
                        SearchResult("factorial of 16"),
                        SearchResult("fibanacci and sunflower"),
                        SearchResult("mid term question paper")
                ))
            }

        })

        /**
         * Call API for recommended questions.
         */
        progressHome.visibility = View.VISIBLE

        RetrofitUtils.initRetrofit(ApiInterface::class.java).getRecommendedQuestions()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ questions ->
                    progressHome?.visibility = View.GONE
                    recyclerRecommended.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                    recyclerRecommended.addItemDecoration(DividerItemDecoration(context, LinearLayoutManager.VERTICAL))
                    recyclerRecommended.adapter = RecommendedQuestionsAdapter(questions)
                }, { error ->
                    progressHome?.visibility = View.GONE
                    Snackbar.make(view, R.string.something_went_wrong, Snackbar.LENGTH_SHORT).show()
                    error.printStackTrace()
                })
        /**
         * On clicking floating action button on home.
         */
        floatingButtonAsk.setOnClickListener {
            (activity as Home).loadAskQuestionFragment()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == SearchBox.VOICE_RECOGNITION_CODE && resultCode == Activity.RESULT_OK) {
            val matches: ArrayList<String> = data?.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS) as ArrayList<String>
            searchbox.populateEditText(matches.first())
        }
    }
}

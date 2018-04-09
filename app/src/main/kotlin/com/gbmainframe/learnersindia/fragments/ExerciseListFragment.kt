package com.gbmainframe.learnersindia.fragments

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
import com.gbmainframe.learnersindia.adapters.ExerciseAdapter
import com.gbmainframe.learnersindia.adapters.ExerciseTopicAdapter
import com.gbmainframe.learnersindia.fragments.VideoListFragment.Companion.CHAPTER_BUNDLE_ID
import com.gbmainframe.learnersindia.fragments.VideoListFragment.Companion.CHAPTER_TITLE
import com.gbmainframe.learnersindia.utils.ApiInterface
import com.gbmainframe.learnersindia.utils.RetrofitUtils
import com.gbmainframe.learnersindia.utils.sharedPrefManager
import kotlinx.android.synthetic.main.layout_exercise_list_fragment.*
import kotlinx.android.synthetic.main.simple_toolbar.*
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

/**
 * Created by ambareeshb on 05/04/18.
 */
class ExerciseListFragment : Fragment() {
    companion object {
        fun newInstance(chapterTitle: String, chapterId: Int): ExerciseListFragment {
            val fragment = ExerciseListFragment()
            val bundle = Bundle()
            bundle.putInt(CHAPTER_BUNDLE_ID, chapterId)
            bundle.putString(CHAPTER_TITLE, chapterTitle)
            fragment.arguments = bundle
            return fragment
        }

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
            inflater.inflate(R.layout.layout_exercise_list_fragment, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val chapterId = arguments?.getInt(CHAPTER_BUNDLE_ID)
        val chapterTitle = arguments?.getString(CHAPTER_TITLE)
        /**
         * Call API for recommended questions.
         */
        progressExerciseList.visibility = View.VISIBLE
        textNoExerciseAvailable.visibility = View.GONE

        toolbarTitle.text = chapterTitle
        backButton.setOnClickListener { (activity as Home).popBackStack() }
        activity?.let {
            val user = sharedPrefManager.getUser(it)
            RetrofitUtils.initRetrofit(ApiInterface::class.java).getExercises(token = user.tocken,
                    sylId = user.syl_id.toInt(),
                    classId = user.cls_id.toInt(),
                    chapId = chapterId!!,
                    subId = 1
            )
//                    sylId = 1,
//                    classId = 10,
//                    subId = 1)

                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({ exercises ->
                        if (progressExerciseList == null) {
                            return@subscribe
                        }
                        progressExerciseList?.visibility = View.GONE
                        if (exercises.response_type == getString(R.string.response_type_error)) {
                            textNoExerciseAvailable.visibility = View.VISIBLE
                            Snackbar.make(view, exercises.response_text, Snackbar.LENGTH_SHORT).show()
                            return@subscribe
                        }
                        textNoExerciseAvailable.visibility = View.GONE
                        exerciseRecycler.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                        exerciseRecycler.addItemDecoration(DividerItemDecoration(context, LinearLayoutManager.VERTICAL))
                        exerciseRecycler.adapter = ExerciseTopicAdapter(exerciseList = exercises.response_data)
                    }, { error ->
                        progressExerciseList?.visibility = View.GONE
                        textNoExerciseAvailable.visibility = View.VISIBLE
                        Snackbar.make(view, R.string.something_went_wrong, Snackbar.LENGTH_SHORT).show()
                        error.printStackTrace()
                    })
        }
    }

}
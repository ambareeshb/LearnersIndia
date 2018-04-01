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
import com.gbmainframe.learnersindia.adapters.RecommendedQuestionsAdapter
import com.gbmainframe.learnersindia.utils.ApiInterface
import com.gbmainframe.learnersindia.utils.RetrofitUtils
import com.gbmainframe.learnersindia.utils.sharedPrefManager
import kotlinx.android.synthetic.main.layout_list_questions.*
import kotlinx.android.synthetic.main.simple_toolbar.*
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

/**
 * Created by ambareeshb on 28/03/18.
 */
class QuestionListFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
            inflater.inflate(R.layout.layout_list_questions, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        toolbarTitle.text = getString(R.string.questions)
        backButton.setOnClickListener { (activity as Home).popBackStack() }

        /**
         * Call API for recommended questions.
         */
        progress.visibility = View.VISIBLE



        activity?.let {
            val user = sharedPrefManager.getUser(it)
            RetrofitUtils.initRetrofit(ApiInterface::class.java).getRecommendedQuestions(
                    user.syl_id.toInt(),
                    1,
                    user.cls_id.toInt())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({ questions ->
                        progress?.visibility = View.GONE
                        if (questions == null || questions.response_type == getString(R.string.response_type_error)) {
                            textNoQuestionAvailable.visibility = View.VISIBLE
                            return@subscribe
                        }
                        textNoQuestionAvailable.visibility = View.GONE
                        recyclerRecommended.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                        recyclerRecommended.addItemDecoration(DividerItemDecoration(context, LinearLayoutManager.VERTICAL))
                        recyclerRecommended.adapter = RecommendedQuestionsAdapter(questions.questions_data){ questionId ->
                            (activity as Home).loadAnswerListFragment(questionId)
                        }
                    }, { error ->
                        progress?.visibility = View.GONE
                        Snackbar.make(view, R.string.something_went_wrong, Snackbar.LENGTH_SHORT).show()
                        error.printStackTrace()
                    })
        }

    }

}
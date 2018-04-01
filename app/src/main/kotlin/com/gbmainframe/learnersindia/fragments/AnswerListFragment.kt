package com.gbmainframe.learnersindia.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.gbmainframe.learnersindia.R
import com.gbmainframe.learnersindia.activities.Home
import com.gbmainframe.learnersindia.adapters.AnswerAdapter
import com.gbmainframe.learnersindia.utils.ApiInterface
import com.gbmainframe.learnersindia.utils.RetrofitUtils
import com.gbmainframe.learnersindia.utils.sharedPrefManager
import kotlinx.android.synthetic.main.layout_answer_list.*
import kotlinx.android.synthetic.main.simple_toolbar.*
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

/**
 * Created by ambareeshb on 01/04/18.
 */
class AnswerListFragment : Fragment() {
    companion object {
        const val ANSWER_BUNDLE_KEY = "ANSWER_KEY"
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
            inflater.inflate(R.layout.layout_answer_list, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        toolbarTitle.text = getString(R.string.answers)
        backButton.setOnClickListener { (activity as Home).popBackStack() }
        //Api to list chapters
        progress.visibility = View.VISIBLE
        arguments?.getInt(ANSWER_BUNDLE_KEY)?.let { questionId ->
            val user = sharedPrefManager.getUser(activity!!)
            RetrofitUtils.initRetrofit(ApiInterface::class.java).getAllAnswers(questionId).subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({
                        progress.visibility = View.GONE
                        if (it == null || it.response_type == getString(R.string.response_type_error)) {
                            textNoAnswersAvailable.visibility = View.VISIBLE
                            return@subscribe
                        }
                        textNoAnswersAvailable.visibility = View.GONE
                        recyclerChapters.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                        recyclerChapters.addItemDecoration(DividerItemDecoration(context, LinearLayoutManager.VERTICAL))
                        recyclerChapters.adapter = AnswerAdapter(it.answer_data)

                    }, {
                        progress.visibility = View.GONE
                        textNoAnswersAvailable.visibility = View.VISIBLE
                        it.printStackTrace()
                    })

        }
    }
}
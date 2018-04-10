package com.gbmainframe.learnersindia.fragments

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.os.bundleOf
import com.gbmainframe.learnersindia.R
import com.gbmainframe.learnersindia.activities.TestActivity
import com.gbmainframe.learnersindia.adapters.TestQuestionsRecyclerAdapter
import com.gbmainframe.learnersindia.models.AnswerSelectModel
import com.gbmainframe.learnersindia.models.TestModel
import com.gbmainframe.learnersindia.models.TestQuestionModel
import com.gbmainframe.learnersindia.utils.ApiInterface
import com.gbmainframe.learnersindia.utils.RetrofitUtils
import com.gbmainframe.learnersindia.utils.sharedPrefManager
import kotlinx.android.synthetic.main.layout_question_list.*
import kotlinx.android.synthetic.main.simple_toolbar.*
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

/**
 * Created by ambareeshb on 09/04/18.
 */
class TestQuestionListFragment : Fragment() {
    private var currentPostions = 0
    private lateinit var questions: ArrayList<TestQuestionModel>

    companion object {
        private const val BUNDLE_TEST_MODEL = "BUNDLE_TEST"
        fun newInstance(test: TestModel) =
                TestQuestionListFragment().apply {
                    arguments = bundleOf(BUNDLE_TEST_MODEL to test)
                }
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
            inflater.inflate(R.layout.layout_question_list, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        toolbarTitle.text = "Tests"
        backButton.setOnClickListener {
            (activity as TestActivity).onBackPressed()
        }
        activity?.let {
            val user = sharedPrefManager.getUser(it)
            val test = arguments?.getParcelable(BUNDLE_TEST_MODEL) as TestModel

            layoutTest.visibility = View.GONE
            progressTest.visibility = View.VISIBLE

            RetrofitUtils.initRetrofit(ApiInterface::class.java).getTestQuestions("c02a814cfd80a7d555e27ad831c93d85",
                    classId = 10,
                    sylId = 1,
                    chapId = 1,
                    subId = 1
//                    user.syl_id.toInt(),
//                    user.cls_id.toInt(),
//                    test.chp_id.toInt()

            ).subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({
                        if (progressTest == null || it == null || it.response_type == getString(R.string.response_type_error) || it.response_data == null) {
                            Snackbar.make(view, it.response_text, Snackbar.LENGTH_SHORT).show()
                            return@subscribe
                        }
                        questions = it.response_data
                        val question = it.response_data[0]
                        val mimeType = "text/html"
                        val encoding = "UTF-8"
                        testQuestion.loadDataWithBaseURL("", "<b>${question.test_question}</b>", mimeType, encoding, "")
                        testRecycler.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                        testRecycler.addItemDecoration(DividerItemDecoration(context, LinearLayoutManager.VERTICAL))
                        testRecycler.adapter = TestQuestionsRecyclerAdapter(question)

                        layoutTest.visibility = View.VISIBLE
                        progressTest.visibility = View.GONE
                        buttonNext.setOnClickListener {
                            loadNextQuestion()
                        }
                    }, {
                        it.printStackTrace()
                    })
        }
    }

    fun loadNextQuestion() {
        currentPostions++
        if (questions.size == currentPostions) {
            (activity as TestActivity).loadTestFinishFragment()
            return
        }
        val question = questions[currentPostions]
        (testRecycler.adapter as TestQuestionsRecyclerAdapter).setNextOptions(question)

    }
}
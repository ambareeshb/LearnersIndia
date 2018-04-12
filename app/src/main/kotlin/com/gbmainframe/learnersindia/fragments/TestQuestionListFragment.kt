package com.gbmainframe.learnersindia.fragments

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.os.bundleOf
import com.gbmainframe.learnersindia.R
import com.gbmainframe.learnersindia.activities.TestActivity
import com.gbmainframe.learnersindia.adapters.TestQuestionsRecyclerAdapter
import com.gbmainframe.learnersindia.models.ChapterModel
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
    private var correctAnswers = 0
    private var wrongAnswers = 0
    private var skippedAnswers = 0
    private var totalMark = 0
    private var testTotalMark = 0


    private var currentPostions = 0
    private lateinit var questions: ArrayList<TestQuestionModel>

    companion object {
        private const val BUNDLE_TEST_MODEL = "BUNDLE_TEST"
        fun newInstance(chapter: ChapterModel) =
                TestQuestionListFragment().apply {
                    arguments = bundleOf(BUNDLE_TEST_MODEL to chapter)
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
            val test = arguments?.getParcelable(BUNDLE_TEST_MODEL) as ChapterModel

            layoutTest.visibility = View.GONE
            progressTest.visibility = View.VISIBLE

            RetrofitUtils.initRetrofit(ApiInterface::class.java).getTestQuestions(user.tocken,
                    subId = 1,
                    sylId = user.syl_id.toInt(),
                    classId = user.cls_id.toInt(),
                    chapId = test.chp_id

            ).subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({
                        if (progressTest == null || it == null || it.response_type == getString(R.string.response_type_error) || it.response_data == null) {
                            Snackbar.make(view, it.response_text, Snackbar.LENGTH_SHORT).show()
                            return@subscribe
                        }
                        questions = it.response_data
                        testTotalMark = it.test_total_marks
                        val question = it.response_data[0]
                        val mimeType = "text/html"
                        val encoding = "UTF-8"
                        testQuestion.loadDataWithBaseURL("", "<b>${question.test_question}</b>", mimeType, encoding, "")
                        testRecycler.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                        testRecycler.addItemDecoration(DividerItemDecoration(context, LinearLayoutManager.VERTICAL))
                        testRecycler.adapter = TestQuestionsRecyclerAdapter(question,
                                { showAnswerField, answerData ->
                                    if (showAnswerField) testQuestionAnswerLayout.visibility = View.VISIBLE
                                    else testQuestionAnswerLayout.visibility = View.GONE
                                    testQuestionSolution.loadDataWithBaseURL("", "<b>$answerData</b>", mimeType, encoding, "")
                                }, { answerStatus ->
                            when (answerStatus.answerStatus) {
                                TestAnswerStatus.RIGHT -> {
                                    correctAnswers++
                                    totalMark += answerStatus.marks
                                }
                                TestAnswerStatus.SKIP -> skippedAnswers++
                                TestAnswerStatus.WRONG -> wrongAnswers++
                            }
                        })


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

    private fun loadNextQuestion() {
        currentPostions++
        if (questions.size <= currentPostions) {
            val user = sharedPrefManager.getUser(activity!!)
            val test = arguments?.getParcelable(BUNDLE_TEST_MODEL) as ChapterModel
            RetrofitUtils.initRetrofit(ApiInterface::class.java).submitTestPaper(
                    token = user.tocken,
                    chapterId = test.chp_id,
                    testTotal = testTotalMark,
                    studentTotal = totalMark,
                    rightAnswer = correctAnswers,
                    wrongAnswer = wrongAnswers,
                    skipped = (questions.size - (correctAnswers + wrongAnswers)),
                    testStatus = "end"
            )
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({
                        if (it.response_type == getString(R.string.response_type_error)) {
                            Snackbar.make(view!!, "Unable to submit test", Snackbar.LENGTH_SHORT).show()
                            return@subscribe
                        }
                        (activity as TestActivity).loadTestFinishFragment(totalMark,
                                correctAnswers,
                                wrongAnswers,
                                (questions.size - (correctAnswers + wrongAnswers)))
                    }, {
                        it.printStackTrace()
                    })
        } else {
            val question = questions[currentPostions]
            val mimeType = "text/html"
            val encoding = "UTF-8"
            questionMarks.text = String.format(getString(R.string.question_marks), question.mark.toInt())
            testQuestion.loadDataWithBaseURL("", "<b>${question.test_question}</b>", mimeType, encoding, "")
            (testRecycler.adapter as TestQuestionsRecyclerAdapter).setNextOptions(question)
        }

    }

    /**
     * Calculate Test answer status.
     */
    fun calculateTestStatus(answerStatus: AnswerStatus): Unit {
    }


    enum class TestAnswerStatus {
        RIGHT, WRONG, SKIP
    }

    public data class AnswerStatus(val answerStatus: TestAnswerStatus, val marks: Int)
}
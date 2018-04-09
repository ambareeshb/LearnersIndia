package com.gbmainframe.learnersindia.models

/**
 * Created by ambareeshb on 09/04/18.
 */
data class TestQuestionModel(val order_number: Int,
                             val test_question_id: Int,
                             val test_question: String,
                             val option1: String,
                             val option2: String,
                             val option3: String,
                             val option4: String,
                             val correct_answer: Int,
                             val mark: String,
                             val answer_solution: String)
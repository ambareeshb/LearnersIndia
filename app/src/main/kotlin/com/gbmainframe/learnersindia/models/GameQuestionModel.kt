package com.gbmainframe.learnersindia.models

/**
 * Created by ambareeshb on 10/04/18.
 */
data class GameQuestionModel(val question_id: Int,
                             val question: String,
                             val option1: String,
                             val option2: String,
                             val option3: String,
                             val option4: String,
                             val answer: Int,
                             val voice1: String,
                             val voice2: String)
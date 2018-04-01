package com.gbmainframe.learnersindia.models

/**
 * Created by ambareeshb on 01/04/18.
 */
data class SearchModel(val question_ask_data:ArrayList<RecommendedQuestions>?):BaseApiModel()

data class QuestionAskData(val qst_id:Int,
                           val qst_title:String,
                           val qst_timestamp:String,
                           val student:String,
                           val profile_pic:String,
                           val subjectname:String,
                           val gradename:String,
                           val chapter: String)

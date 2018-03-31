package com.gbmainframe.learnersindia.models

/**
 * Created by ambareeshb on 27/03/18.
 */
data class RecommendedQuestions(val qst_id: Int,
                                val qst_title: String,
                                val qst_timestamp:String,
                                val student:String,
                                val profile_pic:String,
                                val subjectname:String,
                                val boardname:String,
                                val gradename:String,
                                val chapter:String)

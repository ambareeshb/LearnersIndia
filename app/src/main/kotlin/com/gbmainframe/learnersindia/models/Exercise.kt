package com.gbmainframe.learnersindia.models

/**
 * Created by ambareeshb on 05/04/18.
 */
data class Exercise(val exercise_id: Int,
                    val exercise_title: String,
                    val chapter_name: String,
                    val exercise_solution: String,
                    val boardname: String,
                    val gradename: String,
                    val subjectname: String)
package com.gbmainframe.learnersindia.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * Created by ambareeshb on 09/04/18.
 */
@Parcelize
data class TestModel(val rslt_id: Int,
                     val chapter: String,
                     val student_total_mark: String,
                     val test_total_questions:Int,
                     val chp_id: String,
                     val test_date: Long,
                     val right_answer: Int,
                     val wrong_answer: Int,
                     val skipped_answers: Int,
                     val test_status: String,
                     val test_total_marks: Int) : Parcelable
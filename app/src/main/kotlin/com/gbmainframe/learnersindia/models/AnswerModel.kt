package com.gbmainframe.learnersindia.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * Created by ambareeshb on 31/03/18.
 */
@Parcelize
data class AnswerModel(val full_name: String,
                       val qst_answer: String,
                       val asw_image: String,
                       val asw_timestamp: Long,
                       val full_name_sub: String,
                       val qst_answer_sub: String,
                       val asw_image_sub: String,
                       val asw_timestamp_sub: Long):Parcelable
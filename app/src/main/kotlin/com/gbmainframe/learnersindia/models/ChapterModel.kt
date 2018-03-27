package com.gbmainframe.learnersindia.models

/**
 * Created by ambareeshb on 27/03/18.
 */
data class ChapterModel(
        val response_type: String,
        val response_code: String,
        val response_text: String,
        val syl_id: Int,
        val cls_id: Int,
        val sub_id: Int,
        val chp_id: Int,
        val chapter: String
)
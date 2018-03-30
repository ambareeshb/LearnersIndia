package com.gbmainframe.learnersindia.models

/**
 * Created by ambareeshb on 30/03/18.
 */
data class VideoModel(val ved_id: Int,
                      val video_id: String,
                      val chp_id: Int,
                      val syl_id: Int,
                      val cls_id: Int,
                      val sub_id: Int,
                      val ved_title: String,
                      val ved_type: String,
                      val ved_url: String,
                      val ved_category: String,
                      val ved_desc: String,
                      val ved_tags: String,
                      val chapter: String,
                      val boardname: String,
                      val gradename: String,
                      val subjectname: String)
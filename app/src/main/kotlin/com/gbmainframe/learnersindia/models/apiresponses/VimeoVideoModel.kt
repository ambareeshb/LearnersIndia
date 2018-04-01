package com.gbmainframe.learnersindia.models.apiresponses

/**
 * Created by ambareeshb on 31/03/18.
 */
data class VimeoVideoModel(val title: String,
                           val description: String,
                           val url: String,
                           val upload_date: String,
                           val stats_number_of_likes: String,
                           val stats_number_of_comments: String,
                           val duration: String,
                           val thumbnail_large: String,
                           val thumbnail_medium: String)
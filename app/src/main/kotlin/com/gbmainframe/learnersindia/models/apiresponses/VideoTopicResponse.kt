package com.gbmainframe.learnersindia.models.apiresponses

import com.gbmainframe.learnersindia.models.VideoModel

/**
 * Created by ambareeshb on 30/03/18.
 */
data class VideoTopicResponse(val topic_id: Int,
                              val topic_name: String,
                              val video_data: ArrayList<VideoModel>)
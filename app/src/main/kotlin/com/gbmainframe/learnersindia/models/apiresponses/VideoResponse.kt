package com.gbmainframe.learnersindia.models.apiresponses

import com.gbmainframe.learnersindia.models.BaseApiModel
import com.gbmainframe.learnersindia.models.VideoModel

/**
 * Created by ambareeshb on 30/03/18.
 */
data class VideoResponse(val response_data:ArrayList<VideoTopicResponse>):BaseApiModel()
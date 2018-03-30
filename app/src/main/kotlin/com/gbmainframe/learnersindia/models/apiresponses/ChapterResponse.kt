package com.gbmainframe.learnersindia.models.apiresponses

import com.gbmainframe.learnersindia.models.BaseApiModel
import com.gbmainframe.learnersindia.models.ChapterModel

/**
 * Created by ambareeshb on 30/03/18.
 */
data class ChapterResponse(val chapters_data:ArrayList<ChapterModel>):BaseApiModel()
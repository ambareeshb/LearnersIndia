package com.gbmainframe.learnersindia.models.apiresponses

import com.gbmainframe.learnersindia.models.AnswerModel
import com.gbmainframe.learnersindia.models.BaseApiModel

/**
 * Created by ambareeshb on 31/03/18.
 */
data class AnswerResponse(val answer_data:ArrayList<AnswerModel>):BaseApiModel()
package com.gbmainframe.learnersindia.models.apiresponses

import com.gbmainframe.learnersindia.models.BaseApiModel
import com.gbmainframe.learnersindia.models.GameQuestionModel

/**
 * Created by ambareeshb on 10/04/18.
 */
data class GameQuestionResponse(val question_data: GameQuestionModel) : BaseApiModel()
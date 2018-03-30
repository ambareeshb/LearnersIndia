package com.gbmainframe.learnersindia.models.apiresponses

import com.gbmainframe.learnersindia.models.BaseApiModel
import com.gbmainframe.learnersindia.models.RecommendedQuestions

/**
 * Created by ambareeshb on 30/03/18.
 */
data class RecommendedQuestionResponse(val question_data: ArrayList<RecommendedQuestions>):BaseApiModel()
package com.gbmainframe.learnersindia.models.apiresponses

import com.gbmainframe.learnersindia.models.BaseApiModel
import com.gbmainframe.learnersindia.models.Exercise

/**
 * Created by ambareeshb on 05/04/18.
 */
data class ExerciseResponse(val response_data: ArrayList<Exercise>):BaseApiModel()
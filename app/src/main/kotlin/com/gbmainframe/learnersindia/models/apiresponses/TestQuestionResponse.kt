package com.gbmainframe.learnersindia.models.apiresponses

import com.gbmainframe.learnersindia.models.BaseApiModel
import com.gbmainframe.learnersindia.models.TestQuestionModel

/**
 * Created by ambareeshb on 09/04/18.
 */
data class TestQuestionResponse(val response_data:ArrayList<TestQuestionModel>,val test_total_marks:Int):BaseApiModel()
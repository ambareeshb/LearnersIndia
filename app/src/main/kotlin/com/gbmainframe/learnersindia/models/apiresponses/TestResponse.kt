package com.gbmainframe.learnersindia.models.apiresponses

import com.gbmainframe.learnersindia.models.BaseApiModel
import com.gbmainframe.learnersindia.models.TestModel

/**
 * Created by ambareeshb on 09/04/18.
 */
data class TestResponse(val response_data:ArrayList<TestModel>):BaseApiModel()
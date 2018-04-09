package com.gbmainframe.learnersindia.models.apiresponses

import com.gbmainframe.learnersindia.models.Exercise

/**
 * Created by ambareeshb on 09/04/18.
 */
data class ExerciseTopicResponse(val topic_id:Int,
                                 val topic_name:String,
                                 val exercise_data:ArrayList<Exercise>)
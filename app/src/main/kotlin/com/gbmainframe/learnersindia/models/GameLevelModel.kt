package com.gbmainframe.learnersindia.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * Created by ambareeshb on 10/04/18.
 */
@Parcelize
data class GameLevelModel(val level_id: Int,
                          val level_name: String,
                          val level_score: Int) : Parcelable
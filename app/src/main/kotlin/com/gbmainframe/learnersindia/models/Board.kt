package com.gbmainframe.learnersindia.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * Created by ambareeshb on 25/03/18.
 */

@Parcelize
data class Board(val syl_id:String,val syllabus:String,val icon:String):Parcelable
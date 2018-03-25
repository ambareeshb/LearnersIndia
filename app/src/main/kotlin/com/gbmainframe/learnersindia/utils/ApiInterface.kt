package com.gbmainframe.learnersindia.utils

import com.gbmainframe.learnersindia.models.Board
import com.gbmainframe.learnersindia.models.ClassInfo
import retrofit2.http.GET

/**
 * Created by ambareeshb on 25/03/18.
 */
interface ApiInterface{
    @GET("get-board")
    fun getAvailableBoards():rx.Observable<ArrayList<Board>>
    @GET("get-class")
    fun getAvailableClasses():rx.Observable<ArrayList<ClassInfo>>
}
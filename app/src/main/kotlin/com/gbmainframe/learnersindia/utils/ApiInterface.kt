package com.gbmainframe.learnersindia.utils

import com.gbmainframe.learnersindia.models.Board
import com.gbmainframe.learnersindia.models.ClassInfo
import com.gbmainframe.learnersindia.models.SignInResponse
import com.gbmainframe.learnersindia.models.SignUpResponse
import retrofit2.http.GET
import retrofit2.http.Query
import rx.Observable

/**
 * Created by ambareeshb on 25/03/18.
 */
interface ApiInterface {
    @GET("get-board")
    fun getAvailableBoards(): rx.Observable<ArrayList<Board>>

    @GET("get-class")
    fun getAvailableClasses(): rx.Observable<ArrayList<ClassInfo>>

    @GET("signup")
    fun signUp(@Query("usertype") userType: String,
               @Query("fullname") name: String,
               @Query("email") email: String,
               @Query("mobile") phone: String,
               @Query("password") password: String,
               @Query("board") board: String,
               @Query("class") classInfo: String
    ): rx.Observable<SignUpResponse>

    @GET("signin")
    fun signIn(@Query("usertype") userType: String,
               @Query("loginid") loginId: String,
               @Query("password") password: String
    ):Observable<SignInResponse>

    @GET("get-free-videos")
    fun getFreeVideos()
}
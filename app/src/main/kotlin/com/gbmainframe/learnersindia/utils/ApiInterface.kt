package com.gbmainframe.learnersindia.utils

import com.gbmainframe.learnersindia.models.*
import com.gbmainframe.learnersindia.models.apiresponses.ChapterResponse
import com.gbmainframe.learnersindia.models.apiresponses.PaymentResponse
import com.gbmainframe.learnersindia.models.apiresponses.RecommendedQuestionResponse
import com.gbmainframe.learnersindia.models.apiresponses.VideoResponse
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
    ): Observable<SignInResponse>

    @GET("reccomented-questions")
    fun getRecommendedQuestions(@Query("syl_id") sylId: Int,
                                @Query("sub_id") subId: Int,
                                @Query("class_id") classId: Int): Observable<RecommendedQuestionResponse>

    @GET("get-videos")
    fun getVideos(@Query("tocken") token: String,
                  @Query("syl_id") sylId: Int,
                  @Query("class_id") classId: Int,
                  @Query("sub_id") subId: Int,
                  @Query("chap_id") chapId: Int): Observable<VideoResponse>


    @GET("ask-question")
    fun askQuestion(@Query("tocken") token: String,
                    @Query("syl_id") sylId: Int,
                    @Query("class_id") classId: Int,
                    @Query("sub_id") subId: Int = 1,
                    @Query("chap_id") chapId: Int = 1,
                    @Query("q_title") title: String = "Mathematics",
                    @Query("q_details") details: String): Observable<BaseApiModel>

    @GET("get-chapters")
    fun getChapters(@Query("tocken") token: String,
                    @Query("syl_id") sylId: Int,
                    @Query("class_id") classId: Int,
                    @Query("sub_id") subId: Int = 1): Observable<ChapterResponse>

    @GET("check-paid-status")
    fun checkPaidStatus(@Query("tocken") token: String): Observable<PaymentResponse>

}
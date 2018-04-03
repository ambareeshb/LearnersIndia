package com.gbmainframe.learnersindia.utils

import com.gbmainframe.learnersindia.models.*
import com.gbmainframe.learnersindia.models.apiresponses.*
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import rx.Observable

/**
 * Created by ambareeshb on 25/03/18.
 * To handel API requests.
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
                  @Query("chap_id") chapId: Int): Observable<VideoResponsePaid>

    @GET("get-free-videos")
    fun getFreeVideos(
            @Query("syl_id") sylId: Int,
            @Query("class_id") classId: Int,
            @Query("sub_id") subId: Int): Observable<VideoResponse>

    @GET("https://api.vimeo.com/videos/{video_id}")
    fun getVimeoVideoMetadata(@Path("video_id") videoId: String): Observable<VimeoVideoModel> //Video id should contain o/p format eg: 6271487.json


    @GET("ask-question")
    fun askQuestion(@Query("tocken") token: String,
                    @Query("syl_id") sylId: Int,
                    @Query("class_id") classId: Int,
                    @Query("sub_id") subId: Int = 1,
                    @Query("chap_id") chapId: Int = 1,
                    @Query("q_title") title: String = "Mathematics",
                    @Query("q_details") details: String): Observable<BaseApiModel>

    @GET("get-all-answers")
    fun getAllAnswers(@Query("qstn_id") questionId: Int): Observable<AnswerResponse>

    @GET("get-chapters")
    fun getChapters(@Query("tocken") token: String,
                    @Query("syl_id") sylId: Int,
                    @Query("class_id") classId: Int,
                    @Query("sub_id") subId: Int = 1): Observable<ChapterResponse>

    @GET("check-paid-status")
    fun checkPaidStatus(@Query("tocken") token: String): Observable<PaymentResponse>

    @GET("search")
    fun search(@Query("keyword") keyword: String,
               @Query("syl_id") sylId: Int,
               @Query("class_id") classId: Int,
               @Query("sub_id") subId: Int): Observable<SearchModel>

}
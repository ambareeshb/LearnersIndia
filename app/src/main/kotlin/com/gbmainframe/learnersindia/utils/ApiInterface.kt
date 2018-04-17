package com.gbmainframe.learnersindia.utils

import com.gbmainframe.learnersindia.models.*
import com.gbmainframe.learnersindia.models.apiresponses.*
import retrofit2.http.GET
import retrofit2.http.Header
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
               @Query("class") classInfo: String,
               @Query("country_tel_code") telCode: String

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

    @GET("videos/{video_id}")
    fun getVimeoVideoMetadata(@Header("Authorization") vimeoToken: String,
                              @Path("video_id") videoId: String): Observable<VimeoVideoModel> //Video id should contain o/p format eg: 6271487.json


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

    @GET("get-exercises")
    fun getExercises(@Query("tocken") token: String,
                     @Query("syl_id") sylId: Int,
                     @Query("class_id") classId: Int,
                     @Query("sub_id") subId: Int = 1,
                     @Query("chap_id") chapId: Int = 1): Observable<ExerciseResponsePaid>

    //Test
    @GET("get-testpapers")
    fun getTests(@Query("tocken") token: String): Observable<TestResponse>


    @GET("get-testpaper-questions")
    fun getTestQuestions(@Query("tocken") token: String,
                         @Query("syl_id") sylId: Int,
                         @Query("class_id") classId: Int,
                         @Query("sub_id") subId: Int = 1,
                         @Query("chap_id") chapId: Int = 1): Observable<TestQuestionResponse>

    @GET("submit-testpaper")
    fun submitTestPaper(
            @Query("tocken") token: String = "ced19e81e72cf65b9fd872c3151aaaa2",
            @Query("chap_id") chapterId: Int,
            @Query("out_of_marks") testTotal: Int,
            @Query("total_mark") studentTotal: Int,
            @Query("right_answer") rightAnswer: Int,
            @Query("wrong_answer") wrongAnswer: Int,
            @Query("skipped") skipped: Int,
            @Query("test_status") testStatus: String): Observable<BaseApiModel>

    //Game
    @GET("get-game-level")
    fun getGameLevel(): Observable<ArrayList<GameLevelModel>>

    @GET("get-game-question")
    fun getGameQuestion(@Query("tocken") tocken: String,
                        @Query("level") level: Int): Observable<GameQuestionResponse>

    @GET("submit-game-result")
    fun submitGameResult(@Query("tocken") token: String,
                         @Query("game_status") gameStatus: String,
                         @Query("total_point") point: Int,
                         @Query("life_line1") lifeLine1: Int,
                         @Query("life_line2") lifeLine2: Int): Observable<BaseApiModel>

    //Payment gateway
    @GET("get-packages")
    fun getPackages(): Observable<ArrayList<PaymentPackage>>

    //user profile
    @GET("get-user-profile")

    fun getUserProfile(@Query("usertype") userType: String = "student",
                       @Query("tocken") token: String): Observable<UserResponse>

    //Otp
    @GET("validate-otp-student-signup")
    fun submitOtp(@Query("tocken") token: String,
                  @Query("otp") otp:String): Observable<BaseApiModel>

    @GET("resend-otp-student-signup")
    fun resendOtp(@Query("tocken") token: String): Observable<BaseApiModel>

}
package com.gbmainframe.learnersindia.models

/**
 * Created by ambareeshb on 26/03/18.
 */
data class SignUpResponse(val response_type:String,
                          val response_code:String,
                          val response_text:String,
                          val email:String,
                          val mobile:String,
                          val user_id:String,
                          val otp:String,
                          val tocken:String)
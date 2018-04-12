package com.gbmainframe.learnersindia.models

/**
 * Created by ambareeshb on 26/03/18.
 */
data class SignInResponse(val response_type: String,
                          val response_code: String,
                          val response_text: String,
                          val user_data: UserData)

data class UserData(val full_name: String,
                    val email_id: String,
                    val phoneno: String,
                    val cls_id: String,
                    val syl_id: String,
                    val profile_pic: String,
                    val verification_key: String,
                    val tocken: String,
                    val address: String,
                    val city: String,
                    val state: String,
                    val dob: String,
                    val gender: String,
                    val paidstatus: String)

data class UserResponse(val user_data: UserData) : BaseApiModel()
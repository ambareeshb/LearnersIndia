package com.gbmainframe.learnersindia.utils

import android.content.Context
import android.content.Context.MODE_PRIVATE
import com.gbmainframe.learnersindia.models.UserData
import com.google.gson.Gson

/**
 * Created by ambareeshb on 18/03/18.
 * Manages all operations with shared preferences.
 */

object sharedPrefManager {
    private const val SHARED_PREFERENCE_NAME = "LEARNERS_INDIA"
    private const val USER_SIGNED_IN = "SIGN_IN"
    private const val USER_DATA = "USER_DATA"
    private const val PAYMENT_SUCCESS = "PAYMENT_SUCCESS"

    private fun getSharedPreference(context: Context) =
            context.getSharedPreferences(SHARED_PREFERENCE_NAME, MODE_PRIVATE)

    private fun loginUser(context: Context) =
            getSharedPreference(context).edit().putBoolean(USER_SIGNED_IN, true).apply()

    fun logoutUser(context: Context) =
            getSharedPreference(context).edit().putBoolean(USER_SIGNED_IN, false).apply()

    fun ifloggedIn(context: Context) = getSharedPreference(context).getBoolean(USER_SIGNED_IN, false)

    fun putUserInfo(context: Context, user: UserData) {
        val userJson = Gson().toJson(user)
        getSharedPreference(context).edit().putString(USER_DATA, userJson).commit()
        loginUser(context)
    }

    fun paymentSuccess(context: Context) =
            getSharedPreference(context).edit().putBoolean(PAYMENT_SUCCESS, true).apply()

    fun getUser(context: Context): UserData {
        val userJson = getSharedPreference(context).getString(USER_DATA, "")
        return Gson().fromJson(userJson, UserData::class.java)
    }

}
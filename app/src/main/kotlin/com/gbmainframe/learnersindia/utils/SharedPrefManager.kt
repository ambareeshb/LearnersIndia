package com.gbmainframe.learnersindia.utils

import android.content.Context
import android.content.Context.MODE_PRIVATE

/**
 * Created by ambareeshb on 18/03/18.
 * Manages all operations with shared preferences.
 */

object sharedPrefManager{
    val SHARED_PREFERENCE_NAME = "LEARNERS_INDIA"
    val USER_SIGNED_IN = "SIGN_IN"
    fun getSharedPreference(context: Context) =
           context.getSharedPreferences(SHARED_PREFERENCE_NAME, MODE_PRIVATE)
    public fun loginUser(context: Context) =
            getSharedPreference(context).edit().putBoolean(USER_SIGNED_IN,true).apply()
    public fun logoutUser(context: Context) =
            getSharedPreference(context).edit().putBoolean(USER_SIGNED_IN,false).apply()
    public fun ifloggedIn(context: Context) = getSharedPreference(context).getBoolean(USER_SIGNED_IN,false)

}
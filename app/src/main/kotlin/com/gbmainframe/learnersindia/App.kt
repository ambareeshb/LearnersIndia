package com.gbmainframe.learnersindia

import android.app.Application
import com.vimeo.networking.Configuration
import com.vimeo.networking.VimeoClient

/**
 * Created by ambareeshb on 22/03/18.
 */
class App : Application() {

    override fun onCreate() {
        super.onCreate()
        val uri = "/262504336"


//        VimeoClient.getInstance().authorizeWithClientCredentialsGrant(object : AuthCallback {
//            override fun success() {
//                Log.d("VimeoSuccess", "videoGot")
//            }
//
//            override fun failure(error: VimeoError?) {
//                error?.printStackTrace()
//            }
//        })


    }
}
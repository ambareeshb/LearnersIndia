package com.gbmainframe.learnersindia.activities

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import com.gbmainframe.learnersindia.R
import com.gbmainframe.learnersindia.utils.sharedPrefManager
import rx.Single
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import timber.log.Timber

/**
 * Created by ambareeshb on 18/03/18.
 * splash screen.
 */
class SplashActivity : AppCompatActivity() {
    private lateinit var handler: Handler
    private lateinit var runnable: Runnable


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        handler = Handler()
    }


    override fun onResume() {
        super.onResume()
        runnable = toHomeOrSignUp()
        handler.postDelayed(runnable, 1000)
    }

    override fun onPause() {
        super.onPause()
        handler.removeCallbacks(runnable)
    }


    /**
     *If there is a signed in user go to sign in screen
     * else go to sign up screen.
     */
    private fun toHomeOrSignUp(): Runnable = Runnable {
        if (sharedPrefManager.ifloggedIn(this)) {

        }
        else{

        }
    }

}

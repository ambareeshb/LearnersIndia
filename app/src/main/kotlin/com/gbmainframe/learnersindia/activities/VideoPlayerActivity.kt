package com.gbmainframe.learnersindia.activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import com.gbmainframe.learnersindia.R
import com.gbmainframe.learnersindia.utils.sharedPrefManager
import com.vimeo.networking.VimeoClient.getInstance
import com.vimeo.networking.callbacks.ModelCallback
import com.vimeo.networking.model.User
import com.vimeo.networking.model.Video
import com.vimeo.networking.model.error.VimeoError
import kotlinx.android.synthetic.main.activity_video_player.*

class VideoPlayerActivity : AppCompatActivity() {

    companion object {
        const val VIDEO_URI_BUNDLE_ID = "VIDEO_URI"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_video_player)
//        val userToken = sharedPrefManager.getUser(context = this).tocken
//        val userToken = "ced19e81e72cf65b9fd872c3151aaaa2"
//        val videoId = intent.extras.getString(VIDEO_URI_BUNDLE_ID)

//        val path = "http://learnersindia.com/video-api/$videoId/500/450/$userToken"

        val path = intent.extras.getString(VIDEO_URI_BUNDLE_ID)

        vimeoPlayer.visibility = View.GONE
        learnersProgress.visibility = View.VISIBLE

        vimeoPlayer.apply {
            webViewClient = object : WebViewClient() {
                override fun onPageFinished(view: WebView?, url: String?) {
                    learnersProgress.visibility = View.GONE
                    vimeoPlayer.visibility = View.VISIBLE
                }
            }
            loadUrl(path)

        }

//        getInstance().fetchCurrentUser(object : ModelCallback<User>(User::class.java) {
//            override fun success(t: User?) {
//                getInstance().fetchNetworkContent(path, object : ModelCallback<Video>(Video::class.java) {
//                    override fun success(video: Video?) {
//                        vimeoPlayer.apply {
//                            webViewClient = WebViewClient()
////                            settings.apply {
////                                javaScriptEnabled = true
////                                javaScriptCanOpenWindowsAutomatically = true
////                                pluginState = WebSettings.PluginState.ON
////                                builtInZoomControls = true
////                            }
//                            loadData(video?.embed?.html, "text/html", "utf-8")
//                            visibility = View.VISIBLE
//                            learnersProgress.visibility = View.GONE
//                        }
//                    }
//
//                    override fun failure(error: VimeoError?) {
//                        error?.printStackTrace()
//                    }
//                })
//
//            }
//
//            override fun failure(error: VimeoError?) {
//                error?.printStackTrace()
//            }
//        })
    }
}

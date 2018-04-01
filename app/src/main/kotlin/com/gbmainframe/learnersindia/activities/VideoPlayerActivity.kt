package com.gbmainframe.learnersindia.activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.webkit.WebSettings
import android.webkit.WebViewClient
import com.gbmainframe.learnersindia.R
import com.vimeo.networking.VimeoClient
import com.vimeo.networking.VimeoClient.*
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
        val uri = "/262504336"
        val path = "https://player.vimeo.com/video"
//        val uri = intent.extras.get(VIDEO_URI_BUNDLE_ID)


        getInstance().fetchCurrentUser(object : ModelCallback<User>(User::class.java) {
            override fun success(t: User?) {
                Log.d("VimeoSuccess", "videoGot")
                val videoUri = t?.videosConnection?.uri
                Log.d("VIMEO_SUCCESS", "${t?.videoCount}")
                getInstance().fetchNetworkContent(videoUri + uri, object : ModelCallback<Video>(Video::class.java) {
                    override fun success(video: Video?) {
                        vimeoPlayer.apply {
                            webViewClient = WebViewClient()
                            settings.apply {
                                javaScriptEnabled = true
                                javaScriptCanOpenWindowsAutomatically = true
                                pluginState = WebSettings.PluginState.ON
                                builtInZoomControls = true
                            }
                            loadData(video?.embed?.html, "text/html", "utf-8")
                        }
                    }

                    override fun failure(error: VimeoError?) {
                        error?.printStackTrace()
                    }
                })

            }

            override fun failure(error: VimeoError?) {
                error?.printStackTrace()
            }
        })
    }
}

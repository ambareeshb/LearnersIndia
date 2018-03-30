package com.gbmainframe.learnersindia.adapters

import android.media.MediaMetadataRetriever

/**
 * Created by ambareeshb on 30/03/18.
 */
object Util {
    fun createThumbNailFromVideoUrl(videoPath: String, timeInSeconds: Long) =
            MediaMetadataRetriever().apply {
                setDataSource(videoPath)
                //api time unit is micro seconds
            }.getFrameAtTime(timeInSeconds * 1000000,
                    MediaMetadataRetriever.OPTION_CLOSEST_SYNC)!!


}
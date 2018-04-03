package com.gbmainframe.learnersindia.models.apiresponses

/**
 * Created by ambareeshb on 31/03/18.
 */
data class VimeoVideoModel(val title: String,
                           val description: String,
                           val pictures: Pictures,
                           val files: ArrayList<File>)

data class Files(val files: ArrayList<File>)
data class Pictures(val sizes: ArrayList<Picture>)
data class Picture(val width: Int,
                   val height: Int,
                   val link: String,
                   val link_with_play_button: String)

data class File(val width: String,
                val height: String,
                val link: String)
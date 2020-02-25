package com.paweloot.nasaapod.util

object YouTubeUtils {

    fun extractVideoId(videoUrl: String) =
        videoUrl.removeSurrounding(
            "https://www.youtube.com/embed/",
            "?rel=0"
        )

    fun buildVideoThumbnailUrl(videoUrl: String): String {
        val videoId = extractVideoId(videoUrl)
        return "https://img.youtube.com/vi/${videoId}/maxresdefault.jpg"
    }
}
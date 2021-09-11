package com.application.rozaana.model


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep
import kotlinx.android.parcel.Parcelize

@Keep
data class PexelData(
    @SerializedName("id")
    val id: String?,
    @SerializedName("media")
    val media: List<Media?>?,
    @SerializedName("next_page")
    val nextPage: String?,
    @SerializedName("page")
    val page: Int?,
    @SerializedName("per_page")
    val perPage: Int?,
    @SerializedName("prev_page")
    val prevPage: String?,
    @SerializedName("total_results")
    val totalResults: Int?
) {
    @Keep
    data class Media(
        @SerializedName("avg_color")
        val avgColor: String?,
        @SerializedName("duration")
        val duration: Int?,
        @SerializedName("full_res")
        val fullRes: Any?,
        @SerializedName("height")
        val height: Int?,
        @SerializedName("id")
        val id: Int?,
        @SerializedName("image")
        val image: String?,
        @SerializedName("liked")
        val liked: Boolean?,
        @SerializedName("photographer")
        val photographer: String?,
        @SerializedName("photographer_id")
        val photographerId: Int?,
        @SerializedName("photographer_url")
        val photographerUrl: String?,
        @SerializedName("src")
        val src: Src?,
        @SerializedName("tags")
        val tags: List<Any?>?,
        @SerializedName("type")
        val type: String?,
        @SerializedName("url")
        val url: String?,
        @SerializedName("user")
        val user: User?,
        @SerializedName("video_files")
        val videoFiles: List<VideoFile?>?,
        @SerializedName("video_pictures")
        val videoPictures: List<VideoPicture?>?,
        @SerializedName("width")
        val width: Int?
    ) {
        @Keep
        data class Src(
            @SerializedName("landscape")
            val landscape: String?,
            @SerializedName("large")
            val large: String?,
            @SerializedName("large2x")
            val large2x: String?,
            @SerializedName("medium")
            val medium: String?,
            @SerializedName("original")
            val original: String?,
            @SerializedName("portrait")
            val portrait: String?,
            @SerializedName("small")
            val small: String?,
            @SerializedName("tiny")
            val tiny: String?
        )

        @Keep
        data class User(
            @SerializedName("id")
            val id: Int?,
            @SerializedName("name")
            val name: String?,
            @SerializedName("url")
            val url: String?
        )

        @Keep
        data class VideoFile(
            @SerializedName("file_type")
            val fileType: String?,
            @SerializedName("height")
            val height: Int?,
            @SerializedName("id")
            val id: Int?,
            @SerializedName("link")
            val link: String?,
            @SerializedName("quality")
            val quality: String?,
            @SerializedName("width")
            val width: Int?
        )

        @Keep
        data class VideoPicture(
            @SerializedName("id")
            val id: Int?,
            @SerializedName("nr")
            val nr: Int?,
            @SerializedName("picture")
            val picture: String?
        )
    }
}
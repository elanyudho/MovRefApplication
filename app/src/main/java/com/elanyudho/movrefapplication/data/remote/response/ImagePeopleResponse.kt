package com.elanyudho.movrefapplication.data.remote.response


import com.google.gson.annotations.SerializedName

data class ImagePeopleResponse(
    @SerializedName("id")
    val id: Int = 0,
    @SerializedName("profiles")
    val profiles: List<Profile> = listOf()
) {
    data class Profile(
        @SerializedName("aspect_ratio")
        val aspectRatio: Double = 0.0,
        @SerializedName("file_path")
        val filePath: String = "",
        @SerializedName("height")
        val height: Int = 0,
        @SerializedName("iso_639_1")
        val iso6391: Any? = Any(),
        @SerializedName("vote_average")
        val voteAverage: Double = 0.0,
        @SerializedName("vote_count")
        val voteCount: Int = 0,
        @SerializedName("width")
        val width: Int = 0
    )
}
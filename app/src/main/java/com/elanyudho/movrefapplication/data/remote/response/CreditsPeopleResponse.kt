package com.elanyudho.movrefapplication.data.remote.response


import com.google.gson.annotations.SerializedName

data class CreditsPeopleResponse(
    @SerializedName("cast")
    val cast: List<Cast> = listOf(),
    @SerializedName("id")
    val id: Int = 0
) {
    data class Cast(
        @SerializedName("adult")
        val adult: Boolean = false,
        @SerializedName("backdrop_path")
        val backdropPath: String? = "",
        @SerializedName("character")
        val character: String = "",
        @SerializedName("credit_id")
        val creditId: String = "",
        @SerializedName("genre_ids")
        val genreIds: List<Int> = listOf(),
        @SerializedName("id")
        val id: Int = 0,
        @SerializedName("order")
        val order: Int = 0,
        @SerializedName("original_language")
        val originalLanguage: String = "",
        @SerializedName("original_title")
        val originalTitle: String = "",
        @SerializedName("overview")
        val overview: String = "",
        @SerializedName("popularity")
        val popularity: Double = 0.0,
        @SerializedName("poster_path")
        val posterPath: String? = "",
        @SerializedName("release_date")
        val releaseDate: String = "",
        @SerializedName("title")
        val title: String = "",
        @SerializedName("video")
        val video: Boolean = false,
        @SerializedName("vote_average")
        val voteAverage: Double = 0.0,
        @SerializedName("vote_count")
        val voteCount: Int = 0
    )
}
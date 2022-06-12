package com.elanyudho.movrefapplication.domain.model

data class MovieItem(
    val movieId: Int?,
    val movieName: String?,
    val moviePoster: String?,
    val movieBackDrop: String?,
    val movieGenre: List<Int>?,
    val totalResults: Int?
)

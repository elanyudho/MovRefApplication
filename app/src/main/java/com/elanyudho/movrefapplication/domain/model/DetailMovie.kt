package com.elanyudho.movrefapplication.domain.model

data class DetailMovie(
    val movieId: Int?,
    val movieName: String?,
    val moviePoster: String?,
    val movieBackDrop: String?,
    val movieGenre: List<Genres> = listOf(),
    val movieOverview: String?,
    val movieRate: Double?,
    val movieDuration: Int?,
    val movieDate: String?,
    val movieStatus: String?

) {
    data class Genres(
        val id: Int?,
        val name: String
    )
}
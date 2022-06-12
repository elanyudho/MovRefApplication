package com.elanyudho.movrefapplication.data.remote.mapper

import com.elanyudho.core.abstraction.BaseMapper
import com.elanyudho.movrefapplication.data.remote.response.UpcomingMovieResponse
import com.elanyudho.movrefapplication.domain.model.MovieItem
import com.elanyudho.movrefapplication.utils.api.Constants
import com.elanyudho.movrefapplication.utils.api.Constants.BASE_URL_IMAGE

class UpcomingMovieMapper: BaseMapper<UpcomingMovieResponse, List<MovieItem>> {

    override fun mapToDomain(raw: UpcomingMovieResponse): List<MovieItem> {
        return raw.results.map {
            MovieItem(
                movieId = it.id,
                movieName = it.title,
                moviePoster = BASE_URL_IMAGE + it.posterPath,
                movieBackDrop = BASE_URL_IMAGE + it.backdropPath,
                movieGenre = it.genreIds,
                totalResults = raw.totalResults
            )
        }
    }

    override fun mapToRaw(domain: List<MovieItem>): UpcomingMovieResponse {
        return UpcomingMovieResponse()
    }
}
package com.elanyudho.movrefapplication.data.remote.mapper

import com.elanyudho.core.abstraction.BaseMapper
import com.elanyudho.movrefapplication.data.remote.response.PopularMovieResponse
import com.elanyudho.movrefapplication.domain.model.MovieItem
import com.elanyudho.movrefapplication.utils.api.Constants.BASE_URL_IMAGE

class PopularMovieMapper: BaseMapper<PopularMovieResponse, List<MovieItem>> {

    override fun mapToDomain(raw: PopularMovieResponse): List<MovieItem> {
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

    override fun mapToRaw(domain: List<MovieItem>): PopularMovieResponse {
        return PopularMovieResponse()
    }
}
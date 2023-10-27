package com.elanyudho.core.data.remote.mapper

import com.elanyudho.core.abstraction.BaseMapper
import com.elanyudho.core.data.remote.response.TrendingMovieResponse
import com.elanyudho.core.domain.model.MovieItem
import com.elanyudho.core.extension.api.Constants.BASE_URL_IMAGE

class TrendingMovieMapper: BaseMapper<TrendingMovieResponse, List<MovieItem>> {

    override fun mapToDomain(raw: TrendingMovieResponse): List<MovieItem> {
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

    override fun mapToRaw(domain: List<MovieItem>): TrendingMovieResponse {
        return TrendingMovieResponse()
    }
}
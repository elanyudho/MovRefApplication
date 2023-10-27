package com.elanyudho.core.data.remote.mapper

import com.elanyudho.core.abstraction.BaseMapper
import com.elanyudho.core.data.remote.response.SearchMovieResponse
import com.elanyudho.core.domain.model.MovieItem
import com.elanyudho.core.extension.api.Constants

class SearchMovieMapper: BaseMapper<SearchMovieResponse, List<MovieItem>> {

    override fun mapToDomain(raw: SearchMovieResponse): List<MovieItem> {
        return raw.results.map {
            MovieItem(
                movieId = it.id,
                movieName = it.title,
                moviePoster = Constants.BASE_URL_IMAGE + it.posterPath,
                movieBackDrop = Constants.BASE_URL_IMAGE + it.backdropPath,
                movieGenre = it.genreIds,
                totalResults = raw.totalResults
            )
        }
    }

    override fun mapToRaw(domain: List<MovieItem>): SearchMovieResponse {
        return SearchMovieResponse()
    }
}
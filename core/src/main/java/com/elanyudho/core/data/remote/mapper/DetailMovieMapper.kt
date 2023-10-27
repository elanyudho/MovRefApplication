package com.elanyudho.core.data.remote.mapper

import com.elanyudho.core.abstraction.BaseMapper
import com.elanyudho.core.data.remote.response.DetailMovieResponse
import com.elanyudho.core.domain.model.DetailMovie
import com.elanyudho.core.extension.api.Constants

class DetailMovieMapper: BaseMapper<DetailMovieResponse, DetailMovie> {

    override fun mapToDomain(raw: DetailMovieResponse): DetailMovie {
        val movieGenre = ArrayList<DetailMovie.Genres>()
        for (i in raw.genres) {
            movieGenre.add(
                DetailMovie.Genres(i.id, i.name)
            )
        }
            return DetailMovie(
                movieId = raw.id,
                movieName = raw.title,
                moviePoster = Constants.BASE_URL_IMAGE + raw.posterPath,
                movieBackDrop = Constants.BASE_URL_IMAGE + raw.backdropPath,
                movieGenre = movieGenre,
                movieOverview = raw.overview,
                movieDate = raw.releaseDate,
                movieStatus = raw.status,
                movieDuration = raw.runtime,
                movieRate = raw.voteAverage
            )
    }

    override fun mapToRaw(domain: DetailMovie): DetailMovieResponse {
        return DetailMovieResponse()
    }
}
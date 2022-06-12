package com.elanyudho.movrefapplication.domain.repository

import com.elanyudho.core.exception.Failure
import com.elanyudho.core.vo.Either
import com.elanyudho.movrefapplication.domain.model.CreditsMovie
import com.elanyudho.movrefapplication.domain.model.DetailMovie
import com.elanyudho.movrefapplication.domain.model.MovieItem

interface MovieRepository {

    suspend fun getPopularMovie(page: String): Either<Failure, List<MovieItem>>

    suspend fun getTopRatedMovie(page: String): Either<Failure, List<MovieItem>>

    suspend fun getUpcomingMovie(page: String): Either<Failure, List<MovieItem>>

    suspend fun getTrendingMovie(page: String): Either<Failure, List<MovieItem>>

    suspend fun getSearchMovie(query: String, page: String): Either<Failure, List<MovieItem>>

    suspend fun getDetailMovie(id: String): Either<Failure, DetailMovie>

    suspend fun getCreditsMovie(id: String): Either<Failure, List<CreditsMovie>>

    suspend fun getRecommendationMovie(id: String, page: String): Either<Failure, List<MovieItem>>
}
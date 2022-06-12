package com.elanyudho.movrefapplication.domain.usecase.movie

import com.elanyudho.core.abstraction.UseCase
import com.elanyudho.core.exception.Failure
import com.elanyudho.core.vo.Either
import com.elanyudho.movrefapplication.domain.model.MovieItem
import com.elanyudho.movrefapplication.domain.repository.MovieRepository
import javax.inject.Inject

class GetRecommendationMovieUseCase @Inject constructor(private val repo: MovieRepository): UseCase<List<MovieItem>, GetRecommendationMovieUseCase.Params>(){

    data class Params(
        val id: String,
        val page: String,
    )

    override suspend fun run(params: Params): Either<Failure, List<MovieItem>> {
        return repo.getRecommendationMovie(params.id, params.page)
    }
}
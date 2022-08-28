package com.elanyudho.movrefapplication.domain.usecase.movie

import com.elanyudho.core.abstraction.UseCase
import com.elanyudho.core.exception.Failure
import com.elanyudho.core.vo.Either
import com.elanyudho.movrefapplication.domain.model.Review
import com.elanyudho.movrefapplication.domain.repository.MovieRepository
import javax.inject.Inject

class GetReviewMovieUseCase @Inject constructor(private val repo: MovieRepository) : UseCase<List<Review>, GetReviewMovieUseCase.Params>() {

    data class Params(
        val id: String,
        val page: String
    )

    override suspend fun run(params: Params): Either<Failure, List<Review>> {
        return repo.getReviewMovie(params.id, params.page)
    }
}
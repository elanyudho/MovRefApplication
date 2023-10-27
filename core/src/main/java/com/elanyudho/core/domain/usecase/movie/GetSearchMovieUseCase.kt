package com.elanyudho.core.domain.usecase.movie

import com.elanyudho.core.abstraction.UseCase
import com.elanyudho.core.domain.model.MovieItem
import com.elanyudho.core.domain.repository.MovieRepository
import com.elanyudho.core.exception.Failure
import com.elanyudho.core.vo.Either
import javax.inject.Inject

class GetSearchMovieUseCase @Inject constructor(private val repo: MovieRepository): UseCase<List<MovieItem>, GetSearchMovieUseCase.Params>(){

    data class Params(
        val query: String,
        val page: String,
    )

    override suspend fun run(params: Params): Either<Failure, List<MovieItem>> {
        return repo.getSearchMovie(params.query, params.page)
    }
}
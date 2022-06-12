package com.elanyudho.movrefapplication.domain.usecase.movie

import com.elanyudho.core.abstraction.UseCase
import com.elanyudho.core.exception.Failure
import com.elanyudho.core.vo.Either
import com.elanyudho.movrefapplication.domain.model.MovieItem
import com.elanyudho.movrefapplication.domain.repository.MovieRepository
import javax.inject.Inject

class GetPopularMovieUseCase @Inject constructor(private val repo: MovieRepository): UseCase<List<MovieItem>, String>(){

    override suspend fun run(params: String): Either<Failure, List<MovieItem>> {
        return repo.getPopularMovie(params)
    }
}
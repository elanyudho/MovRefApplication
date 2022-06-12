package com.elanyudho.movrefapplication.domain.usecase.movie

import com.elanyudho.core.abstraction.UseCase
import com.elanyudho.core.exception.Failure
import com.elanyudho.core.vo.Either
import com.elanyudho.movrefapplication.domain.model.DetailMovie
import com.elanyudho.movrefapplication.domain.repository.MovieRepository
import javax.inject.Inject

class GetDetailMovieUseCase @Inject constructor(private val repo: MovieRepository): UseCase<DetailMovie, String>(){

    override suspend fun run(params: String): Either<Failure, DetailMovie> {
        return repo.getDetailMovie(params)
    }
}
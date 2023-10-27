package com.elanyudho.core.domain.usecase.movie

import com.elanyudho.core.abstraction.UseCase
import com.elanyudho.core.domain.model.CreditsMovie
import com.elanyudho.core.domain.repository.MovieRepository
import com.elanyudho.core.exception.Failure
import com.elanyudho.core.vo.Either

import javax.inject.Inject

class GetCreditsMovieUseCase @Inject constructor(private val repo: MovieRepository): UseCase<List<CreditsMovie>, String>(){

    override suspend fun run(params: String): Either<Failure, List<CreditsMovie>> {
        return repo.getCreditsMovie(params)
    }
}
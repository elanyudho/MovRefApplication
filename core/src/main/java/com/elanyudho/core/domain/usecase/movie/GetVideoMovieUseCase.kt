package com.elanyudho.core.domain.usecase.movie

import com.elanyudho.core.abstraction.UseCase
import com.elanyudho.core.domain.model.Video
import com.elanyudho.core.domain.repository.MovieRepository
import com.elanyudho.core.exception.Failure
import com.elanyudho.core.vo.Either
import javax.inject.Inject

class GetVideoMovieUseCase @Inject constructor(private val repo: MovieRepository): UseCase<List<Video>, String>() {

    override suspend fun run(params: String): Either<Failure, List<Video>> {
        return repo.getVideoMovie(params)
    }

}
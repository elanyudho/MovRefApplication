package com.elanyudho.movrefapplication.domain.usecase.genre

import com.elanyudho.core.abstraction.UseCase
import com.elanyudho.core.exception.Failure
import com.elanyudho.core.vo.Either
import com.elanyudho.movrefapplication.domain.model.MovieItem
import com.elanyudho.movrefapplication.domain.repository.GenreRepository
import javax.inject.Inject

class GetMovieGenreUseCase @Inject constructor(private val repo: GenreRepository): UseCase<List<MovieItem>, GetMovieGenreUseCase.Params>() {

    data class Params(
        val page: String,
        val genreId:String
    )
    override suspend fun run(params: Params): Either<Failure, List<MovieItem>> {
        return repo.movieGenre(params.page, params.genreId)
    }
}
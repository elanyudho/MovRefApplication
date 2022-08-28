package com.elanyudho.movrefapplication.domain.repository

import com.elanyudho.core.exception.Failure
import com.elanyudho.core.vo.Either
import com.elanyudho.movrefapplication.domain.model.Genre
import com.elanyudho.movrefapplication.domain.model.MovieItem

interface GenreRepository {
    suspend fun getGenre(): Either<Failure, List<Genre>>

    suspend fun movieGenre(page: String, genreId: String): Either<Failure, List<MovieItem>>
}
package com.elanyudho.core.domain.repository

import com.elanyudho.core.domain.model.Genre
import com.elanyudho.core.domain.model.MovieItem
import com.elanyudho.core.exception.Failure
import com.elanyudho.core.vo.Either

interface GenreRepository {
    suspend fun getGenre(): Either<Failure, List<Genre>>

    suspend fun movieGenre(page: String, genreId: String): Either<Failure, List<MovieItem>>
}
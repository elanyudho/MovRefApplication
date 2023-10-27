package com.elanyudho.core.domain.usecase.genre

import com.elanyudho.core.abstraction.UseCase
import com.elanyudho.core.domain.model.Genre
import com.elanyudho.core.domain.repository.GenreRepository
import com.elanyudho.core.exception.Failure
import com.elanyudho.core.vo.Either
import javax.inject.Inject

class GetGenreUseCase @Inject constructor(private val repo: GenreRepository): UseCase<List<Genre>, UseCase.None>(){

    override suspend fun run(params: None): Either<Failure, List<Genre>> {
        return repo.getGenre()
    }
}
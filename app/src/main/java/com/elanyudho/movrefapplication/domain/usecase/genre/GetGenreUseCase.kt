package com.elanyudho.movrefapplication.domain.usecase.genre

import com.elanyudho.core.abstraction.UseCase
import com.elanyudho.core.exception.Failure
import com.elanyudho.core.vo.Either
import com.elanyudho.movrefapplication.domain.model.Genre
import com.elanyudho.movrefapplication.domain.repository.GenreRepository
import javax.inject.Inject

class GetGenreUseCase @Inject constructor(private val repo: GenreRepository): UseCase<List<Genre>, UseCase.None>(){

    override suspend fun run(params: None): Either<Failure, List<Genre>> {
        return repo.getGenre()
    }
}
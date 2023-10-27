package com.elanyudho.core.domain.usecase.people

import com.elanyudho.core.abstraction.UseCase
import com.elanyudho.core.domain.model.PeopleImage
import com.elanyudho.core.domain.repository.PeopleRepository
import com.elanyudho.core.exception.Failure
import com.elanyudho.core.vo.Either
import javax.inject.Inject

class GetImagePeopleUseCase @Inject constructor(private val repo: PeopleRepository): UseCase<List<PeopleImage>, String>(){

    override suspend fun run(params: String): Either<Failure, List<PeopleImage>> {
        return repo.getImagePeople(params)
    }
}
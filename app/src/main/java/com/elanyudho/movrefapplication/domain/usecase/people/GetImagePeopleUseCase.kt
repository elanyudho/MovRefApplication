package com.elanyudho.movrefapplication.domain.usecase.people

import com.elanyudho.core.abstraction.UseCase
import com.elanyudho.core.exception.Failure
import com.elanyudho.core.vo.Either
import com.elanyudho.movrefapplication.domain.model.PeopleImage
import com.elanyudho.movrefapplication.domain.repository.PeopleRepository
import javax.inject.Inject

class GetImagePeopleUseCase @Inject constructor(private val repo: PeopleRepository): UseCase<List<PeopleImage>, String>(){

    override suspend fun run(params: String): Either<Failure, List<PeopleImage>> {
        return repo.getImagePeople(params)
    }
}
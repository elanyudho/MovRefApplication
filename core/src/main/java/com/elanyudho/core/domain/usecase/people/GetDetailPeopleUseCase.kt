package com.elanyudho.core.domain.usecase.people

import com.elanyudho.core.abstraction.UseCase
import com.elanyudho.core.domain.model.DetailPeople
import com.elanyudho.core.domain.repository.PeopleRepository
import com.elanyudho.core.exception.Failure
import com.elanyudho.core.vo.Either
import javax.inject.Inject

class GetDetailPeopleUseCase @Inject constructor(private val repo: PeopleRepository): UseCase<DetailPeople, String>(){

    override suspend fun run(params: String): Either<Failure, DetailPeople> {
        return repo.getDetailPeople(params)
    }
}
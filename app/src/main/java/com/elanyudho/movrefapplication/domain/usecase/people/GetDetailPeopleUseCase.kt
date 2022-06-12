package com.elanyudho.movrefapplication.domain.usecase.people

import com.elanyudho.core.abstraction.UseCase
import com.elanyudho.core.exception.Failure
import com.elanyudho.core.vo.Either
import com.elanyudho.movrefapplication.domain.model.DetailPeople
import com.elanyudho.movrefapplication.domain.repository.PeopleRepository
import javax.inject.Inject

class GetDetailPeopleUseCase @Inject constructor(private val repo: PeopleRepository): UseCase<DetailPeople, String>(){

    override suspend fun run(params: String): Either<Failure, DetailPeople> {
        return repo.getDetailPeople(params)
    }
}
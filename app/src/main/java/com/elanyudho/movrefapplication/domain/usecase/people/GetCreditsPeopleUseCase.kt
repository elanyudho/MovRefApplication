package com.elanyudho.movrefapplication.domain.usecase.people

import com.elanyudho.core.abstraction.UseCase
import com.elanyudho.core.exception.Failure
import com.elanyudho.core.vo.Either
import com.elanyudho.movrefapplication.domain.model.CreditsPeople
import com.elanyudho.movrefapplication.domain.repository.PeopleRepository
import javax.inject.Inject

class GetCreditsPeopleUseCase @Inject constructor(private val repo: PeopleRepository): UseCase<List<CreditsPeople>, String>(){

    override suspend fun run(params: String): Either<Failure, List<CreditsPeople>> {
        return repo.getCreditsPeople(params)
    }
}
package com.elanyudho.core.domain.usecase.people

import com.elanyudho.core.abstraction.UseCase
import com.elanyudho.core.domain.model.CreditsPeople
import com.elanyudho.core.domain.repository.PeopleRepository
import com.elanyudho.core.exception.Failure
import com.elanyudho.core.vo.Either
import javax.inject.Inject

class GetCreditsPeopleUseCase @Inject constructor(private val repo: PeopleRepository): UseCase<List<CreditsPeople>, String>(){

    override suspend fun run(params: String): Either<Failure, List<CreditsPeople>> {
        return repo.getCreditsPeople(params)
    }
}
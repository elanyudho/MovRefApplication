package com.elanyudho.core.domain.usecase.people

import com.elanyudho.core.abstraction.UseCase
import com.elanyudho.core.domain.model.PeopleItem
import com.elanyudho.core.domain.repository.PeopleRepository
import com.elanyudho.core.exception.Failure
import com.elanyudho.core.vo.Either
import javax.inject.Inject

class GetSearchPeopleUseCase @Inject constructor(private val repo: PeopleRepository): UseCase<List<PeopleItem>, GetSearchPeopleUseCase.Params>(){

    data class Params(
        val query: String,
        val page: String,
    )

    override suspend fun run(params: Params): Either<Failure, List<PeopleItem>> {
        return repo.getSearchPeople(params.query, params.page)
    }
}
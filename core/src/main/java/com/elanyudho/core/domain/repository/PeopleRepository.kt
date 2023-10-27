package com.elanyudho.core.domain.repository

import com.elanyudho.core.domain.model.CreditsPeople
import com.elanyudho.core.domain.model.DetailPeople
import com.elanyudho.core.domain.model.PeopleImage
import com.elanyudho.core.domain.model.PeopleItem
import com.elanyudho.core.exception.Failure
import com.elanyudho.core.vo.Either

interface PeopleRepository {

    suspend fun getPopularPeople(page: String): Either<Failure, List<PeopleItem>>

    suspend fun getDetailPeople(id: String): Either<Failure, DetailPeople>

    suspend fun getCreditsPeople(id: String): Either<Failure, List<CreditsPeople>>

    suspend fun getImagePeople(id: String): Either<Failure, List<PeopleImage>>

    suspend fun getSearchPeople(query: String, page: String): Either<Failure, List<PeopleItem>>
}
package com.elanyudho.movrefapplication.domain.repository

import com.elanyudho.core.exception.Failure
import com.elanyudho.core.vo.Either
import com.elanyudho.movrefapplication.domain.model.CreditsPeople
import com.elanyudho.movrefapplication.domain.model.DetailPeople
import com.elanyudho.movrefapplication.domain.model.PeopleImage
import com.elanyudho.movrefapplication.domain.model.PeopleItem

interface PeopleRepository {

    suspend fun getPopularPeople(page: String): Either<Failure, List<PeopleItem>>

    suspend fun getDetailPeople(id: String): Either<Failure, DetailPeople>

    suspend fun getCreditsPeople(id: String): Either<Failure, List<CreditsPeople>>

    suspend fun getImagePeople(id: String): Either<Failure, List<PeopleImage>>

    suspend fun getSearchPeople(query: String, page: String): Either<Failure, List<PeopleItem>>
}
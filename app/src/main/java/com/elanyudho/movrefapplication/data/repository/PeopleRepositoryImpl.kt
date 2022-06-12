package com.elanyudho.movrefapplication.data.repository

import com.elanyudho.core.exception.Failure
import com.elanyudho.core.vo.Either
import com.elanyudho.movrefapplication.data.remote.mapper.*
import com.elanyudho.movrefapplication.data.remote.source.RemoteDataSource
import com.elanyudho.movrefapplication.domain.model.CreditsPeople
import com.elanyudho.movrefapplication.domain.model.DetailPeople
import com.elanyudho.movrefapplication.domain.model.PeopleImage
import com.elanyudho.movrefapplication.domain.model.PeopleItem
import com.elanyudho.movrefapplication.domain.repository.PeopleRepository
import kotlinx.coroutines.delay
import javax.inject.Inject

class PeopleRepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val popularPeopleMapper: PopularPeopleMapper,
    private val detailPeopleMapper: DetailPeopleMapper,
    private val imagePeopleMapper: ImagePeopleMapper,
    private val creditsPeopleMapper: CreditsPeopleMapper,
    private val searchPeopleMapper: SearchPeopleMapper
) : PeopleRepository {

    override suspend fun getPopularPeople(page: String): Either<Failure, List<PeopleItem>> {
        return when (val response = remoteDataSource.getPopularPeople(page)) {
            is Either.Success -> {
                val list = popularPeopleMapper.mapToDomain(response.body)
                Either.Success(list)
            }
            is Either.Error -> {
                Either.Error(response.failure)
            }
        }
    }

    override suspend fun getDetailPeople(id: String): Either<Failure, DetailPeople> {
        return when(val response = remoteDataSource.getDetailPeople(id)) {
            is Either.Success -> {
                val data = detailPeopleMapper.mapToDomain(response.body)
                Either.Success(data)
            }
            is Either.Error -> {
                Either.Error(response.failure)
            }
        }
    }

    override suspend fun getCreditsPeople(id: String): Either<Failure, List<CreditsPeople>> {
        return when(val response = remoteDataSource.getCreditsPeople(id)) {
            is Either.Success -> {
                val list = creditsPeopleMapper.mapToDomain(response.body)
                Either.Success(list)
            }
            is Either.Error -> {
                Either.Error(response.failure)
            }
        }
    }

    override suspend fun getImagePeople(id: String): Either<Failure, List<PeopleImage>> {
        return when(val response = remoteDataSource.getImagePeople(id)) {
            is Either.Success -> {
                val list = imagePeopleMapper.mapToDomain(response.body)
                Either.Success(list)
            }
            is Either.Error -> {
                Either.Error(response.failure)
            }
        }
    }

    override suspend fun getSearchPeople(
        query: String,
        page: String
    ): Either<Failure, List<PeopleItem>> {
        return when(val response = remoteDataSource.getSearchPeople(query, page)) {
            is Either.Success -> {
                delay(350)
                val list = searchPeopleMapper.mapToDomain(response.body)
                Either.Success(list)
            }
            is Either.Error -> {
                Either.Error(response.failure)
            }
        }
    }
}
package com.elanyudho.movrefapplication.data.repository

import com.elanyudho.core.exception.Failure
import com.elanyudho.core.vo.Either
import com.elanyudho.movrefapplication.data.remote.mapper.GenreMapper
import com.elanyudho.movrefapplication.data.remote.mapper.MovieGenreMapper
import com.elanyudho.movrefapplication.data.remote.source.RemoteDataSource
import com.elanyudho.movrefapplication.domain.model.Genre
import com.elanyudho.movrefapplication.domain.model.MovieItem
import com.elanyudho.movrefapplication.domain.repository.GenreRepository
import javax.inject.Inject

class GenreRepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val genreMapper: GenreMapper,
    private val genreMovieMapper: MovieGenreMapper
): GenreRepository {

    override suspend fun getGenre(): Either<Failure, List<Genre>> {
        return when(val response = remoteDataSource.getGenre()) {
            is Either.Success -> {
                val list = genreMapper.mapToDomain(response.body)
                Either.Success(list)
            }
            is Either.Error -> {
                Either.Error(response.failure)
            }
        }
    }

    override suspend fun movieGenre(
        page: String,
        genreId: String
    ): Either<Failure, List<MovieItem>> {
        return when(val response = remoteDataSource.getMovieGenre(page, genreId)) {
            is Either.Success -> {
                val list = genreMovieMapper.mapToDomain(response.body)
                Either.Success(list)
            }
            is Either.Error -> {
                Either.Error(response.failure)
            }
        }
    }
}
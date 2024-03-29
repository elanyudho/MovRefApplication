package com.elanyudho.core.data.repository

import com.elanyudho.core.data.remote.mapper.CreditsMovieMapper
import com.elanyudho.core.data.remote.mapper.DetailMovieMapper
import com.elanyudho.core.data.remote.mapper.PopularMovieMapper
import com.elanyudho.core.data.remote.mapper.ReviewMapper
import com.elanyudho.core.data.remote.mapper.SearchMovieMapper
import com.elanyudho.core.data.remote.mapper.TopRatedMovieMapper
import com.elanyudho.core.data.remote.mapper.TrendingMovieMapper
import com.elanyudho.core.data.remote.mapper.UpcomingMovieMapper
import com.elanyudho.core.data.remote.mapper.VideoMovieMapper
import com.elanyudho.core.data.remote.source.RemoteDataSource
import com.elanyudho.core.domain.model.CreditsMovie
import com.elanyudho.core.domain.model.DetailMovie
import com.elanyudho.core.domain.model.MovieItem
import com.elanyudho.core.domain.model.Review
import com.elanyudho.core.domain.model.Video
import com.elanyudho.core.domain.repository.MovieRepository
import com.elanyudho.core.exception.Failure
import com.elanyudho.core.vo.Either
import kotlinx.coroutines.delay
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val popularMovieMapper: PopularMovieMapper,
    private val topRatedMovieMapper: TopRatedMovieMapper,
    private val upcomingMovieMapper: UpcomingMovieMapper,
    private val trendingMovieMapper: TrendingMovieMapper,
    private val searchMovieMapper: SearchMovieMapper,
    private val detailMovieMapper: DetailMovieMapper,
    private val creditsMovieMapper: CreditsMovieMapper,
    private val reviewMapper: ReviewMapper,
    private val videoMovieMapper: VideoMovieMapper
): MovieRepository {

    override suspend fun getPopularMovie(page: String): Either<Failure, List<MovieItem>> {
        return when(val response = remoteDataSource.getPopularMovie(page)) {
            is Either.Success -> {
                val list = popularMovieMapper.mapToDomain(response.body)
                Either.Success(list)
            }
            is Either.Error -> {
                Either.Error(response.failure)
            }
        }
    }

    override suspend fun getTopRatedMovie(page: String): Either<Failure, List<MovieItem>> {
        return when(val response = remoteDataSource.getTopRatedMovie(page)) {
            is Either.Success -> {
                val list = topRatedMovieMapper.mapToDomain(response.body)
                Either.Success(list)
            }
            is Either.Error -> {
                Either.Error(response.failure)
            }
        }
    }

    override suspend fun getUpcomingMovie(page: String): Either<Failure, List<MovieItem>> {
        return when(val response = remoteDataSource.getUpcomingMovie(page)) {
            is Either.Success -> {
                val list = upcomingMovieMapper.mapToDomain(response.body)
                Either.Success(list)
            }
            is Either.Error -> {
                Either.Error(response.failure)
            }
        }
    }

    override suspend fun getTrendingMovie(page: String): Either<Failure, List<MovieItem>> {
        return when(val response = remoteDataSource.getTrendingMovie(page)) {
            is Either.Success -> {
                val list = trendingMovieMapper.mapToDomain(response.body)
                Either.Success(list)
            }
            is Either.Error -> {
                Either.Error(response.failure)
            }
        }
    }

    override suspend fun getSearchMovie(query: String, page: String): Either<Failure, List<MovieItem>> {
        return when(val response = remoteDataSource.getSearchMovie(query, page)) {
            is Either.Success -> {
                delay(350)
                val list = searchMovieMapper.mapToDomain(response.body)
                Either.Success(list)
            }
            is Either.Error -> {
                Either.Error(response.failure)
            }
        }
    }

    override suspend fun getDetailMovie(id: String): Either<Failure, DetailMovie> {
        return when(val response = remoteDataSource.getDetailMovie(id)) {
            is Either.Success -> {
                val data = detailMovieMapper.mapToDomain(response.body)
                Either.Success(data)
            }
            is Either.Error -> {
                Either.Error(response.failure)
            }
        }
    }

    override suspend fun getCreditsMovie(id: String): Either<Failure, List<CreditsMovie>> {
        return when(val response = remoteDataSource.getCreditsMovie(id)) {
            is Either.Success -> {
                val list = creditsMovieMapper.mapToDomain(response.body)
                Either.Success(list)
            }
            is Either.Error -> {
                Either.Error(response.failure)
            }
        }
    }

    override suspend fun getRecommendationMovie(id: String, page: String): Either<Failure, List<MovieItem>> {
        return when(val response = remoteDataSource.getRecommendationMovie(id, page)) {
            is Either.Success -> {
                val list = popularMovieMapper.mapToDomain(response.body)
                Either.Success(list)
            }
            is Either.Error -> {
                Either.Error(response.failure)
            }
        }
    }

    override suspend fun getReviewMovie(id: String, page: String): Either<Failure, List<Review>> {
        return when(val response = remoteDataSource.getReviewMovie(id, page)) {
            is Either.Success -> {
                val list = reviewMapper.mapToDomain(response.body)
                Either.Success(list)
            }
            is Either.Error -> {
                Either.Error(response.failure)
            }
        }
    }

    override suspend fun getVideoMovie(id: String): Either<Failure, List<Video>> {
        return when(val response = remoteDataSource.getVideoMovie(id)) {
            is Either.Success -> {
                val list = videoMovieMapper.mapToDomain(response.body)
                Either.Success(list)
            }
            is Either.Error -> {
                Either.Error(response.failure)
            }
        }
    }
}
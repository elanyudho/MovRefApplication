package com.elanyudho.movrefapplication.data.remote.source

import com.elanyudho.core.exception.Failure
import com.elanyudho.core.vo.Either
import com.elanyudho.movrefapplication.data.remote.response.*
import com.elanyudho.movrefapplication.data.remote.service.ApiService
import javax.inject.Inject

class RemoteDataSource
@Inject constructor(private val api: ApiService) : RemoteSafeRequest() {

    suspend fun getPopularMovie(page: String): Either<Failure, PopularMovieResponse> =
        request {
            api.getPopularMovie(page)
        }

    suspend fun getTopRatedMovie(page: String): Either<Failure, TopRatedMovieResponse> =
        request {
            api.getTopRatedMovie(page)
        }

    suspend fun getUpcomingMovie(page: String): Either<Failure, UpcomingMovieResponse> =
        request {
            api.getUpcomingMovie(page)
        }

    suspend fun getTrendingMovie(page: String): Either<Failure, TrendingMovieResponse> =
        request {
            api.getTrendingMovie(page)
        }

    suspend fun getPopularPeople(page: String): Either<Failure, PopularPeopleResponse> =
        request {
            api.getPopularPeople(page)
        }

    suspend fun getGenre(): Either<Failure, GenreResponse> =
        request {
            api.getGenre()
        }

    suspend fun getSearchMovie(query: String, page: String): Either<Failure, SearchMovieResponse> =
        request {
            api.getSearchMovie(query, page)
        }

    suspend fun getDetailMovie(id: String): Either<Failure, DetailMovieResponse> =
        request {
            api.getDetailMovie(id)
        }

    suspend fun getCreditsMovie(id: String): Either<Failure, CreditsMovieResponse> =
        request {
            api.getCreditsMovie(id)
        }

    suspend fun getRecommendationMovie(id: String, page: String): Either<Failure, PopularMovieResponse> =
        request {
            api.getRecommendationMovie(id, page)
        }

    suspend fun getDetailPeople(id: String): Either<Failure, DetailPeopleResponse> =
        request {
            api.getDetailPeople(id)
        }

    suspend fun getCreditsPeople(id: String): Either<Failure, CreditsPeopleResponse> =
        request {
            api.getCreditsPeople(id)
        }

    suspend fun getImagePeople(id: String): Either<Failure, ImagePeopleResponse> =
        request {
            api.getImagesPeople(id)
        }

    suspend fun getSearchPeople(query: String, page: String): Either<Failure, SearchPeopleResponse> =
        request {
            api.getSearchPeople(query, page)
        }

    suspend fun getMovieGenre(page: String, genreId: String): Either<Failure, GenreMovieResponse> =
        request {
            api.getMovieListByGenre(page, genreId)
        }

    suspend fun getReviewMovie(id: String, page: String): Either<Failure, ReviewResponse> =
        request {
            api.getReviewMovie(id, page)
        }
    suspend fun getVideoMovie(id: String): Either<Failure, VideoResponse> =
        request {
            api.getVideoMovie(id)
        }
}
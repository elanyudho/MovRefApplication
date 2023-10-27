package com.elanyudho.core.data.remote.service




import com.elanyudho.core.data.remote.response.CreditsMovieResponse
import com.elanyudho.core.data.remote.response.CreditsPeopleResponse
import com.elanyudho.core.data.remote.response.DetailMovieResponse
import com.elanyudho.core.data.remote.response.DetailPeopleResponse
import com.elanyudho.core.data.remote.response.GenreMovieResponse
import com.elanyudho.core.data.remote.response.GenreResponse
import com.elanyudho.core.data.remote.response.ImagePeopleResponse
import com.elanyudho.core.data.remote.response.PopularMovieResponse
import com.elanyudho.core.data.remote.response.PopularPeopleResponse
import com.elanyudho.core.data.remote.response.ReviewResponse
import com.elanyudho.core.data.remote.response.SearchMovieResponse
import com.elanyudho.core.data.remote.response.SearchPeopleResponse
import com.elanyudho.core.data.remote.response.TopRatedMovieResponse
import com.elanyudho.core.data.remote.response.TrendingMovieResponse
import com.elanyudho.core.data.remote.response.UpcomingMovieResponse
import com.elanyudho.core.data.remote.response.VideoResponse
import com.elanyudho.core.extension.api.Constants.API_KEY
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("movie/popular?api_key=${API_KEY}&language=en-US")
    suspend fun getPopularMovie(
        @Query("page") page: String
    ): Response<PopularMovieResponse>

    @GET("movie/top_rated?api_key=${API_KEY}&language=en-US")
    suspend fun getTopRatedMovie(
        @Query("page") page: String
    ): Response<TopRatedMovieResponse>

    @GET("movie/upcoming?api_key=${API_KEY}&language=en-US")
    suspend fun getUpcomingMovie(
        @Query("page") page: String
    ): Response<UpcomingMovieResponse>

    @GET("trending/movie/day?api_key=${API_KEY}&language=en-US")
    suspend fun getTrendingMovie(
        @Query("page") page: String
    ): Response<TrendingMovieResponse>

    @GET("person/popular?api_key=${API_KEY}&language=en-US")
    suspend fun getPopularPeople(
        @Query("page") page: String
    ): Response<PopularPeopleResponse>

    @GET("genre/movie/list?api_key=${API_KEY}&language=en-US")
    suspend fun getGenre(): Response<GenreResponse>

    @GET("search/movie?api_key=${API_KEY}&language=en-US")
    suspend fun getSearchMovie(
        @Query("query") query: String,
        @Query("page") page: String
    ): Response<SearchMovieResponse>

    @GET("movie/{id}?api_key=${API_KEY}&language=en-US")
    suspend fun getDetailMovie(
        @Path("id") id: String
    ): Response<DetailMovieResponse>

    @GET("movie/{id}/credits?api_key=${API_KEY}&language=en-US")
    suspend fun getCreditsMovie(
        @Path("id") id: String
    ): Response<CreditsMovieResponse>

    @GET("movie/{id}/recommendations?api_key=${API_KEY}&language=en-US")
    suspend fun getRecommendationMovie(
        @Path("id") id: String,
        @Query("page") page: String
    ): Response<PopularMovieResponse>

    @GET("person/{id}?api_key=${API_KEY}&language=en-US")
    suspend fun getDetailPeople(
        @Path("id") id: String
    ): Response<DetailPeopleResponse>

    @GET("person/{id}/movie_credits?api_key=${API_KEY}&language=en-US")
    suspend fun getCreditsPeople(
        @Path("id") id: String
    ): Response<CreditsPeopleResponse>

    @GET("person/{id}/images?api_key=${API_KEY}")
    suspend fun getImagesPeople(
        @Path("id") id: String
    ): Response<ImagePeopleResponse>

    @GET("search/person?api_key=${API_KEY}&language=en-US")
    suspend fun getSearchPeople(
        @Query("query") query: String,
        @Query("page") page: String
    ): Response<SearchPeopleResponse>

    @GET("discover/movie?api_key=${API_KEY}&language=en-US")
    suspend fun getMovieListByGenre(
        @Query("page") page: String,
        @Query("with_genres") genreId: String
    ): Response<GenreMovieResponse>

    @GET("movie/{id}/reviews?api_key=${API_KEY}&language=en-US")
    suspend fun getReviewMovie(
        @Path("id") id: String,
        @Query("page") page: String
    ): Response<ReviewResponse>

    @GET("movie/{id}/videos?api_key=${API_KEY}&language=en-US")
    suspend fun getVideoMovie(
        @Path("id") id: String
    ): Response<VideoResponse>
}
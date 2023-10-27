package com.elanyudho.core.di

import com.elanyudho.core.domain.repository.GenreRepository
import com.elanyudho.core.domain.repository.MovieRepository
import com.elanyudho.core.domain.repository.PeopleRepository
import com.elanyudho.core.domain.usecase.genre.GetGenreUseCase
import com.elanyudho.core.domain.usecase.genre.GetMovieGenreUseCase
import com.elanyudho.core.domain.usecase.movie.GetCreditsMovieUseCase
import com.elanyudho.core.domain.usecase.movie.GetDetailMovieUseCase
import com.elanyudho.core.domain.usecase.movie.GetPopularMovieUseCase
import com.elanyudho.core.domain.usecase.movie.GetRecommendationMovieUseCase
import com.elanyudho.core.domain.usecase.movie.GetReviewMovieUseCase
import com.elanyudho.core.domain.usecase.movie.GetSearchMovieUseCase
import com.elanyudho.core.domain.usecase.movie.GetTopRatedMovieUseCase
import com.elanyudho.core.domain.usecase.movie.GetTrendingMovieUseCase
import com.elanyudho.core.domain.usecase.movie.GetUpcomingMovieUseCase
import com.elanyudho.core.domain.usecase.movie.GetVideoMovieUseCase
import com.elanyudho.core.domain.usecase.people.GetCreditsPeopleUseCase
import com.elanyudho.core.domain.usecase.people.GetDetailPeopleUseCase
import com.elanyudho.core.domain.usecase.people.GetImagePeopleUseCase
import com.elanyudho.core.domain.usecase.people.GetPopularPeopleUseCase
import com.elanyudho.core.domain.usecase.people.GetSearchPeopleUseCase

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.scopes.ActivityScoped

@Module
@InstallIn(ActivityComponent::class)
object UseCaseModule {

    @Provides
    @ActivityScoped
    fun providePopularMovieUseCase(repository: MovieRepository) = GetPopularMovieUseCase(repository)

    @Provides
    @ActivityScoped
    fun provideTopRatedMovieUseCase(repository: MovieRepository) = GetTopRatedMovieUseCase(repository)

    @Provides
    @ActivityScoped
    fun provideUpcomingMovieUseCase(repository: MovieRepository) = GetUpcomingMovieUseCase(repository)

    @Provides
    @ActivityScoped
    fun provideTrendingMovieUseCase(repository: MovieRepository) = GetTrendingMovieUseCase(repository)

    @Provides
    @ActivityScoped
    fun provideSearchMovieUseCase(repository: MovieRepository) = GetSearchMovieUseCase(repository)

    @Provides
    @ActivityScoped
    fun provideGenreUseCase(repository: GenreRepository) = GetGenreUseCase(repository)

    @Provides
    @ActivityScoped
    fun providePopularPeopleUseCase(repository: PeopleRepository) = GetPopularPeopleUseCase(repository)

    @Provides
    @ActivityScoped
    fun provideDetailMovieUseCase(repository: MovieRepository) = GetDetailMovieUseCase(repository)

    @Provides
    @ActivityScoped
    fun provideCreditsMovieUseCase(repository: MovieRepository) = GetCreditsMovieUseCase(repository)

    @Provides
    @ActivityScoped
    fun provideRecommendationMovieUseCase(repository: MovieRepository) = GetRecommendationMovieUseCase(repository)

    @Provides
    @ActivityScoped
    fun provideDetailPeopleUseCase(repository: PeopleRepository) = GetDetailPeopleUseCase(repository)

    @Provides
    @ActivityScoped
    fun provideCreditsPeopleUseCase(repository: PeopleRepository) = GetCreditsPeopleUseCase(repository)

    @Provides
    @ActivityScoped
    fun provideImagePeopleUseCase(repository: PeopleRepository) = GetImagePeopleUseCase(repository)

    @Provides
    @ActivityScoped
    fun provideSearchPeopleUseCase(repository: PeopleRepository) = GetSearchPeopleUseCase(repository)

    @Provides
    @ActivityScoped
    fun provideGetMovieGenreUseCase(repository: GenreRepository) = GetMovieGenreUseCase(repository)

    @Provides
    @ActivityScoped
    fun provideGetReviewMovieUseCase(repository: MovieRepository) = GetReviewMovieUseCase(repository)

    @Provides
    @ActivityScoped
    fun provideGetVideoMovieUseCase(repository: MovieRepository) = GetVideoMovieUseCase(repository)
}
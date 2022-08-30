package com.elanyudho.movrefapplication.di

import com.elanyudho.movrefapplication.domain.repository.GenreRepository
import com.elanyudho.movrefapplication.domain.repository.MovieRepository
import com.elanyudho.movrefapplication.domain.repository.PeopleRepository
import com.elanyudho.movrefapplication.domain.usecase.genre.GetGenreUseCase
import com.elanyudho.movrefapplication.domain.usecase.genre.GetMovieGenreUseCase
import com.elanyudho.movrefapplication.domain.usecase.movie.*
import com.elanyudho.movrefapplication.domain.usecase.people.*
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
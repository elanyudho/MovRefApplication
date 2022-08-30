package com.elanyudho.movrefapplication.di

import com.elanyudho.movrefapplication.data.remote.mapper.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.scopes.ActivityScoped

@Module
@InstallIn(ActivityComponent::class)
object MapperModule {

    @Provides
    @ActivityScoped
    fun providePopularMovieMapper() = PopularMovieMapper()

    @Provides
    @ActivityScoped
    fun provideTopRatedMovieMapper() = TopRatedMovieMapper()

    @Provides
    @ActivityScoped
    fun provideUpcomingMovieMapper() = UpcomingMovieMapper()

    @Provides
    @ActivityScoped
    fun provideTrendingMovieMapper() = TrendingMovieMapper()

    @Provides
    @ActivityScoped
    fun provideSearchMovieMapper() = SearchMovieMapper()

    @Provides
    @ActivityScoped
    fun provideGenreMapper() = GenreMapper()

    @Provides
    @ActivityScoped
    fun providePopularPeopleMapper() = PopularPeopleMapper()

    @Provides
    @ActivityScoped
    fun provideDetailMovieMapper() = DetailMovieMapper()

    @Provides
    @ActivityScoped
    fun provideCreditsMovieMapper() = CreditsMovieMapper()

    @Provides
    @ActivityScoped
    fun provideImagePeopleMapper() = ImagePeopleMapper()

    @Provides
    @ActivityScoped
    fun provideDetailPeopleMapper() = DetailPeopleMapper()

    @Provides
    @ActivityScoped
    fun provideCreditsPeopleMapper() = CreditsPeopleMapper()

    @Provides
    @ActivityScoped
    fun provideSearchPeopleMapper() = SearchPeopleMapper()

    @Provides
    @ActivityScoped
    fun provideMovieGenreMapper() = MovieGenreMapper()

    @Provides
    @ActivityScoped
    fun provideReviewMovieMapper() = ReviewMapper()

    @Provides
    @ActivityScoped
    fun provideVideoMovieMapper() = VideoMovieMapper()

}
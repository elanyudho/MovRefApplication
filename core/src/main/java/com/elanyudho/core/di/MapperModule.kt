package com.elanyudho.core.di

import com.elanyudho.core.data.remote.mapper.CreditsMovieMapper
import com.elanyudho.core.data.remote.mapper.CreditsPeopleMapper
import com.elanyudho.core.data.remote.mapper.DetailMovieMapper
import com.elanyudho.core.data.remote.mapper.DetailPeopleMapper
import com.elanyudho.core.data.remote.mapper.GenreMapper
import com.elanyudho.core.data.remote.mapper.ImagePeopleMapper
import com.elanyudho.core.data.remote.mapper.MovieGenreMapper
import com.elanyudho.core.data.remote.mapper.PopularMovieMapper
import com.elanyudho.core.data.remote.mapper.PopularPeopleMapper
import com.elanyudho.core.data.remote.mapper.ReviewMapper
import com.elanyudho.core.data.remote.mapper.SearchMovieMapper
import com.elanyudho.core.data.remote.mapper.SearchPeopleMapper
import com.elanyudho.core.data.remote.mapper.TopRatedMovieMapper
import com.elanyudho.core.data.remote.mapper.TrendingMovieMapper
import com.elanyudho.core.data.remote.mapper.UpcomingMovieMapper
import com.elanyudho.core.data.remote.mapper.VideoMovieMapper
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
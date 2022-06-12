package com.elanyudho.movrefapplication.di

import com.elanyudho.movrefapplication.data.repository.GenreRepositoryImpl
import com.elanyudho.movrefapplication.data.repository.MovieRepositoryImpl
import com.elanyudho.movrefapplication.data.repository.PeopleRepositoryImpl
import com.elanyudho.movrefapplication.domain.repository.GenreRepository
import com.elanyudho.movrefapplication.domain.repository.MovieRepository
import com.elanyudho.movrefapplication.domain.repository.PeopleRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.scopes.ActivityScoped

@Module
@InstallIn(ActivityComponent::class)
abstract class RepositoryModule {

    @Binds
    @ActivityScoped
    abstract fun bindMovieRepository(repositoryImpl: MovieRepositoryImpl): MovieRepository

    @Binds
    @ActivityScoped
    abstract fun bindGenreRepository(repositoryImpl: GenreRepositoryImpl): GenreRepository

    @Binds
    @ActivityScoped
    abstract fun bindPeopleRepository(repositoryImpl: PeopleRepositoryImpl): PeopleRepository

}
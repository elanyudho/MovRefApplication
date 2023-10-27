package com.elanyudho.core.di

import com.elanyudho.core.data.repository.GenreRepositoryImpl
import com.elanyudho.core.data.repository.MovieRepositoryImpl
import com.elanyudho.core.data.repository.PeopleRepositoryImpl
import com.elanyudho.core.domain.repository.GenreRepository
import com.elanyudho.core.domain.repository.MovieRepository
import com.elanyudho.core.domain.repository.PeopleRepository
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
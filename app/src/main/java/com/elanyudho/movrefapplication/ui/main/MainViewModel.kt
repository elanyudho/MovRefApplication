package com.elanyudho.movrefapplication.ui.main

import androidx.lifecycle.viewModelScope
import com.elanyudho.core.abstraction.BaseViewModel
import com.elanyudho.core.abstraction.UseCase
import com.elanyudho.core.dispatcher.DispatcherProvider
import com.elanyudho.core.exception.Failure
import com.elanyudho.core.extension.onError
import com.elanyudho.core.extension.onSuccess
import com.elanyudho.movrefapplication.domain.model.Genre
import com.elanyudho.movrefapplication.domain.model.MovieItem
import com.elanyudho.movrefapplication.domain.model.PeopleItem
import com.elanyudho.movrefapplication.domain.usecase.genre.GetGenreUseCase
import com.elanyudho.movrefapplication.domain.usecase.movie.*
import com.elanyudho.movrefapplication.domain.usecase.people.GetPopularPeopleUseCase
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val dispatcherProvider: DispatcherProvider,
    private val getPopularMovie: GetPopularMovieUseCase,
    private val getTopRatedMovieUseCase: GetTopRatedMovieUseCase,
    private val getUpcomingMovieUseCase: GetUpcomingMovieUseCase,
    private val getTrendingMovieUseCase: GetTrendingMovieUseCase,
    private val getSearchMovieUseCase: GetSearchMovieUseCase,
    private val getGenreUseCase: GetGenreUseCase,
    private val getPopularPeopleUseCase: GetPopularPeopleUseCase
): BaseViewModel<MainViewModel.MainUiState>(){

    sealed class MainUiState {
        data class Loading(val isLoading: Boolean): MainUiState()
        data class MovieDataLoaded(val movieList: List<MovieItem>): MainUiState()
        data class TrendingMovieDataLoaded(val movieList: List<MovieItem>): MainUiState()
        data class PopularPeopleDataLoaded(val peopleList: List<PeopleItem>): MainUiState()
        data class SearchMovieLoaded(val movieList: List<MovieItem>): MainUiState()
        data class GenreDataLoaded(val genreList: List<Genre>): MainUiState()
        data class FailedLoadData(val failure: Failure): MainUiState()
    }

    fun getPopularMovie(page: String){
        _uiState.value = MainUiState.Loading(true)
        viewModelScope.launch(dispatcherProvider.io) {
            getPopularMovie.run(page)
                .onSuccess {
                    withContext(dispatcherProvider.main) {
                        _uiState.value = MainUiState.Loading(false)
                        _uiState.value = MainUiState.MovieDataLoaded(it)
                    }
                }
                .onError {
                    withContext(dispatcherProvider.main) {
                        _uiState.value = MainUiState.Loading(false)
                        _uiState.value = MainUiState.FailedLoadData(it)
                    }
                }
        }
    }

    fun getTopRatedMovie(page: String){
        _uiState.value = MainUiState.Loading(true)
        viewModelScope.launch(dispatcherProvider.io) {
            getTopRatedMovieUseCase.run(page)
                .onSuccess {
                    withContext(dispatcherProvider.main) {
                        _uiState.value = MainUiState.Loading(false)
                        _uiState.value = MainUiState.MovieDataLoaded(it)
                    }
                }
                .onError {
                    withContext(dispatcherProvider.main) {
                        _uiState.value = MainUiState.Loading(false)
                        _uiState.value = MainUiState.FailedLoadData(it)
                    }
                }
        }
    }

    fun getUpcomingMovie(page: String){
        _uiState.value = MainUiState.Loading(true)
        viewModelScope.launch(dispatcherProvider.io) {
            getUpcomingMovieUseCase.run(page)
                .onSuccess {
                    withContext(dispatcherProvider.main) {
                        _uiState.value = MainUiState.Loading(false)
                        _uiState.value = MainUiState.MovieDataLoaded(it)
                    }
                }
                .onError {
                    withContext(dispatcherProvider.main) {
                        _uiState.value = MainUiState.Loading(false)
                        _uiState.value = MainUiState.FailedLoadData(it)
                    }
                }
        }
    }

    fun getTrendingMovie(page: String){
        _uiState.value = MainUiState.Loading(true)
        viewModelScope.launch(dispatcherProvider.io) {
            getTrendingMovieUseCase.run(page)
                .onSuccess {
                    withContext(dispatcherProvider.main) {
                        _uiState.value = MainUiState.Loading(false)
                        _uiState.value = MainUiState.TrendingMovieDataLoaded(it)
                    }
                }
                .onError {
                    withContext(dispatcherProvider.main) {
                        _uiState.value = MainUiState.Loading(false)
                        _uiState.value = MainUiState.FailedLoadData(it)
                    }
                }
        }
    }

    fun getGenre(){
        _uiState.value = MainUiState.Loading(true)
        viewModelScope.launch(dispatcherProvider.io) {
            getGenreUseCase.run(UseCase.None)
                .onSuccess {
                    withContext(dispatcherProvider.main) {
                        _uiState.value = MainUiState.Loading(false)
                        _uiState.value = MainUiState.GenreDataLoaded(it)
                    }
                }
                .onError {
                    withContext(dispatcherProvider.main) {
                        _uiState.value = MainUiState.Loading(false)
                        _uiState.value = MainUiState.FailedLoadData(it)
                    }
                }
        }
    }

    fun getPopularPeople(page: String){
        _uiState.value = MainUiState.Loading(true)
        viewModelScope.launch(dispatcherProvider.io) {
            getPopularPeopleUseCase.run(page)
                .onSuccess {
                    withContext(dispatcherProvider.main) {
                        _uiState.value = MainUiState.Loading(false)
                        _uiState.value = MainUiState.PopularPeopleDataLoaded(it)
                    }
                }
                .onError {
                    withContext(dispatcherProvider.main) {
                        _uiState.value = MainUiState.Loading(false)
                        _uiState.value = MainUiState.FailedLoadData(it)
                    }
                }
        }
    }

    fun getSearchMovie(query: String, page: String){
        _uiState.value = MainUiState.Loading(true)
        viewModelScope.launch(dispatcherProvider.io) {
            getSearchMovieUseCase.run(GetSearchMovieUseCase.Params(query, page))
                .onSuccess {
                    withContext(dispatcherProvider.main) {
                        _uiState.value = MainUiState.Loading(false)
                        _uiState.value = MainUiState.SearchMovieLoaded(it)
                    }
                }
                .onError {
                    withContext(dispatcherProvider.main) {
                        _uiState.value = MainUiState.Loading(false)
                        _uiState.value = MainUiState.FailedLoadData(it)
                    }
                }
        }
    }

}
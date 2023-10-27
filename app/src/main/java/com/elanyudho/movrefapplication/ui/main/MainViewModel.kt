package com.elanyudho.movrefapplication.ui.main

import androidx.lifecycle.viewModelScope
import com.denzcoskun.imageslider.models.SlideModel
import com.elanyudho.core.abstraction.BaseViewModel
import com.elanyudho.core.abstraction.UseCase
import com.elanyudho.core.dispatcher.DispatcherProvider
import com.elanyudho.core.domain.model.Genre
import com.elanyudho.core.domain.model.MovieItem
import com.elanyudho.core.domain.model.PeopleItem
import com.elanyudho.core.domain.usecase.genre.GetGenreUseCase
import com.elanyudho.core.domain.usecase.movie.GetPopularMovieUseCase
import com.elanyudho.core.domain.usecase.movie.GetSearchMovieUseCase
import com.elanyudho.core.domain.usecase.movie.GetTopRatedMovieUseCase
import com.elanyudho.core.domain.usecase.movie.GetTrendingMovieUseCase
import com.elanyudho.core.domain.usecase.movie.GetUpcomingMovieUseCase
import com.elanyudho.core.domain.usecase.people.GetPopularPeopleUseCase
import com.elanyudho.core.exception.Failure
import com.elanyudho.core.extension.onError
import com.elanyudho.core.extension.onSuccess
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
        data class LoadingMovieData(val isLoading: Boolean): MainUiState()
        data class LoadingTrendingMovieData(val isLoading: Boolean): MainUiState()
        data class LoadingPopularPeopleDataData(val isLoading: Boolean): MainUiState()
        data class LoadingGenreData(val isLoading: Boolean): MainUiState()
        data class LoadingSearchData(val isLoading: Boolean): MainUiState()
        data class MovieDataLoaded(val movieList: List<MovieItem>): MainUiState()
        data class TrendingMovieDataLoaded(val movieList: List<SlideModel>): MainUiState()
        data class PopularPeopleDataLoaded(val peopleList: List<PeopleItem>): MainUiState()
        data class SearchMovieLoaded(val movieList: List<MovieItem>): MainUiState()
        data class GenreDataLoaded(val genreList: List<Genre>): MainUiState()
        data class FailedLoadMovieData(val failure: Failure): MainUiState()
        data class FailedLoadTrendingMovieData(val failure: Failure): MainUiState()
        data class FailedLoadPopularPeopleData(val failure: Failure): MainUiState()
        data class FailedLoadSearchMovieData(val failure: Failure): MainUiState()
        data class FailedLoadGenreData(val failure: Failure): MainUiState()
    }

    fun getPopularMovie(page: String){
        _uiState.value = MainUiState.LoadingMovieData(true)
        viewModelScope.launch(dispatcherProvider.io) {
            getPopularMovie.run(page)
                .onSuccess {
                    withContext(dispatcherProvider.main) {
                        _uiState.value = MainUiState.LoadingMovieData(false)
                        _uiState.value = MainUiState.MovieDataLoaded(it)
                    }
                }
                .onError {
                    withContext(dispatcherProvider.main) {
                        _uiState.value = MainUiState.LoadingMovieData(false)
                        _uiState.value = MainUiState.FailedLoadMovieData(it)
                    }
                }
        }
    }

    fun getTopRatedMovie(page: String){
        _uiState.value = MainUiState.LoadingMovieData(true)
        viewModelScope.launch(dispatcherProvider.io) {
            getTopRatedMovieUseCase.run(page)
                .onSuccess {
                    withContext(dispatcherProvider.main) {
                        _uiState.value = MainUiState.LoadingMovieData(false)
                        _uiState.value = MainUiState.MovieDataLoaded(it)
                    }
                }
                .onError {
                    withContext(dispatcherProvider.main) {
                        _uiState.value = MainUiState.LoadingMovieData(false)
                        _uiState.value = MainUiState.FailedLoadMovieData(it)
                    }
                }
        }
    }

    fun getUpcomingMovie(page: String){
        _uiState.value = MainUiState.LoadingMovieData(true)
        viewModelScope.launch(dispatcherProvider.io) {
            getUpcomingMovieUseCase.run(page)
                .onSuccess {
                    withContext(dispatcherProvider.main) {
                        _uiState.value = MainUiState.LoadingMovieData(false)
                        _uiState.value = MainUiState.MovieDataLoaded(it)
                    }
                }
                .onError {
                    withContext(dispatcherProvider.main) {
                        _uiState.value = MainUiState.LoadingMovieData(false)
                        _uiState.value = MainUiState.FailedLoadMovieData(it)
                    }
                }
        }
    }

    fun getTrendingMovie(page: String){
        _uiState.value = MainUiState.LoadingTrendingMovieData(true)
        viewModelScope.launch(dispatcherProvider.io) {
            getTrendingMovieUseCase.run(page)
                .onSuccess {
                    withContext(dispatcherProvider.main) {
                        _uiState.value = MainUiState.LoadingTrendingMovieData(false)

                        val movieBanner = ArrayList<SlideModel>()
                        for (i in 0..4) {
                            movieBanner.add(
                                SlideModel(
                                    it[i].movieBackDrop,
                                    it[i].movieName
                                )
                            )
                        }
                        _uiState.value = MainUiState.TrendingMovieDataLoaded(movieBanner)
                    }
                }
                .onError {
                    withContext(dispatcherProvider.main) {
                        _uiState.value = MainUiState.LoadingTrendingMovieData(false)
                        _uiState.value = MainUiState.FailedLoadTrendingMovieData(it)
                    }
                }
        }
    }

    fun getGenre(){
        _uiState.value = MainUiState.LoadingGenreData(true)
        viewModelScope.launch(dispatcherProvider.io) {
            getGenreUseCase.run(UseCase.None)
                .onSuccess {
                    withContext(dispatcherProvider.main) {
                        _uiState.value = MainUiState.LoadingGenreData(false)
                        _uiState.value = MainUiState.GenreDataLoaded(it)
                    }
                }
                .onError {
                    withContext(dispatcherProvider.main) {
                        _uiState.value = MainUiState.LoadingGenreData(false)
                        _uiState.value = MainUiState.FailedLoadGenreData(it)
                    }
                }
        }
    }

    fun getPopularPeople(page: String){
        _uiState.value = MainUiState.LoadingPopularPeopleDataData(true)
        viewModelScope.launch(dispatcherProvider.io) {
            getPopularPeopleUseCase.run(page)
                .onSuccess {
                    withContext(dispatcherProvider.main) {
                        _uiState.value = MainUiState.LoadingPopularPeopleDataData(false)
                        _uiState.value = MainUiState.PopularPeopleDataLoaded(it)
                    }
                }
                .onError {
                    withContext(dispatcherProvider.main) {
                        _uiState.value = MainUiState.LoadingPopularPeopleDataData(false)
                        _uiState.value = MainUiState.FailedLoadPopularPeopleData(it)
                    }
                }
        }
    }

    fun getSearchMovie(query: String, page: String){
        _uiState.value = MainUiState.LoadingSearchData(true)
        viewModelScope.launch(dispatcherProvider.io) {
            getSearchMovieUseCase.run(GetSearchMovieUseCase.Params(query, page))
                .onSuccess {
                    withContext(dispatcherProvider.main) {
                        _uiState.value = MainUiState.LoadingSearchData(false)
                        _uiState.value = MainUiState.SearchMovieLoaded(it)
                    }
                }
                .onError {
                    withContext(dispatcherProvider.main) {
                        _uiState.value = MainUiState.LoadingSearchData(false)
                        _uiState.value = MainUiState.FailedLoadSearchMovieData(it)
                    }
                }
        }
    }

}
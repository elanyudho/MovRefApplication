package com.elanyudho.movrefapplication.ui.moremovie

import androidx.lifecycle.viewModelScope
import com.elanyudho.core.abstraction.BaseViewModel
import com.elanyudho.core.dispatcher.DispatcherProvider
import com.elanyudho.core.domain.model.MovieItem
import com.elanyudho.core.domain.usecase.movie.GetPopularMovieUseCase
import com.elanyudho.core.domain.usecase.movie.GetTopRatedMovieUseCase
import com.elanyudho.core.domain.usecase.movie.GetTrendingMovieUseCase
import com.elanyudho.core.domain.usecase.movie.GetUpcomingMovieUseCase
import com.elanyudho.core.exception.Failure
import com.elanyudho.core.extension.onError
import com.elanyudho.core.extension.onSuccess
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MoreMovieViewModel @Inject constructor(
    private val dispatcherProvider: DispatcherProvider,
    private val getPopularMovie: GetPopularMovieUseCase,
    private val getTopRatedMovieUseCase: GetTopRatedMovieUseCase,
    private val getUpcomingMovieUseCase: GetUpcomingMovieUseCase,
    private val getTrendingMovieUseCase: GetTrendingMovieUseCase,
): BaseViewModel<MoreMovieViewModel.MoreUiState>(){

    sealed class MoreUiState {
        object InitialLoading: MoreUiState()
        object PagingLoading: MoreUiState()
        data class MovieDataLoaded(val movieList: List<MovieItem>): MoreUiState()
        data class FailedLoadData(val failure: Failure): MoreUiState()
    }

    fun getPopularMovie(page: Long){
        _uiState.value = if (page == 1L) {
            MoreUiState.InitialLoading
        }else{
            MoreUiState.PagingLoading
        }

        viewModelScope.launch(dispatcherProvider.io) {
            getPopularMovie.run(page.toString())
                .onSuccess {
                    withContext(dispatcherProvider.main) {
                        _uiState.value = MoreUiState.MovieDataLoaded(it)
                    }
                }
                .onError {
                    withContext(dispatcherProvider.main) {
                        _uiState.value = MoreUiState.FailedLoadData(it)
                    }
                }
        }
    }

    fun getTopRatedMovie(page: Long){
        _uiState.value = if (page == 1L) {
            MoreUiState.InitialLoading
        }else{
            MoreUiState.PagingLoading
        }

        viewModelScope.launch(dispatcherProvider.io) {
            getTopRatedMovieUseCase.run(page.toString())
                .onSuccess {
                    withContext(dispatcherProvider.main) {
                        _uiState.value = MoreUiState.MovieDataLoaded(it)
                    }
                }
                .onError {
                    withContext(dispatcherProvider.main) {
                        _uiState.value = MoreUiState.FailedLoadData(it)
                    }
                }
        }
    }

    fun getUpcomingMovie(page: Long){
        _uiState.value = if (page == 1L) {
            MoreUiState.InitialLoading
        }else{
            MoreUiState.PagingLoading
        }

        viewModelScope.launch(dispatcherProvider.io) {
            getUpcomingMovieUseCase.run(page.toString())
                .onSuccess {
                    withContext(dispatcherProvider.main) {
                        _uiState.value = MoreUiState.MovieDataLoaded(it)
                    }
                }
                .onError {
                    withContext(dispatcherProvider.main) {
                        _uiState.value = MoreUiState.FailedLoadData(it)
                    }
                }
        }
    }

    fun getTrendingMovie(page: Long){
        _uiState.value = if (page == 1L) {
            MoreUiState.InitialLoading
        }else{
            MoreUiState.PagingLoading
        }

        viewModelScope.launch(dispatcherProvider.io) {
            getTrendingMovieUseCase.run(page.toString())
                .onSuccess {
                    withContext(dispatcherProvider.main) {
                        _uiState.value = MoreUiState.MovieDataLoaded(it)
                    }
                }
                .onError {
                    withContext(dispatcherProvider.main) {
                        _uiState.value = MoreUiState.FailedLoadData(it)
                    }
                }
        }
    }


}
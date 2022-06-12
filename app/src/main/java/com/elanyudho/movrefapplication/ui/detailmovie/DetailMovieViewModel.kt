package com.elanyudho.movrefapplication.ui.detailmovie

import androidx.lifecycle.viewModelScope
import com.elanyudho.core.abstraction.BaseViewModel
import com.elanyudho.core.dispatcher.DispatcherProvider
import com.elanyudho.core.exception.Failure
import com.elanyudho.core.extension.onError
import com.elanyudho.core.extension.onSuccess
import com.elanyudho.movrefapplication.domain.model.CreditsMovie
import com.elanyudho.movrefapplication.domain.model.DetailMovie
import com.elanyudho.movrefapplication.domain.model.MovieItem
import com.elanyudho.movrefapplication.domain.usecase.movie.GetCreditsMovieUseCase
import com.elanyudho.movrefapplication.domain.usecase.movie.GetDetailMovieUseCase
import com.elanyudho.movrefapplication.domain.usecase.movie.GetRecommendationMovieUseCase
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class DetailMovieViewModel @Inject constructor(
    private val dispatcherProvider: DispatcherProvider,
    private val getDetailMovieUseCase: GetDetailMovieUseCase,
    private val getCreditsMovieUseCase: GetCreditsMovieUseCase,
    private val getRecommendationMovieUseCase: GetRecommendationMovieUseCase
): BaseViewModel<DetailMovieViewModel.DetailUiState>() {

    sealed class DetailUiState {
        data class Loading(val isLoading: Boolean) : DetailUiState()
        data class DetailMovieDataLoaded(val detail: DetailMovie) : DetailUiState()
        data class CreditsMovieDataLoaded(val credits: List<CreditsMovie>) : DetailUiState()
        data class RecommendationMovieDataLoaded(val listMovie: List<MovieItem>) : DetailUiState()
        data class FailedLoadData(val failure: Failure) : DetailUiState()
    }

    fun getDetailMovie(id: String) {
        _uiState.value = DetailUiState.Loading(true)
        viewModelScope.launch(dispatcherProvider.io) {
            getDetailMovieUseCase.run(id)
                .onSuccess {
                    withContext(dispatcherProvider.main) {
                        _uiState.value = DetailUiState.Loading(false)
                        _uiState.value = DetailUiState.DetailMovieDataLoaded(it)
                    }
                }
                .onError {
                    withContext(dispatcherProvider.main) {
                        _uiState.value = DetailUiState.Loading(false)
                        _uiState.value = DetailUiState.FailedLoadData(it)
                    }
                }
        }
    }

    fun getCreditsMovie(id: String) {
        _uiState.value = DetailUiState.Loading(true)
        viewModelScope.launch(dispatcherProvider.io) {
            getCreditsMovieUseCase.run(id)
                .onSuccess {
                    withContext(dispatcherProvider.main) {
                        _uiState.value = DetailUiState.Loading(false)
                        _uiState.value = DetailUiState.CreditsMovieDataLoaded(it)
                    }
                }
                .onError {
                    withContext(dispatcherProvider.main) {
                        _uiState.value = DetailUiState.Loading(false)
                        _uiState.value = DetailUiState.FailedLoadData(it)
                    }
                }
        }
    }

    fun getRecommendationMovie(id: String, page: String) {
        _uiState.value = DetailUiState.Loading(true)
        viewModelScope.launch(dispatcherProvider.io) {
            getRecommendationMovieUseCase.run(GetRecommendationMovieUseCase.Params(id, page))
                .onSuccess {
                    withContext(dispatcherProvider.main) {
                        _uiState.value = DetailUiState.Loading(false)
                        _uiState.value = DetailUiState.RecommendationMovieDataLoaded(it)
                    }
                }
                .onError {
                    withContext(dispatcherProvider.main) {
                        _uiState.value = DetailUiState.Loading(false)
                        _uiState.value = DetailUiState.FailedLoadData(it)
                    }
                }
        }
    }
}
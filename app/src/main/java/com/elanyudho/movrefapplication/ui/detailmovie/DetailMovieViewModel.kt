package com.elanyudho.movrefapplication.ui.detailmovie

import androidx.lifecycle.viewModelScope
import com.elanyudho.core.abstraction.BaseViewModel
import com.elanyudho.core.dispatcher.DispatcherProvider
import com.elanyudho.core.domain.model.CreditsMovie
import com.elanyudho.core.domain.model.DetailMovie
import com.elanyudho.core.domain.model.MovieItem
import com.elanyudho.core.domain.model.Review
import com.elanyudho.core.domain.model.Video
import com.elanyudho.core.domain.usecase.movie.GetCreditsMovieUseCase
import com.elanyudho.core.domain.usecase.movie.GetDetailMovieUseCase
import com.elanyudho.core.domain.usecase.movie.GetRecommendationMovieUseCase
import com.elanyudho.core.domain.usecase.movie.GetReviewMovieUseCase
import com.elanyudho.core.domain.usecase.movie.GetVideoMovieUseCase
import com.elanyudho.core.exception.Failure
import com.elanyudho.core.extension.onError
import com.elanyudho.core.extension.onSuccess

import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class DetailMovieViewModel @Inject constructor(
    private val dispatcherProvider: DispatcherProvider,
    private val getDetailMovieUseCase: GetDetailMovieUseCase,
    private val getCreditsMovieUseCase: GetCreditsMovieUseCase,
    private val getRecommendationMovieUseCase: GetRecommendationMovieUseCase,
    private val getReviewMovieUseCase: GetReviewMovieUseCase,
    private val getVideoMovieUseCase: GetVideoMovieUseCase
) : BaseViewModel<DetailMovieViewModel.DetailUiState>() {

    sealed class DetailUiState {
        data class Loading(val isLoading: Boolean) : DetailUiState()
        data class DetailMovieDataLoaded(val detail: DetailMovie) : DetailUiState()
        data class CreditsMovieDataLoaded(val credits: List<CreditsMovie>) : DetailUiState()
        data class RecommendationMovieDataLoaded(val listMovie: List<MovieItem>) : DetailUiState()
        data class FailedLoadData(val failure: Failure) : DetailUiState()
        object InitialLoading : DetailUiState()
        object PagingLoading : DetailUiState()
        data class ReviewMovieDataLoaded(val reviews: List<Review>) : DetailUiState()
        data class VideoMovieDataLoaded(val videos: List<Video>) : DetailUiState()
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

    fun getReviewMovie(id: String, page: Long) {
        _uiState.value = if (page == 1L) {
            DetailUiState.InitialLoading
        } else {
            DetailUiState.PagingLoading
        }
        viewModelScope.launch(dispatcherProvider.io) {
            getReviewMovieUseCase.run(GetReviewMovieUseCase.Params(id, page.toString()))
                .onSuccess {
                    withContext(dispatcherProvider.main) {
                        _uiState.value = DetailUiState.ReviewMovieDataLoaded(it)
                    }
                }
                .onError {
                    withContext(dispatcherProvider.main) {
                        _uiState.value = DetailUiState.FailedLoadData(it)
                    }
                }
        }
    }

    fun getVideoMovie(id: String) {
        _uiState.value = DetailUiState.Loading(true)
        viewModelScope.launch(dispatcherProvider.io) {
            getVideoMovieUseCase.run(id)
                .onSuccess {
                    withContext(dispatcherProvider.main) {
                        _uiState.value = DetailUiState.Loading(false)
                        _uiState.value = DetailUiState.VideoMovieDataLoaded(it)
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
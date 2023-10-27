package com.elanyudho.movrefapplication.ui.genre.listmoviebygenre

import androidx.lifecycle.viewModelScope
import com.elanyudho.core.abstraction.BaseViewModel
import com.elanyudho.core.dispatcher.DispatcherProvider
import com.elanyudho.core.domain.model.MovieItem
import com.elanyudho.core.domain.usecase.genre.GetMovieGenreUseCase
import com.elanyudho.core.exception.Failure
import com.elanyudho.core.extension.onError
import com.elanyudho.core.extension.onSuccess
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MovieGenreViewModel @Inject constructor(
    private val dispatcherProvider: DispatcherProvider,
    private val getMovieGenreUseCase: GetMovieGenreUseCase
): BaseViewModel<MovieGenreViewModel.MovieGenreUiState>(){

    sealed class MovieGenreUiState {
        object InitialLoading: MovieGenreUiState()
        object PagingLoading: MovieGenreUiState()
        data class MovieDataLoaded(val movieList: List<MovieItem>): MovieGenreUiState()
        data class FailedLoadData(val failure: Failure): MovieGenreUiState()
    }

    fun getMovieGenre(page: Long, genreId: String){
        _uiState.value = if (page == 1L) {
            MovieGenreUiState.InitialLoading
        }else{
            MovieGenreUiState.PagingLoading
        }
        viewModelScope.launch(dispatcherProvider.io) {
            getMovieGenreUseCase.run(GetMovieGenreUseCase.Params(page.toString(), genreId))
                .onSuccess {
                    withContext(dispatcherProvider.main) {
                        _uiState.value = MovieGenreUiState.MovieDataLoaded(it)
                    }
                }
                .onError {
                    withContext(dispatcherProvider.main) {
                        _uiState.value = MovieGenreUiState.FailedLoadData(it)
                    }
                }
        }
    }
}
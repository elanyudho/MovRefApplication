package com.elanyudho.movrefapplication.ui.genre.listgenre

import androidx.lifecycle.viewModelScope
import com.elanyudho.core.abstraction.BaseViewModel
import com.elanyudho.core.abstraction.UseCase
import com.elanyudho.core.dispatcher.DispatcherProvider
import com.elanyudho.core.domain.model.Genre
import com.elanyudho.core.domain.usecase.genre.GetGenreUseCase
import com.elanyudho.core.exception.Failure
import com.elanyudho.core.extension.onError
import com.elanyudho.core.extension.onSuccess
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ListGenreViewModel @Inject constructor(
    private val dispatcherProvider: DispatcherProvider,
    private val getGenreUseCase: GetGenreUseCase
) : BaseViewModel<ListGenreViewModel.ListGenreUiState>() {

    sealed class ListGenreUiState() {
        data class GenreDataLoaded(val genreList: List<Genre>): ListGenreUiState()
        data class Loading(val isLoading: Boolean): ListGenreUiState()
        data class FailedLoadData(val failure: Failure): ListGenreUiState()
    }

    fun getGenre(){
        _uiState.value = ListGenreUiState.Loading(true)
        viewModelScope.launch(dispatcherProvider.io) {
            getGenreUseCase.run(UseCase.None)
                .onSuccess {
                    withContext(dispatcherProvider.main) {
                        _uiState.value = ListGenreUiState.Loading(false)
                        _uiState.value = ListGenreUiState.GenreDataLoaded(it)
                    }
                }
                .onError {
                    withContext(dispatcherProvider.main) {
                        _uiState.value = ListGenreUiState.Loading(false)
                        _uiState.value = ListGenreUiState.FailedLoadData(it)
                    }
                }
        }
    }
}
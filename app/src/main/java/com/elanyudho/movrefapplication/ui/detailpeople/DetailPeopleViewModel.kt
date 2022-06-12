package com.elanyudho.movrefapplication.ui.detailpeople

import androidx.lifecycle.viewModelScope
import com.elanyudho.core.abstraction.BaseViewModel
import com.elanyudho.core.dispatcher.DispatcherProvider
import com.elanyudho.core.exception.Failure
import com.elanyudho.core.extension.onError
import com.elanyudho.core.extension.onSuccess
import com.elanyudho.movrefapplication.domain.model.CreditsPeople
import com.elanyudho.movrefapplication.domain.model.DetailPeople
import com.elanyudho.movrefapplication.domain.model.PeopleImage
import com.elanyudho.movrefapplication.domain.usecase.people.GetCreditsPeopleUseCase
import com.elanyudho.movrefapplication.domain.usecase.people.GetDetailPeopleUseCase
import com.elanyudho.movrefapplication.domain.usecase.people.GetImagePeopleUseCase
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class DetailPeopleViewModel @Inject constructor(
    private val dispatcherProvider: DispatcherProvider,
    private val getDetailPeopleUseCase: GetDetailPeopleUseCase,
    private val getCreditsPeopleUseCase: GetCreditsPeopleUseCase,
    private val getImagePeopleUseCase: GetImagePeopleUseCase
): BaseViewModel<DetailPeopleViewModel.DetailUiState>() {

    sealed class DetailUiState {
        data class Loading(val isLoading: Boolean) : DetailUiState()
        data class DetailPeopleDataLoaded(val detail: DetailPeople) : DetailUiState()
        data class CreditsPeopleDataLoaded(val credits: List<CreditsPeople>) : DetailUiState()
        data class ImagePeopleLoaded(val listMovie: List<PeopleImage>) : DetailUiState()
        data class FailedLoadData(val failure: Failure) : DetailUiState()
    }

    fun getDetailPeople(id: String) {
        _uiState.value = DetailUiState.Loading(true)
        viewModelScope.launch(dispatcherProvider.io) {
            getDetailPeopleUseCase.run(id)
                .onSuccess {
                    withContext(dispatcherProvider.main) {
                        _uiState.value = DetailUiState.Loading(false)
                        _uiState.value = DetailUiState.DetailPeopleDataLoaded(it)
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

    fun getCreditsPeople(id: String) {
        _uiState.value = DetailUiState.Loading(true)
        viewModelScope.launch(dispatcherProvider.io) {
            getCreditsPeopleUseCase.run(id)
                .onSuccess {
                    withContext(dispatcherProvider.main) {
                        _uiState.value = DetailUiState.Loading(false)
                        _uiState.value = DetailUiState.CreditsPeopleDataLoaded(it)
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

    fun getImagePeople(id: String) {
        _uiState.value = DetailUiState.Loading(true)
        viewModelScope.launch(dispatcherProvider.io) {
            getImagePeopleUseCase.run(id)
                .onSuccess {
                    withContext(dispatcherProvider.main) {
                        _uiState.value = DetailUiState.Loading(false)
                        _uiState.value = DetailUiState.ImagePeopleLoaded(it)
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
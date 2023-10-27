package com.elanyudho.movrefapplication.ui.morepeople

import androidx.lifecycle.viewModelScope
import com.elanyudho.core.abstraction.BaseViewModel
import com.elanyudho.core.dispatcher.DispatcherProvider
import com.elanyudho.core.domain.model.PeopleItem
import com.elanyudho.core.domain.usecase.people.GetPopularPeopleUseCase
import com.elanyudho.core.domain.usecase.people.GetSearchPeopleUseCase
import com.elanyudho.core.exception.Failure
import com.elanyudho.core.extension.onError
import com.elanyudho.core.extension.onSuccess

import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MorePeopleViewModel @Inject constructor(
    private val dispatcherProvider: DispatcherProvider,
    private val getPopularPeopleUseCase: GetPopularPeopleUseCase,
    private val getSearchPeopleUseCase: GetSearchPeopleUseCase
): BaseViewModel<MorePeopleViewModel.MoreUiState>(){

    sealed class MoreUiState {
        object InitialLoading: MoreUiState()
        object PagingLoading: MoreUiState()
        data class Loading(val isLoading: Boolean): MoreUiState()
        data class PeopleDataLoaded(val peopleList: List<PeopleItem>): MoreUiState()
        data class SearchPeopleDataLoaded(val peopleList: List<PeopleItem>): MoreUiState()
        data class FailedLoadData(val failure: Failure): MoreUiState()
    }

    fun getPopularPeople(page: Long){
        _uiState.value = if (page == 1L) {
            MoreUiState.InitialLoading
        }else{
            MoreUiState.PagingLoading
        }

        viewModelScope.launch(dispatcherProvider.io) {
            getPopularPeopleUseCase.run(page.toString())
                .onSuccess {
                    withContext(dispatcherProvider.main) {
                        _uiState.value = MoreUiState.PeopleDataLoaded(it)
                    }
                }
                .onError {
                    withContext(dispatcherProvider.main) {
                        _uiState.value = MoreUiState.FailedLoadData(it)
                    }
                }
        }
    }

    fun getSearchPeople(query: String, page: String){
        _uiState.value = MoreUiState.Loading(true)
        viewModelScope.launch(dispatcherProvider.io) {
            getSearchPeopleUseCase.run(GetSearchPeopleUseCase.Params(query, page))
                .onSuccess {
                    withContext(dispatcherProvider.main) {
                        _uiState.value = MoreUiState.Loading(false)
                        _uiState.value = MoreUiState.SearchPeopleDataLoaded(it)
                    }
                }
                .onError {
                    withContext(dispatcherProvider.main) {
                        _uiState.value = MoreUiState.Loading(false)
                        _uiState.value = MoreUiState.FailedLoadData(it)
                    }
                }
        }
    }


}
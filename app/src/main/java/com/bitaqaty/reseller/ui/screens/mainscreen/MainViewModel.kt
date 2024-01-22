package com.bitaqaty.reseller.ui.screens.mainscreen

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bitaqaty.reseller.data.model.BaseModel
import com.bitaqaty.reseller.data.model.Genres
import com.bitaqaty.reseller.data.repository.MovieRepository
import com.bitaqaty.reseller.utils.network.DataState

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val repo: MovieRepository) : ViewModel() {
    val genres: MutableState<DataState<Genres>?> = mutableStateOf(null)
    val searchData: MutableState<DataState<BaseModel>?> = mutableStateOf(null)

    fun genreList() {
        viewModelScope.launch {
            repo.genreList().onEach {
                genres.value = it
            }.launchIn(viewModelScope)
        }
    }
    @ExperimentalCoroutinesApi
    @FlowPreview
    fun searchApi(searchKey: String) {
        viewModelScope.launch {
            flowOf(searchKey).debounce(300)
                .filter {
                    it.trim().isEmpty().not()
                }
                .distinctUntilChanged()
                .flatMapLatest {
                    repo.search(it)
                }.collect {
                    if (it is DataState.Success){
                        it.data
                        Timber.e(" data ${it.data.totalPages}")
                    }
                    searchData.value = it
                }
        }
    }
}
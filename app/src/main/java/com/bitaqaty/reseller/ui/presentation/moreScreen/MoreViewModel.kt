package com.bitaqaty.reseller.ui.presentation.moreScreen


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bitaqaty.reseller.data.model.ErrorMessage
import com.bitaqaty.reseller.domain.AuthenticationUseCase
import com.bitaqaty.reseller.utilities.network.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MoreViewModel @Inject constructor(private val repo: AuthenticationUseCase) :
    ViewModel() {
    private val _getLogout =
        MutableSharedFlow<Resource<ErrorMessage>>()
    val getLogout: MutableSharedFlow<Resource<ErrorMessage>>
        get() = _getLogout

    fun logout() {
        viewModelScope.launch {
            repo.logout()
                .catch {

                }
                .onEach {
                    _getLogout.emit(it)
                }.launchIn(viewModelScope)
        }
    }

}
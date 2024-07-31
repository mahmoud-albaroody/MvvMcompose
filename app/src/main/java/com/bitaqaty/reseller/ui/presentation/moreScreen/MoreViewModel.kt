package com.bitaqaty.reseller.ui.presentation.moreScreen


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bitaqaty.reseller.domain.AuthenticationUseCase
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
        MutableSharedFlow<Void>()
    val getLogout: MutableSharedFlow<Void>
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
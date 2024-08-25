package com.bitaqaty.reseller.ui.presentation.home

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bitaqaty.reseller.data.model.Category
import com.bitaqaty.reseller.data.model.SystemSettings
import com.bitaqaty.reseller.data.model.User
import com.bitaqaty.reseller.data.repository.BBRepository
import com.bitaqaty.reseller.domain.SettingUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repo: BBRepository, private val settingUseCase: SettingUseCase
) : ViewModel() {
    //    private val _categoryState =
//        mutableStateOf<DataState<ArrayList<Category>>?>(DataState.Loading)
//    val categoryState: State<DataState<ArrayList<Category>>?> = _categoryState
    val categoryState: MutableState<Nothing?> = mutableStateOf(null)

    private val _getProfile =
        MutableSharedFlow<User>()
    val getProfile: MutableSharedFlow<User>
        get() = _getProfile

    private val _getSystemSetting =
        MutableSharedFlow<ArrayList<SystemSettings>>()
    val getSystemSetting: MutableSharedFlow<ArrayList<SystemSettings>>
        get() = _getSystemSetting


    fun getCategoryList() {
//        viewModelScope.launch {
//            repo.getCategoryList()
//                .catch {
//                    Log.e("dddd", "sdfdsf")
//                }
//                .onEach {
//                 //   categoryState.value = it
//                }.launchIn(viewModelScope)
//        }
    }

    fun getProfile() {
        viewModelScope.launch {
            settingUseCase.getProfile()
                .catch {

                }
                .onEach {
                    _getProfile.emit(it)
                }.launchIn(viewModelScope)
        }
    }

    fun getSystemSetting() {
        viewModelScope.launch {
            settingUseCase.getSystemSettings()
                .catch {

                }
                .onEach {
                    _getSystemSetting.emit(it)
                }.launchIn(viewModelScope)
        }
    }

}
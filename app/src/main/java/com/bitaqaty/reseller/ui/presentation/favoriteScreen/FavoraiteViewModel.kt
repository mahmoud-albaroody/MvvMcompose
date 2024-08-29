package com.bitaqaty.reseller.ui.presentation.favoriteScreen


import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bitaqaty.reseller.data.model.FavoriteRequest
import com.bitaqaty.reseller.data.model.Product
import com.bitaqaty.reseller.data.model.ProductListResult
import com.bitaqaty.reseller.data.repository.BBRepository
import com.bitaqaty.reseller.utilities.network.DataState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoraiteViewModel @Inject constructor(
    private val repo: BBRepository
) : ViewModel() {
    private val _editFavoriteState =
        mutableStateOf<DataState<Unit>>(DataState.Loading)
    val editFavoriteState: State<DataState<Unit>> = _editFavoriteState

    private val _favoriteListState =
        mutableStateOf<DataState<ArrayList<Product>>>(DataState.Loading)
    val favoriteListState: State<DataState<ArrayList<Product>>> = _favoriteListState
    fun addFavoriteProduct(product: Product){
        val request = FavoriteRequest(productId = product.getProductsId())
        viewModelScope.launch {
            repo.addFavoriteProduct(request).collect{ state ->
                _editFavoriteState.value = state
                if(state is DataState.Success){
                    product.isFavorite = true
                }
            }
        }
    }

    fun deleteFavoriteProduct(product: Product){
        val request = FavoriteRequest(productId = product.getProductsId())
        viewModelScope.launch {
            repo.deleteFavoriteProduct(request).collect{ state ->
                _editFavoriteState.value = state
                if(state is DataState.Success){
                    product.isFavorite = false
                }
            }
        }
    }

    fun getFavoriteProducts(){
        viewModelScope.launch {
            repo.getFavoriteProducts().collect{ state ->
                _favoriteListState.value = state
            }
        }
    }
}
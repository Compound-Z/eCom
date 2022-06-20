package vn.ztech.software.ecom.ui.cart

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import vn.ztech.software.ecom.common.LoadState
import vn.ztech.software.ecom.common.StoreDataStatus
import vn.ztech.software.ecom.common.extension.toLoadState
import vn.ztech.software.ecom.model.Product
import vn.ztech.software.ecom.model.ProductDetails
import vn.ztech.software.ecom.util.CustomError

class CartViewModel(private val cartUseCase: ICartUseCase): ViewModel() {
    val loading = MutableLiveData<Boolean>()
    val products = MutableLiveData<Product>()
    val addProductStatus = MutableLiveData<Boolean>()
    val error = MutableLiveData<CustomError>()

    fun addProductToCart(productId: String?) {
        productId?:throw CustomError(customMessage = "System error")
        viewModelScope.launch {
            cartUseCase.addProductToCart(productId).flowOn(Dispatchers.IO).toLoadState().collect {
                when(it){
                    LoadState.Loading -> {
                        loading.value = true
                    }
                    is LoadState.Loaded -> {
                        loading.value = false
                        addProductStatus.value = true
                    }
                    is LoadState.Error -> {
                        loading.value = false
                        addProductStatus.value = false
                    }
                }
            }
        }
    }
}
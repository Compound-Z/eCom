package vn.ztech.software.ecom.ui.cart

import android.util.Log
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
import vn.ztech.software.ecom.util.errorMessage

class CartViewModel(private val cartUseCase: ICartUseCase): ViewModel() {
    val loading = MutableLiveData<Boolean>()
    val products = MutableLiveData<MutableList<Product>>()
    val addProductStatus = MutableLiveData<Boolean>()
    val deleteProductStatus = MutableLiveData<Boolean>()
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
    fun deleteProductFromCart(productId: String?) {
        productId?:throw CustomError(customMessage = "System error")
        viewModelScope.launch {
            cartUseCase.deleteProductFromCart(productId).flowOn(Dispatchers.IO).toLoadState().collect {
                when(it){
                    LoadState.Loading -> {
                        loading.value = true
                    }
                    is LoadState.Loaded -> {
                        loading.value = false

                        //remove the product from cart in local memory
                        val deletedProductIdx = products.value?.indexOfFirst { it._id == productId }
                        deletedProductIdx?.let {products.value?.removeAt(deletedProductIdx)}
                        Log.d("PRODUCT", products.value.toString())
                        deleteProductStatus.value = true
                    }
                    is LoadState.Error -> {
                        loading.value = false
                        deleteProductStatus.value = false
                    }
                }
            }
        }
    }
    fun getListProductsInCart(isLoadingEnabled: Boolean = true){
        viewModelScope.launch {
            cartUseCase.getListProductsInCart().flowOn(Dispatchers.IO).toLoadState().collect {
                when(it){
                    LoadState.Loading -> {
                        if(isLoadingEnabled) loading.value = true
                    }
                    is LoadState.Loaded -> {
                        loading.value = false
                        products.value = it.data.toMutableList()
                        Log.d("getListProductsInCart", products.value.toString())
                    }
                    is LoadState.Error -> {
                        loading.value = false
                        error.value = errorMessage(it.e)
                    }
                }
            }
        }
    }
}
package vn.ztech.software.ecom.ui.address

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import vn.ztech.software.ecom.common.LoadState
import vn.ztech.software.ecom.common.extension.toLoadState
import vn.ztech.software.ecom.model.Address
import vn.ztech.software.ecom.util.CustomError
import vn.ztech.software.ecom.util.errorMessage

class AddressViewModel(private val addressUseCase: IAddressUseCase): ViewModel() {
    val loading = MutableLiveData<Boolean>()
    val addresses = MutableLiveData<Address>()
    val addAddressStatus = MutableLiveData<Boolean>()
    val deleteAddressStatus = MutableLiveData<Boolean>()
    val editAddressStatus = MutableLiveData<Boolean>()
    val error = MutableLiveData<CustomError>()


//    fun addAddress(productId: String?, isLoadingEnabled: Boolean = true) {
//        productId?:throw CustomError(customMessage = "System error")
//        viewModelScope.launch {
//            addressUseCase.addAddress(productId).flowOn(Dispatchers.IO).toLoadState().collect {
//                when(it){
//                    LoadState.Loading -> {
//                        if(isLoadingEnabled) loading.value = true
//                    }
//                    is LoadState.Loaded -> {
//                        loading.value = false
//                        addAddressStatus.value = true
//                    }
//                    is LoadState.Error -> {
//                        loading.value = false
//                        addAddressStatus.value = false
//                        error.value = errorMessage(it.e)
//                    }
//                }
//            }
//        }
//    }
//    fun deleteAddress(productId: String?) {
//        productId?:throw CustomError(customMessage = "System error")
//        viewModelScope.launch {
//            addressUseCase.deleteAddress(productId).flowOn(Dispatchers.IO).toLoadState().collect {
//                when(it){
//                    LoadState.Loading -> {
//                    }
//                    is LoadState.Loaded -> {
//                        //remove the product from cart in local memory
//                        val deletedProductIdx = addresses.value?.indexOfFirst { it._id == productId }
//                        deletedProductIdx?.let {addresses.value?.removeAt(deletedProductIdx)}
//
//                        Log.d("PRODUCT", addresses.value.toString())
//                        deleteAddressStatus.value = true
//                    }
//                    is LoadState.Error -> {
//                        deleteAddressStatus.value = false
//                        error.value = errorMessage(it.e)
//
//                    }
//                }
//            }
//        }
//    }
//
//    fun editAddress(productId: String?, currQuantity: Int) {
//        productId?:throw CustomError(customMessage = "System error")
//        viewModelScope.launch {
//            addressUseCase.adjustQuantityOfProductInCart(productId, currQuantity).flowOn(Dispatchers.IO).toLoadState().collect {
//                when(it){
//                    LoadState.Loading -> {
//                    }
//                    is LoadState.Loaded -> {
//                        Log.d("PRODUCT", addresses.value.toString())
//                        editAddressStatus.value = true
//                    }
//                    is LoadState.Error -> {
//                        editAddressStatus.value = false
//                        error.value = errorMessage(it.e)
//
//                    }
//                }
//            }
//        }
//    }
    fun getAddresses(isLoadingEnabled: Boolean = true){
        viewModelScope.launch {
            addressUseCase.getAddresses().flowOn(Dispatchers.IO).toLoadState().collect {
                when(it){
                    LoadState.Loading -> {
                        if(isLoadingEnabled) loading.value = true
                    }
                    is LoadState.Loaded -> {
                        loading.value = false
                        addresses.value = it.data
                        Log.d("getAddresses", addresses.value.toString())
                    }
                    is LoadState.Error -> {
                        loading.value = false
                        error.value = errorMessage(it.e)
                        Log.d("getAddresses: error", it.e.message.toString())

                    }
                }
            }
        }
    }
}
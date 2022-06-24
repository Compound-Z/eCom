package vn.ztech.software.ecom.ui.order

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import vn.ztech.software.ecom.api.request.GetShippingOptionsReq
import vn.ztech.software.ecom.api.response.CartProductResponse
import vn.ztech.software.ecom.api.response.GetShippingOptionsRes
import vn.ztech.software.ecom.common.LoadState
import vn.ztech.software.ecom.common.extension.toLoadState
import vn.ztech.software.ecom.model.*
import vn.ztech.software.ecom.util.CustomError
import vn.ztech.software.ecom.util.errorMessage

private const val TAG = "OrderViewModel"
class OrderViewModel(val shippingUseCase: IShippingUserCase): ViewModel() {

    val loading = MutableLiveData<Boolean>()
    val addAddressStatus = MutableLiveData<Boolean>()
    val updateAddressStatus = MutableLiveData<Boolean>()
    val error = MutableLiveData<CustomError>()
    val products = MutableLiveData<MutableList<CartProductResponse>>()
    val currentSelectedAddress = MutableLiveData<AddressItem>()
    val shippingOptions = MutableLiveData<List<GetShippingOptionsRes>>()
    val loadingShipping = MutableLiveData<Boolean>()

    fun getShippingOptions(getShippingOptionReq: GetShippingOptionsReq, isLoadingEnabled: Boolean = true){
        viewModelScope.launch {
            shippingUseCase.getShippingOptions(getShippingOptionReq).flowOn(Dispatchers.IO).toLoadState().collect {
                when(it){
                    LoadState.Loading -> {
                        if(isLoadingEnabled) loadingShipping.value = true
                    }
                    is LoadState.Loaded -> {
                        loadingShipping.value = false
                        shippingOptions.value = it.data
                        Log.d("getShippingOptions", shippingOptions.value.toString())
                    }
                    is LoadState.Error -> {
                        loadingShipping.value = false
                        error.value = errorMessage(it.e)
                        Log.d("getShippingOptions: error", it.e.message.toString())

                    }
                }
            }
        }
    }

    fun checkIfCanGetShippingOptions(): Boolean {
        Log.d(TAG, "${!products.value.isNullOrEmpty()} && ${currentSelectedAddress.value != null}")
        return !products.value.isNullOrEmpty() && currentSelectedAddress.value != null
    }
}
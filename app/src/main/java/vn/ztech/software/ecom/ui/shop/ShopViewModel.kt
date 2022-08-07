package vn.ztech.software.ecom.ui.shop

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import vn.ztech.software.ecom.common.LoadState
import vn.ztech.software.ecom.common.StoreDataStatus
import vn.ztech.software.ecom.common.extension.toLoadState
import vn.ztech.software.ecom.model.Product
import vn.ztech.software.ecom.model.Shop
import vn.ztech.software.ecom.util.CustomError
import vn.ztech.software.ecom.util.errorMessage

private const val TAG = "ShopViewModel"

class ShopViewModel(
    private val shopUseCase: IShopUseCase
): ViewModel() {
    val shop = MutableLiveData<Shop>()
    val shopId = MutableLiveData<String>()
    val loading = MutableLiveData<Boolean>()
    val error = MutableLiveData<CustomError>()


    fun getShopInfo(shopId: String?){
        if (shopId == null){
            error.value = errorMessage(CustomError(customMessage = "System error"))
            return
        }
        viewModelScope.launch {
            shopUseCase.getShopInfo(shopId).flowOn(Dispatchers.IO).toLoadState().collect {
                when(it){
                    LoadState.Loading -> {
                        loading.value = true
                    }
                    is LoadState.Loaded -> {
                        loading.value = false
                        shop.value = it.data
                        Log.d(TAG, "LOADED")
                    }
                    is LoadState.Error -> {
                        loading.value = false
                        error.value = errorMessage(it.e)
                        Log.d(TAG+" ERROR:", it.e.message.toString())
                    }
                }
            }
        }
    }

    fun clearErrors() {
        error.value = null
    }
}

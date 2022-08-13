package vn.ztech.software.ecom.ui.order.order_history

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import vn.ztech.software.ecom.api.request.UpdateOrderStatusBody
import vn.ztech.software.ecom.common.LoadState
import vn.ztech.software.ecom.common.extension.toLoadState
import vn.ztech.software.ecom.model.Order
import vn.ztech.software.ecom.ui.order.IOrderUserCase
import vn.ztech.software.ecom.util.CustomError
import vn.ztech.software.ecom.util.errorMessage

class ListOrdersViewModel(private val orderUseCase: IOrderUserCase): ViewModel() {
    val loading = MutableLiveData<Boolean>()
    val orders = MutableLiveData<List<Order>>()
    val error = MutableLiveData<CustomError>()
    val order = MutableLiveData<Order>()
    val statusFilter = MutableLiveData<String>()


    fun getOrders(statusFilter: String?, isLoadingEnabled: Boolean = true) {
        if(statusFilter==null) {
            error.value = errorMessage( CustomError(customMessage = "System error"))
            return
        }
        viewModelScope.launch {
            orderUseCase.getOrders(statusFilter).flowOn(Dispatchers.IO).toLoadState().collect {
                when (it) {
                    LoadState.Loading -> {
                        Log.d("ORDERS", isLoadingEnabled.toString())
                        if (isLoadingEnabled) loading.value = true
                        Log.d("ORDERS", loading.value.toString())

                    }
                    is LoadState.Loaded -> {
                        loading.value = false
                        orders.value = it.data
                    }
                    is LoadState.Error -> {
                        loading.value = false
                        error.value = errorMessage(it.e)
                    }
                }
            }
        }
    }

    fun receivedOrder(_id: String) {
        val targetStatus = "RECEIVED"
        updateOrderStatus(_id, targetStatus)
    }
    private fun updateOrderStatus(_orderId: String, targetStatus: String, isLoadingEnabled: Boolean = true){
        viewModelScope.launch {
            orderUseCase.updateOrderStatus(_orderId, UpdateOrderStatusBody(targetStatus)).flowOn(Dispatchers.IO).toLoadState().collect {
                when (it) {
                    LoadState.Loading -> {
                        if (isLoadingEnabled) loading.value = true
                    }
                    is LoadState.Loaded -> {
                        loading.value = false
                        order.value = it.data
                    }
                    is LoadState.Error -> {
                        loading.value = false
                        error.value = errorMessage(it.e)
                    }
                }
            }
        }
    }
    fun clearErrors() {
        error.value = null
    }
}
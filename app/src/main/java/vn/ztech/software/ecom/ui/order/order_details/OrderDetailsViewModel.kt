package vn.ztech.software.ecom.ui.order.order_details

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
import vn.ztech.software.ecom.model.OrderDetails
import vn.ztech.software.ecom.ui.order.IOrderUserCase
import vn.ztech.software.ecom.util.CustomError
import vn.ztech.software.ecom.util.errorMessage

class OrderDetailsViewModel(private val orderUseCase: IOrderUserCase): ViewModel() {
    val loading = MutableLiveData<Boolean>()
    val orderDetails = MutableLiveData<OrderDetails>()
    val error = MutableLiveData<CustomError>()
    val cancelOrderStatus = MutableLiveData<Boolean>()
    fun getOrderDetails(orderId: String?){
        if (orderId == null){
            errorMessage(CustomError(customMessage = "System error: the order is missing"))
        }else{
            viewModelScope.launch {
                orderUseCase.getOrderDetails(orderId).flowOn(Dispatchers.IO).toLoadState().collect {
                    when(it){
                        LoadState.Loading -> {
                            loading.value = true
                        }
                        is LoadState.Loaded -> {
                            loading.value = false
                            orderDetails.value = it.data
                            Log.d("cancelOrder", orderDetails.value.toString())
                        }
                        is LoadState.Error -> {
                            loading.value = false
                            error.value = errorMessage(it.e)
                            Log.d("cancelOrder: error", it.e.message.toString())

                        }
                    }
                }
            }
        }
    }
    fun cancelOrder(orderId: String?){
        if (orderId == null){
            errorMessage(CustomError(customMessage = "System error: the order is missing"))
        }else{
            viewModelScope.launch {
                orderUseCase.cancelOrder(orderId).flowOn(Dispatchers.IO).toLoadState().collect {
                    when(it){
                        LoadState.Loading -> {
                            loading.value = true
                        }
                        is LoadState.Loaded -> {
                            loading.value = false
                            orderDetails.value = it.data
                            cancelOrderStatus.value = true
                            Log.d("cancelOrder", orderDetails.value.toString())
                        }
                        is LoadState.Error -> {
                            loading.value = false
                            cancelOrderStatus.value = false
                            error.value = errorMessage(it.e)
                            Log.d("cancelOrder: error", it.e.message.toString())

                        }
                    }
                }
            }
        }
    }
    fun clearErrors() {
        error.value = null
    }

    fun receivedOrder(_id: String?) {
        if (_id == null) {
            error.value = errorMessage(CustomError(customMessage = "System error, order is empty, try again later!"))
            return
        }

        val targetStatus = "RECEIVED"
        updateOrderStatus(_id, targetStatus)
    }
    private fun updateOrderStatus(_orderId: String, targetStatus: String, isLoadingEnabled: Boolean = true){
        viewModelScope.launch {
            orderUseCase.updateOrderStatusDetails(_orderId, UpdateOrderStatusBody(targetStatus)).flowOn(Dispatchers.IO).toLoadState().collect {
                when (it) {
                    LoadState.Loading -> {
                        if (isLoadingEnabled) loading.value = true
                    }
                    is LoadState.Loaded -> {
                        loading.value = false
                        orderDetails.value = it.data
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
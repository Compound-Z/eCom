package vn.ztech.software.ecom.ui.order.order_history

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
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


    fun getOrders(statusFilter: String?, isLoadingEnabled: Boolean = true) {
        statusFilter ?: throw CustomError(customMessage = "System error")
        viewModelScope.launch {
            orderUseCase.getOrders(statusFilter).flowOn(Dispatchers.IO).toLoadState().collect {
                when (it) {
                    LoadState.Loading -> {
                        if (isLoadingEnabled) loading.value = true
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
}
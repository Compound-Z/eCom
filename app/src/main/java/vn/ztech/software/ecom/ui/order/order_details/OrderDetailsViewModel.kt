package vn.ztech.software.ecom.ui.order.order_details

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import vn.ztech.software.ecom.model.OrderDetails
import vn.ztech.software.ecom.ui.order.IOrderUserCase
import vn.ztech.software.ecom.util.CustomError

class OrderDetailsViewModel(private val orderUseCase: IOrderUserCase): ViewModel() {
    val loading = MutableLiveData<Boolean>()
    val orderDetails = MutableLiveData<OrderDetails>()
    val error = MutableLiveData<CustomError>()

    fun getOrderDetails(orderId: String){
            //todo: impl
    }
}
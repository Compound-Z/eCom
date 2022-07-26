package vn.ztech.software.ecom.ui.order.order

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import vn.ztech.software.ecom.api.request.CreateOrderRequest
import vn.ztech.software.ecom.api.request.GetShippingOptionsReq
import vn.ztech.software.ecom.api.response.CartProductResponse
import vn.ztech.software.ecom.api.response.ShippingOption
import vn.ztech.software.ecom.common.LoadState
import vn.ztech.software.ecom.common.extension.toLoadState
import vn.ztech.software.ecom.model.*
import vn.ztech.software.ecom.ui.order.IOrderUserCase
import vn.ztech.software.ecom.util.CustomError
import vn.ztech.software.ecom.util.errorMessage
import vn.ztech.software.ecom.util.extension.findSelectedShippingOption
import vn.ztech.software.ecom.api.request.CartItem

private const val TAG = "OrderViewModel"
class OrderViewModel(private val shippingUseCase: IShippingUserCase, val orderUseCase: IOrderUserCase): ViewModel() {

    val loading = MutableLiveData<Boolean>()
    val error = MutableLiveData<CustomError>()
    val products = MutableLiveData<MutableList<CartProductResponse>>()
    val subOrders = MutableLiveData<List<SubOrder>>()
    val currentSelectedAddress = MutableLiveData<AddressItem>()
//    val shippingOptions = MutableLiveData<List<ShippingOption>>()
    val currentSelectedShippingOption = MutableLiveData<ShippingOption>()
    val loadingShipping = MutableLiveData<Boolean>()
    val loadShippingOptionsDone = MutableLiveData<Boolean>()
    val orderCost = MutableLiveData<OrderCost>()
    val createdOrder = MutableLiveData<OrderDetails>()

    fun getShippingOptions(shopId: String, getShippingOptionReq: GetShippingOptionsReq, isLoadingEnabled: Boolean = true){
        viewModelScope.launch {
            shippingUseCase.getShippingOptions(getShippingOptionReq).flowOn(Dispatchers.IO).toLoadState().collect {
                when(it){
                    LoadState.Loading -> {
                        if(isLoadingEnabled) loadingShipping.value = true
                    }
                    is LoadState.Loaded -> {
                        loadingShipping.value = false
                        setShippingOptionsToSubOrder(shopId, it.data)
                        loadShippingOptionsDone.value = true
//                        shippingOptions.value = it.data
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

    private fun setShippingOptionsToSubOrder(shopId: String, data: List<ShippingOption>) {


        subOrders.value?.forEach {
            if (it.shop._id == shopId){
                it.shippingOptions = data.distinctBy { it.service_id }/**remove duplicated shipping options*/
                return@forEach
            }
        }
    }


    fun checkIfCanGetShippingOptions(): Boolean {
        Log.d(TAG, "${!products.value.isNullOrEmpty()} && ${currentSelectedAddress.value != null}")
        return !products.value.isNullOrEmpty() && currentSelectedAddress.value != null
    }

    fun calculateCost(){
        var productsCost = 0
        var shippingFee = 0

        subOrders.value?.forEach {
            shippingFee += it.shippingOptions?.findSelectedShippingOption(it.shippingServiceId)?.fee?.total?:0
            productsCost += it.items.sumOf { it.price*it.quantity }
        }

        orderCost.value = OrderCost(productsCost, shippingFee, productsCost+shippingFee)
    }

    fun createOrder(products: MutableList<CartProductResponse>?, addressItem: AddressItem?, shippingOption: ShippingOption?) {
        if (products.isNullOrEmpty() || addressItem == null || !checkShippingOptions()){
            if(addressItem==null){
                error.value = errorMessage(CustomError(customMessage = "Please choose an address"))
            }else if(!checkShippingOptions()){
                error.value = errorMessage(CustomError(customMessage = "Please choose a shipping option"))
            }else{
                error.value = errorMessage(CustomError(customMessage = "Products is empty, go shopping please"))
            }
        }else{
            /**map shipping option to order items*/
            val listOrderItem = mutableListOf<CartItem>()
            subOrders.value?.forEach {subOrder->
                subOrder.items.forEach {
                    listOrderItem.add(vn.ztech.software.ecom.api.request.CartItem(it.productId, it.quantity, subOrder.shippingServiceId))
                }
            }
            val createOrderRequest = CreateOrderRequest(
                addressItemId = addressItem._id,
                orderItems = listOrderItem,
                note = "" //todo: implement ui for Note
            )
            viewModelScope.launch {
                orderUseCase.createOrder(createOrderRequest).flowOn(Dispatchers.IO).toLoadState().collect{
                    when(it){
                        LoadState.Loading -> {
                            loading.value = true
                        }
                        is LoadState.Loaded -> {
                            loading.value = false
                            createdOrder.value = it.data
                            Log.d("createOrder", createdOrder.value.toString())
                        }
                        is LoadState.Error -> {
                            loading.value = false
                            error.value = errorMessage(it.e)
                            Log.d("createOrder: error", it.e.message.toString())
                        }
                    }
                }
            }
        }
    }

    private fun checkShippingOptions(): Boolean {
        Log.d("SubOrders",  subOrders.value.toString())
        subOrders.value?.forEach {
            Log.d("SubOrders", it.shippingServiceId.toString())
            if (it.shippingServiceId==-1) return false
        }
        return true
    }


    data class OrderCost(
        var productsCost: Int = -1,
        var shippingFee: Int = -1,
        var totalCost: Int = -1,
    )
    fun clearErrors() {
        error.value = null
    }

    fun setSelectedShippingOptionToSubOrder(shopId: String, serviceId: Int) {
        subOrders.value?.forEach {
            if (it.shop._id == shopId){
                it.shippingServiceId = serviceId
                return@forEach
            }
        }
    }
}
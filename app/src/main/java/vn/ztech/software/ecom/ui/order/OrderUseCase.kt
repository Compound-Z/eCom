package vn.ztech.software.ecom.ui.order

import androidx.room.FtsOptions
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import vn.ztech.software.ecom.api.request.CreateOrderRequest
import vn.ztech.software.ecom.model.Order
import vn.ztech.software.ecom.model.OrderDetails
import vn.ztech.software.ecom.repository.IOrderRepository

interface IOrderUserCase{
    suspend fun createOrder(createOrderRequest: CreateOrderRequest): Flow<OrderDetails>
}

class OrderUseCase(private val orderRepository: IOrderRepository): IOrderUserCase{

    override suspend fun createOrder(createOrderRequest: CreateOrderRequest): Flow<OrderDetails> = flow {
        val order = orderRepository.createOrder(createOrderRequest)
        emit(order)
    }
}
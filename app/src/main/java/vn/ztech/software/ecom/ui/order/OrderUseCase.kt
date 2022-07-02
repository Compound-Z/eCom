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
    suspend fun cancelOrder(orderId: String): Flow<OrderDetails>
    suspend fun getOrders(statusFilter: String): Flow<List<Order>>
    suspend fun getOrderDetails(orderId: String): Flow<OrderDetails>

}

class OrderUseCase(private val orderRepository: IOrderRepository): IOrderUserCase{

    override suspend fun createOrder(createOrderRequest: CreateOrderRequest): Flow<OrderDetails> = flow {
        val order = orderRepository.createOrder(createOrderRequest)
        emit(order)
    }

    override suspend fun cancelOrder(orderId: String): Flow<OrderDetails> = flow {
        val orderDetails = orderRepository.cancelOrder(orderId)
        emit(orderDetails)
    }

    override suspend fun getOrders(statusFilter: String): Flow<List<Order>> = flow {
        val orders = orderRepository.getOrders(statusFilter)
        val orders2 = orders.sortedByDescending{ it.updatedAt }
        emit(orders2)
    }

    override suspend fun getOrderDetails(orderId: String): Flow<OrderDetails> = flow {
        val orderDetails = orderRepository.getOrderDetails(orderId)
        emit(orderDetails)
    }
}
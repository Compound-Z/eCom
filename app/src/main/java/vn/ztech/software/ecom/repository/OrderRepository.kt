package vn.ztech.software.ecom.repository

import vn.ztech.software.ecom.api.IOrderApi
import vn.ztech.software.ecom.api.request.CreateOrderRequest
import vn.ztech.software.ecom.api.request.UpdateOrderStatusBody
import vn.ztech.software.ecom.api.response.GetOrdersRequest
import vn.ztech.software.ecom.model.Order
import vn.ztech.software.ecom.model.OrderDetails

interface IOrderRepository{
    suspend fun createOrder(createOrderRequest: CreateOrderRequest): OrderDetails
    suspend fun cancelOrder(orderId: String): OrderDetails
    suspend fun getOrders(statusFilter: String): List<Order>
    suspend fun getOrderDetails(orderId: String): OrderDetails
    suspend fun updateOrderStatus(orderId: String, updateOrderStatusBody: UpdateOrderStatusBody): Order
    suspend fun updateOrderStatusDetails(orderId: String, updateOrderStatusBody: UpdateOrderStatusBody): OrderDetails

}

class OrderRepository(private val orderApi: IOrderApi): IOrderRepository{
    override suspend fun createOrder(createOrderRequest: CreateOrderRequest): OrderDetails {
        return orderApi.createOrder(createOrderRequest)
    }
    override suspend fun cancelOrder(orderId: String): OrderDetails {
        return orderApi.cancelOrder(orderId)
    }

    override suspend fun getOrders(statusFilter: String): List<Order> {
        return orderApi.getOrders(GetOrdersRequest(statusFilter))
    }

    override suspend fun getOrderDetails(orderId: String): OrderDetails {
        return orderApi.getOrderDetails(orderId)
    }
    override suspend fun updateOrderStatus(_id: String, updateOrderStatusBody: UpdateOrderStatusBody): Order {
        return orderApi.updateOrderStatus(_id, updateOrderStatusBody)
    }
    override suspend fun updateOrderStatusDetails(_id: String, updateOrderStatusBody: UpdateOrderStatusBody): OrderDetails {
        return orderApi.updateOrderStatusDetails(_id, updateOrderStatusBody)
    }
}
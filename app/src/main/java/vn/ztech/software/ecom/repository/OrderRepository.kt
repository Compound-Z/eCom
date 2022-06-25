package vn.ztech.software.ecom.repository

import vn.ztech.software.ecom.api.IOrderApi
import vn.ztech.software.ecom.api.request.CreateOrderRequest
import vn.ztech.software.ecom.model.OrderDetails

interface IOrderRepository{
    suspend fun createOrder(createOrderRequest: CreateOrderRequest): OrderDetails
    suspend fun cancelOrder(orderId: String): OrderDetails

}

class OrderRepository(private val orderApi: IOrderApi): IOrderRepository{
    override suspend fun createOrder(createOrderRequest: CreateOrderRequest): OrderDetails {
        return orderApi.createOrder(createOrderRequest)
    }
    override suspend fun cancelOrder(orderId: String): OrderDetails {
        return orderApi.cancelOrder(orderId)
    }
}
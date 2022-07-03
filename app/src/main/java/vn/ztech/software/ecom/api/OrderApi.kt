package vn.ztech.software.ecom.api

import androidx.annotation.Keep
import retrofit2.http.*
import vn.ztech.software.ecom.api.request.CreateOrderRequest
import vn.ztech.software.ecom.api.request.UpdateOrderStatusBody
import vn.ztech.software.ecom.api.response.GetOrdersRequest
import vn.ztech.software.ecom.model.Order
import vn.ztech.software.ecom.model.OrderDetails

@Keep
interface IOrderApi{
    @POST("/api/v1/orders")
    suspend fun createOrder(@Body request: CreateOrderRequest): OrderDetails
    @DELETE("/api/v1/orders/{orderId}")
    suspend fun cancelOrder(@Path("orderId") orderId: String): OrderDetails
    @POST("/api/v1/orders/my-orders")
    suspend fun getOrders(@Body request: GetOrdersRequest): List<Order>
    @GET("/api/v1/orders/{orderId}")
    suspend fun getOrderDetails(@Path("orderId") orderId: String): OrderDetails
    @PATCH("/api/v1/orders/{orderId}")
    suspend fun updateOrderStatus(@Path("orderId")_id: String, @Body request: UpdateOrderStatusBody): Order
    @PATCH("/api/v1/orders/{orderId}")
    suspend fun updateOrderStatusDetails(@Path("orderId")_id: String, @Body request: UpdateOrderStatusBody): OrderDetails
}
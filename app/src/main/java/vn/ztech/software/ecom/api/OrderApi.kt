package vn.ztech.software.ecom.api

import androidx.annotation.Keep
import retrofit2.http.Body
import retrofit2.http.POST
import vn.ztech.software.ecom.api.request.CreateOrderRequest
import vn.ztech.software.ecom.model.OrderDetails

@Keep
interface IOrderApi{
    @POST("/api/v1/orders")
    suspend fun createOrder(@Body request: CreateOrderRequest): OrderDetails
}
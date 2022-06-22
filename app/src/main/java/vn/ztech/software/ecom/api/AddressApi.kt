package vn.ztech.software.ecom.api

import androidx.annotation.Keep
import retrofit2.http.*
import vn.ztech.software.ecom.api.request.AddProductToCartRequest
import vn.ztech.software.ecom.api.request.AdjustProductQuantityRequest
import vn.ztech.software.ecom.api.response.BasicResponse
import vn.ztech.software.ecom.model.Address

@Keep
interface IAddressApi{
    @GET("/api/v1/addresses")
    suspend fun getAddresses(): Address

//    @POST("/api/v1/addresses")
//    suspend fun addProductToCart(@Body request: AddProductToCartRequest): BasicResponse
//
//    @PATCH("/api/v1/addresses/{productId}")
//    suspend fun adjustProductQuantityInCart(@Path("productId")productId: String, @Body request: AdjustProductQuantityRequest): BasicResponse
//
//    @DELETE("/api/v1/addresses/{productId}")
//    suspend fun deleteProductFromCart(@Path("productId") productId: String): BasicResponse
}
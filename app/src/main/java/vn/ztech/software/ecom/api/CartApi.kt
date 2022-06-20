package vn.ztech.software.ecom.api

import androidx.annotation.Keep
import retrofit2.http.*
import vn.ztech.software.ecom.api.request.AddProductToCartRequest
import vn.ztech.software.ecom.api.request.AdjustProductQuantityRequest
import vn.ztech.software.ecom.api.response.BasicResponse
import vn.ztech.software.ecom.model.Product

@Keep
interface ICartApi{
    @GET("/api/v1/cart")
    suspend fun getListProductsInCart(): List<Product>

    @POST("/api/v1/cart")
    suspend fun addProductToCart(@Body request: AddProductToCartRequest): BasicResponse

    @PATCH("/api/v1/cart/{productId}")
    suspend fun adjustProductQuantityInCart(@Path("productId")productId: String, @Body request: AdjustProductQuantityRequest): BasicResponse

    @DELETE("/api/v1/cart/{productId}")
    suspend fun deleteProductFromCart(@Path("productId") productId: String): BasicResponse
}
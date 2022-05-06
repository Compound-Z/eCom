package vn.ztech.software.ecom.data.remote.api

import androidx.annotation.Keep
import retrofit2.http.GET
import vn.ztech.software.ecom.data.remote.dto.ProductDetailsResponse
import vn.ztech.software.ecom.data.remote.dto.ProductResponse

@Keep
interface IProductApi{
    @GET("api/v1/product/list")
    suspend fun getListProducts(): List<ProductResponse>

    @GET("api/v1/product/{productId}")
    suspend fun getProductDetails(productId: String): ProductDetailsResponse
}



package vn.ztech.software.ecom.data.remote.api

import androidx.annotation.Keep
import retrofit2.http.GET
import retrofit2.http.Path
import vn.ztech.software.ecom.data.remote.dto.ProductDetailsResponse
import vn.ztech.software.ecom.data.remote.dto.ProductResponse

@Keep
interface IProductApi{
    @GET("api/v1/products")
    suspend fun getListProducts(): List<ProductResponse>

    @GET("api/v1/productDetails/{productId}")
    suspend fun getProductDetails(@Path("productId")productId: String): ProductDetailsResponse
}



package vn.ztech.software.ecom.data.remote.api

import androidx.annotation.Keep
import retrofit2.http.GET
import vn.ztech.software.ecom.data.remote.dto.ProductResponse

@Keep
interface ICartApi{
    @GET("api/v1/listProductsInCart")
    suspend fun getListProductsInCart(): List<ProductResponse>
}
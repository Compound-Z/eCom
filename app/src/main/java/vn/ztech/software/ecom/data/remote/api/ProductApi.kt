package vn.ztech.software.ecom.data.remote.api

import androidx.annotation.Keep
import retrofit2.http.GET
import retrofit2.http.Path
import vn.ztech.software.ecom.domain.model.Product
import vn.ztech.software.ecom.domain.model.ProductDetails

@Keep
interface IProductApi{
    @GET("/api/v1/products")
    suspend fun getListProducts(): List<Product>

    @GET("/api/v1/products/{id}")
    suspend fun getProductDetails(@Path("id")id: String): ProductDetails
}



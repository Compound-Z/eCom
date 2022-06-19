package vn.ztech.software.ecom.api

import androidx.annotation.Keep
import retrofit2.http.GET
import retrofit2.http.Path
import vn.ztech.software.ecom.model.Product
import vn.ztech.software.ecom.model.ProductDetails

@Keep
interface IProductApi{
    @GET("/api/v1/products")
    suspend fun getListProducts(): List<Product>

    @GET("/api/v1/products/{id}")
    suspend fun getProductDetails(@Path("id")id: String): ProductDetails

    @GET("/api/v1/products/search/{searchWords}")
    suspend fun search(@Path("searchWords")searchWords: String): List<Product>

}



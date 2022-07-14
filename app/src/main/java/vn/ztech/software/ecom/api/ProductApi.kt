package vn.ztech.software.ecom.api

import androidx.annotation.Keep
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import vn.ztech.software.ecom.api.request.GetProductsRequest
import vn.ztech.software.ecom.api.response.GetOrdersRequest
import vn.ztech.software.ecom.api.response.PagedGetAllProductsResponse
import vn.ztech.software.ecom.model.Product
import vn.ztech.software.ecom.model.ProductDetails

@Keep
interface IProductApi{
    @POST("/api/v1/products/all")
    suspend fun getListProducts(@Body request: GetProductsRequest): PagedGetAllProductsResponse

    @GET("/api/v1/products/{id}")
    suspend fun getProductDetails(@Path("id")id: String): ProductDetails

    @POST("/api/v1/products/search/{searchWords}")
    suspend fun search(@Path("searchWords")searchWords: String, @Body request: GetProductsRequest): PagedGetAllProductsResponse

    @GET("/api/v1/products/get-one-product/{productId}")
    suspend fun getOneProduct(@Path("productId") productId: String): Product
}



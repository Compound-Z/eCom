package vn.ztech.software.ecom.api

import androidx.annotation.Keep
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import vn.ztech.software.ecom.api.request.GetProductsInCategoryRequest
import vn.ztech.software.ecom.api.request.SearchProductInCategoryRequest
import vn.ztech.software.ecom.api.response.PagedGetAllProductsResponse
import vn.ztech.software.ecom.model.Category
import vn.ztech.software.ecom.model.Product

@Keep
interface ICategoryApi{
    @GET("/api/v1/categories")
    suspend fun getListCategories(): List<Category>

    @POST("/api/v1/categories/{category}")
    suspend fun getListProductsInCategory(@Path("category")category: String, @Body getProductsInCategoryRequest: GetProductsInCategoryRequest): PagedGetAllProductsResponse

    @POST("/api/v1/categories/search/{category_name}")
    suspend fun search(@Path("category_name")searchWordsCategory: String, @Body request: SearchProductInCategoryRequest): PagedGetAllProductsResponse

}
package vn.ztech.software.ecom.api

import androidx.annotation.Keep
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Path
import vn.ztech.software.ecom.api.request.SearchProductInCategoryRequest
import vn.ztech.software.ecom.model.Category
import vn.ztech.software.ecom.model.Product

@Keep
interface ICategoryApi{
    @GET("/api/v1/categories")
    suspend fun getListCategories(): List<Category>

    @GET("/api/v1/categories/search/{searchWordsCategory}")
    suspend fun search(@Path("searchWordsCategory")searchWordsCategory: String, @Body request: SearchProductInCategoryRequest): List<Product>
}
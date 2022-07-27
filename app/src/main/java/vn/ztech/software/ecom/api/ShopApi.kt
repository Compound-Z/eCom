package vn.ztech.software.ecom.api

import androidx.annotation.Keep
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import vn.ztech.software.ecom.api.request.GetProductsInCategoryRequest
import vn.ztech.software.ecom.api.request.GetProductsOfCategoryInShopRequest
import vn.ztech.software.ecom.api.request.GetProductsRequest
import vn.ztech.software.ecom.api.request.SearchProductsInShopRequest
import vn.ztech.software.ecom.api.response.PagedGetAllProductsResponse
import vn.ztech.software.ecom.model.Category
import vn.ztech.software.ecom.model.Shop

@Keep
interface IShopApi{
    @GET("/api/v1/shops/{shop_id}")
    suspend fun getShopInfo(@Path("shop_id") shopId: String): Shop

    @POST("/api/v1/shops/{shop_id}/products") //todo:paginate
    suspend fun getProductsInShop(@Path("shop_id") shopId: String, @Body request: GetProductsRequest): PagedGetAllProductsResponse

    @GET("/api/v1/shops/{shop_id}/categories")
    suspend fun getCategoriesInShop(@Path("shop_id") shopId: String): List<Category>

    @POST("/api/v1/shops/{shop_id}/cate-products")
    suspend fun getProductsOfCategoryInShop(@Path("shop_id") shopId: String, @Body request: GetProductsOfCategoryInShopRequest ): PagedGetAllProductsResponse

    @POST("/api/v1/shops/search/{search_words}")
    suspend fun searchProductsInShop(@Path("search_words") searchWords: String, @Body request: SearchProductsInShopRequest ): PagedGetAllProductsResponse
}
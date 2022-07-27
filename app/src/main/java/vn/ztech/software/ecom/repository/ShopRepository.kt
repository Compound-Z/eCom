package vn.ztech.software.ecom.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import vn.ztech.software.ecom.api.IShopApi
import vn.ztech.software.ecom.api.request.GetProductsRequest
import vn.ztech.software.ecom.common.Constants
import vn.ztech.software.ecom.model.Product
import vn.ztech.software.ecom.model.ProductDetails
import vn.ztech.software.ecom.model.Shop

interface IShopRepository {
    suspend fun getShopInfo(shopId: String): Shop
//    suspend fun getListProducts(): Flow<PagingData<Product>>
//    suspend fun getProductDetails(productId: String): ProductDetails
//    suspend fun search(searchWords: String): Flow<PagingData<Product>>
//    suspend fun getOneProduct(productId: String): Product

}

class ShopRepository(private val shopApi: IShopApi): IShopRepository{
    //    override suspend fun getListProducts(): Flow<PagingData<Product>> {
//        return Pager(
//            config = PagingConfig(
//                pageSize = Constants.NETWORK_PAGE_SIZE,
//                enablePlaceholders = false,
//            ),
//            pagingSourceFactory = {
//                ProductPagingSource(GetProductsRequest(),shopApi)
//            },
//            initialKey = 1
//        ).flow
//    }
//
//    override suspend fun getProductDetails(productId: String): ProductDetails {
//        return shopApi.getProductDetails(productId)
//    }
//
//    override suspend fun search(searchWords: String): Flow<PagingData<Product>> {
//        return Pager(
//            config = PagingConfig(
//                pageSize = Constants.NETWORK_PAGE_SIZE,
//                enablePlaceholders = false,
//            ),
//            pagingSourceFactory = {
//                SearchProductPagingSource(searchWords, GetProductsRequest(), shopApi)
//            },
//            initialKey = 1
//        ).flow
//    }
//    override suspend fun getOneProduct(productId: String): Product {
//        return shopApi.getOneProduct(productId)
//    }
    override suspend fun getShopInfo(shopId: String): Shop {
        return shopApi.getShopInfo(shopId)
    }
}
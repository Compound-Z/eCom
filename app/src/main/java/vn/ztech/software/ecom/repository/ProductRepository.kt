package vn.ztech.software.ecom.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import vn.ztech.software.ecom.api.IProductApi
import vn.ztech.software.ecom.api.request.GetProductsRequest
import vn.ztech.software.ecom.common.Constants
import vn.ztech.software.ecom.model.Product
import vn.ztech.software.ecom.model.ProductDetails

interface IProductRepository {
    suspend fun getListProducts(): Flow<PagingData<Product>>
    suspend fun getProductDetails(productId: String): ProductDetails
    suspend fun search(searchWords: String): Flow<PagingData<Product>>
}

class ProductRepository(private val productApi: IProductApi): IProductRepository{
    override suspend fun getListProducts(): Flow<PagingData<Product>> {
        return Pager(
            config = PagingConfig(
                pageSize = Constants.NETWORK_PAGE_SIZE,
                enablePlaceholders = false,
            ),
            pagingSourceFactory = {
                ProductPagingSource(GetProductsRequest(),productApi)
            },
            initialKey = 1
        ).flow
    }

    override suspend fun getProductDetails(productId: String): ProductDetails {
        return productApi.getProductDetails(productId)
    }

    override suspend fun search(searchWords: String): Flow<PagingData<Product>> {
        return Pager(
            config = PagingConfig(
                pageSize = Constants.NETWORK_PAGE_SIZE,
                enablePlaceholders = false,
            ),
            pagingSourceFactory = {
                SearchProductPagingSource(searchWords, GetProductsRequest(), productApi)
            },
            initialKey = 1
        ).flow
    }

}
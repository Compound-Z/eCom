package vn.ztech.software.ecom.repository

import ProductInCategoryPagingSource
import android.util.Log
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import vn.ztech.software.ecom.api.ICategoryApi
import vn.ztech.software.ecom.api.request.GetProductsInCategoryRequest
import vn.ztech.software.ecom.api.request.SearchProductInCategoryRequest
import vn.ztech.software.ecom.common.Constants
import vn.ztech.software.ecom.model.Category
import vn.ztech.software.ecom.model.Product

interface ICategoryRepository {
    suspend fun getListCategories(): List<Category>
    suspend fun getListProductsInCategory(category: String): Flow<PagingData<Product>>
    suspend fun search(searchWords: String, searchWordsProduct: String): Flow<PagingData<Product>>
}

class CategoryRepository(private val CategoryApi: ICategoryApi): ICategoryRepository{
    override suspend fun getListCategories(): List<Category> {
        return CategoryApi.getListCategories()
    }

    override suspend fun getListProductsInCategory(category: String):  Flow<PagingData<Product>> {
        return Pager(
            config = PagingConfig(
                pageSize = Constants.NETWORK_PAGE_SIZE,
                enablePlaceholders = false,
            ),
            pagingSourceFactory = {
                ProductInCategoryPagingSource(category, GetProductsInCategoryRequest(), CategoryApi)
            },
            initialKey = 1
        ).flow
    }

    override suspend fun search(searchWordsCategory: String, searchWordsProduct: String): Flow<PagingData<Product>> {
        Log.d("x","search ${searchWordsCategory}")
        return Pager(
            config = PagingConfig(
                pageSize = Constants.NETWORK_PAGE_SIZE,
                enablePlaceholders = false,
            ),
            pagingSourceFactory = {
                SearchProductInCategoryPagingSource(searchWordsCategory, SearchProductInCategoryRequest(searchWordsProduct), CategoryApi)
            },
            initialKey = 1
        ).flow
    }
}
package vn.ztech.software.ecom.repository

import vn.ztech.software.ecom.api.ICategoryApi
import vn.ztech.software.ecom.api.request.SearchProductInCategoryRequest
import vn.ztech.software.ecom.model.Category
import vn.ztech.software.ecom.model.Product

interface ICategoryRepository {
    suspend fun getListCategories(): List<Category>
    suspend fun search(searchWords: String, searchWordsProduct: String): List<Product>
}

class CategoryRepository(private val CategoryApi: ICategoryApi): ICategoryRepository{
    override suspend fun getListCategories(): List<Category> {
        return CategoryApi.getListCategories()
    }

    override suspend fun search(searchWordsCategory: String, searchWordsProduct: String): List<Product> {
        return CategoryApi.search(searchWordsCategory, SearchProductInCategoryRequest(searchWordsProduct))
    }

}
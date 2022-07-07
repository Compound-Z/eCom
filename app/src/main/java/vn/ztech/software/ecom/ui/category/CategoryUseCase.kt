package vn.ztech.software.ecom.ui.category

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import vn.ztech.software.ecom.model.Category
import vn.ztech.software.ecom.model.Product
import vn.ztech.software.ecom.repository.ICategoryRepository

interface IListCategoriesUseCase{
    suspend fun getListCategories(): Flow<List<Category>>
    suspend fun getListProductsInCategory(category: String): Flow<PagingData<Product>>
    suspend fun search(searchWordsCategory: String, searchWordsProduct: String): Flow<PagingData<Product>>
}

class ListCategoriesUseCase(private val categoryRepository: ICategoryRepository): IListCategoriesUseCase{
    override suspend fun getListCategories(): Flow<List<Category>> = flow{
        val listCategories = categoryRepository.getListCategories()
        emit(listCategories)
    }
    override suspend fun getListProductsInCategory(category: String): Flow<PagingData<Product>> {
        return categoryRepository.getListProductsInCategory(category)
    }
    override suspend fun search(searchWordsCategory: String, searchWordsProduct: String): Flow<PagingData<Product>> {
        return categoryRepository.search(searchWordsCategory, searchWordsProduct)
    }
}
package vn.ztech.software.ecom.ui.category

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import vn.ztech.software.ecom.model.Category
import vn.ztech.software.ecom.model.Product
import vn.ztech.software.ecom.repository.ICategoryRepository

interface IListCategoriesUseCase{
    suspend fun getListCategories(): Flow<List<Category>>
    suspend fun search(searchWordsCategory: String, searchWordsProduct: String): Flow<List<Product>>
}

class ListCategoriesUseCase(private val categoryRepository: ICategoryRepository): IListCategoriesUseCase{
    override suspend fun getListCategories(): Flow<List<Category>> = flow{
        val listCategories = categoryRepository.getListCategories()
        emit(listCategories)
    }

    override suspend fun search(searchWordsCategory: String, searchWordsProduct: String): Flow<List<Product>> = flow{
        emit(categoryRepository.search(searchWordsCategory, searchWordsProduct))
    }
}
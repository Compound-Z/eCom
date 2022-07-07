package vn.ztech.software.ecom.ui.home

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import vn.ztech.software.ecom.repository.IProductRepository
import vn.ztech.software.ecom.model.Product

interface IListProductUseCase{
    suspend fun getListProducts(): Flow<PagingData<Product>>
    suspend fun search(searchWords: String): Flow<PagingData<Product>>
}

class ListProductsUseCase(private val productRepository: IProductRepository): IListProductUseCase {
    override suspend fun getListProducts(): Flow<PagingData<Product>> {
        return productRepository.getListProducts()
    }

    override suspend fun search(searchWords: String): Flow<PagingData<Product>> {
        return productRepository.search(searchWords)
    }

}
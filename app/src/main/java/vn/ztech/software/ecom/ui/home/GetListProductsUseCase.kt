package vn.ztech.software.ecom.domain.use_case.get_list_product

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import vn.ztech.software.ecom.repository.IProductRepository
import vn.ztech.software.ecom.model.Product

interface IListProductUseCase{
    suspend fun getListProducts(): Flow<List<Product>>
}

class ListProductsUseCase(private val productRepository: IProductRepository): IListProductUseCase{
    override suspend fun getListProducts(): Flow<List<Product>> = flow{
        val listProducts = productRepository.getListProducts()
        emit(listProducts)
    }

}
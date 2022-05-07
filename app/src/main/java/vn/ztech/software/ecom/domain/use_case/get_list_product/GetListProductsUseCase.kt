package vn.ztech.software.ecom.domain.use_case.get_list_product

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import vn.ztech.software.ecom.common.LoadState
import vn.ztech.software.ecom.data.remote.dto.toProduct
import vn.ztech.software.ecom.data.repository.IProductRepository
import vn.ztech.software.ecom.data.repository.ProductRepository
import vn.ztech.software.ecom.domain.model.Product

interface IListProductUseCase{
    suspend fun getListProducts(): Flow<List<Product>>
}

class ListProductsUseCase(private val productRepository: IProductRepository): IListProductUseCase{
    override suspend fun getListProducts(): Flow<List<Product>> = flow{
        val listProducts = productRepository.getListProducts().map {
            it.toProduct()
        }
        emit(listProducts)
    }

}
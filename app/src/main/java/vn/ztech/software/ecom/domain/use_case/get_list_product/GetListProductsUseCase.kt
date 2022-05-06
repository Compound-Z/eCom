package vn.ztech.software.ecom.domain.use_case.get_list_product

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import vn.ztech.software.ecom.common.LoadState
import vn.ztech.software.ecom.data.remote.dto.toProduct
import vn.ztech.software.ecom.data.repository.IProductRepository
import vn.ztech.software.ecom.data.repository.ProductRepository
import vn.ztech.software.ecom.domain.model.Product

interface IGetListProductUseCase{
    suspend fun getListProductUseCase(): Flow<List<Product>>
}

class GetListProductsUseCase(private val productRepository: IProductRepository): IGetListProductUseCase{
    override suspend fun getListProductUseCase(): Flow<List<Product>> = flow{
        val listProducts = productRepository.getListProducts().map {
            it.toProduct()
        }
        emit(listProducts)
    }

}
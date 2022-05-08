package vn.ztech.software.ecom.domain.use_case.cart

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import vn.ztech.software.ecom.data.remote.dto.toProduct
import vn.ztech.software.ecom.data.repository.ICartRepository
import vn.ztech.software.ecom.domain.model.Product

interface ICartUseCase{
    suspend fun getListProducts(): Flow<List<Product>>
}

class CartUseCase(private val cartRepository: ICartRepository): ICartUseCase{
    override suspend fun getListProducts(): Flow<List<Product>> = flow{
        val listProducts = cartRepository.getListProductsInCart().map {
            it.toProduct()
        }
        emit(listProducts)
    }
}
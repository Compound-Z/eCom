package vn.ztech.software.ecom.ui.cart

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import vn.ztech.software.ecom.api.response.BasicResponse
import vn.ztech.software.ecom.model.Product
import vn.ztech.software.ecom.repository.ICartRepository

interface ICartUseCase{
    suspend fun getListProductsInCart(): Flow<List<Product>>
    suspend fun addProductToCart(productId: String): Flow<BasicResponse>
    suspend fun adjustQuantityOfProductInCart(productId: String, quantity: Int): Flow<BasicResponse>
    suspend fun deleteProductFromCart(productId: String): Flow<BasicResponse>
}

class CartUseCase(private val cartRepository: ICartRepository): ICartUseCase{
    override suspend fun getListProductsInCart(): Flow<List<Product>> = flow{
        val listProducts = cartRepository.getListProductsInCart()
        emit(listProducts)
    }

    override suspend fun addProductToCart(productId: String): Flow<BasicResponse> = flow {
        emit(cartRepository.addProductToCart(productId))
    }

    override suspend fun adjustQuantityOfProductInCart(
        productId: String,
        quantity: Int
    ): Flow<BasicResponse> = flow {
        emit(cartRepository.adjustQuantityOfProductInCart(productId, quantity))
    }

    override suspend fun deleteProductFromCart(productId: String): Flow<BasicResponse> = flow {
        emit(cartRepository.deleteProductFromCart(productId))
    }

}
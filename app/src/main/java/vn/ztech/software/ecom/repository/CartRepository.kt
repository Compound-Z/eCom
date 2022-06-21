package vn.ztech.software.ecom.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import vn.ztech.software.ecom.api.ICartApi
import vn.ztech.software.ecom.api.request.AddProductToCartRequest
import vn.ztech.software.ecom.api.request.AdjustProductQuantityRequest
import vn.ztech.software.ecom.api.response.BasicResponse
import vn.ztech.software.ecom.api.response.CartProductResponse
import vn.ztech.software.ecom.model.Product

interface ICartRepository{
    suspend fun getListProductsInCart(): List<CartProductResponse>
    suspend fun addProductToCart(productId: String): BasicResponse
    suspend fun adjustQuantityOfProductInCart(productId: String, quantity: Int): BasicResponse
    suspend fun deleteProductFromCart(productId: String): BasicResponse
}

class CartRepository(private val cartApi: ICartApi): ICartRepository{
    override suspend fun getListProductsInCart(): List<CartProductResponse> {
        return cartApi.getListProductsInCart()
    }

    override suspend fun addProductToCart(productId: String): BasicResponse {
        return cartApi.addProductToCart(AddProductToCartRequest(productId))
    }

    override suspend fun adjustQuantityOfProductInCart(
        productId: String,
        quantity: Int
    ): BasicResponse {
        return cartApi.adjustProductQuantityInCart(productId, AdjustProductQuantityRequest(quantity))
    }

    override suspend fun deleteProductFromCart(productId: String): BasicResponse{
        return cartApi.deleteProductFromCart(productId)
    }

}
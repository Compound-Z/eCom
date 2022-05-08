package vn.ztech.software.ecom.data.repository

import vn.ztech.software.ecom.data.remote.api.ICartApi
import vn.ztech.software.ecom.data.remote.dto.ProductResponse

interface ICartRepository {
    suspend fun getListProductsInCart(): List<ProductResponse>
}

class CartRepository(private val cartApi: ICartApi): ICartRepository{
    override suspend fun getListProductsInCart(): List<ProductResponse> {
        return cartApi.getListProductsInCart()
    }

}
package vn.ztech.software.ecom.repository

import vn.ztech.software.ecom.api.IAddressApi
import vn.ztech.software.ecom.api.request.AddProductToCartRequest
import vn.ztech.software.ecom.api.request.AdjustProductQuantityRequest
import vn.ztech.software.ecom.api.response.BasicResponse
import vn.ztech.software.ecom.model.Address

interface IAddressRepository{
    suspend fun getAddresses(): Address
//    suspend fun addAddress(productId: String): BasicResponse
//    suspend fun editAddress(productId: String, quantity: Int): BasicResponse
//    suspend fun deleteAddress(productId: String): BasicResponse
}

class AddressRepository(private val cartApi: IAddressApi): IAddressRepository{
    override suspend fun getAddresses(): Address {
        return cartApi.getAddresses()
    }

//    override suspend fun addAddress(productId: String): BasicResponse {
//        return cartApi.addAddress(AddProductToCartRequest(productId))
//    }
//
//    override suspend fun editAddress(
//        productId: String,
//        quantity: Int
//    ): BasicResponse {
//        return cartApi.adjustProductQuantityInCart(productId, AdjustProductQuantityRequest(quantity))
//    }
//
//    override suspend fun deleteAddress(productId: String): BasicResponse {
//        return cartApi.deleteAddress(productId)
//    }

}
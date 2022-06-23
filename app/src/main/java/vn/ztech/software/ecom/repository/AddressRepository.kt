package vn.ztech.software.ecom.repository

import vn.ztech.software.ecom.api.IAddressApi
import vn.ztech.software.ecom.api.request.AddProductToCartRequest
import vn.ztech.software.ecom.api.request.AdjustProductQuantityRequest
import vn.ztech.software.ecom.api.response.BasicResponse
import vn.ztech.software.ecom.model.Address
import vn.ztech.software.ecom.model.District
import vn.ztech.software.ecom.model.Province
import vn.ztech.software.ecom.model.Ward

interface IAddressRepository{
    suspend fun getAddresses(): Address
    suspend fun getProvinces(): List<Province>
    suspend fun getDistricts(provinceId: Int): List<District>
    suspend fun getWards(districtId: Int): List<Ward>

//    suspend fun addAddress(productId: String): BasicResponse
//    suspend fun editAddress(productId: String, quantity: Int): BasicResponse
//    suspend fun deleteAddress(productId: String): BasicResponse
}

class AddressRepository(private val cartApi: IAddressApi): IAddressRepository{
    override suspend fun getAddresses(): Address {
        return cartApi.getAddresses()
    }
    override suspend fun getProvinces(): List<Province> {
        return cartApi.getProvinces()
    }
    override suspend fun getDistricts(provinceId: Int): List<District> {
        return cartApi.getDistricts(provinceId)
    }
    override suspend fun getWards(districtId: Int): List<Ward> {
        return cartApi.getWards(districtId)
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
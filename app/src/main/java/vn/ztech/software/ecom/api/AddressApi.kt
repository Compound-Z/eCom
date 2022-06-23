package vn.ztech.software.ecom.api

import androidx.annotation.Keep
import retrofit2.http.*
import vn.ztech.software.ecom.api.request.AddProductToCartRequest
import vn.ztech.software.ecom.api.request.AdjustProductQuantityRequest
import vn.ztech.software.ecom.api.response.BasicResponse
import vn.ztech.software.ecom.model.Address
import vn.ztech.software.ecom.model.District
import vn.ztech.software.ecom.model.Province
import vn.ztech.software.ecom.model.Ward

@Keep
interface IAddressApi{
    @GET("/api/v1/addresses")
    suspend fun getAddresses(): Address
    @GET("/api/v1/addresses/provinces")
    suspend fun getProvinces(): List<Province>
    @GET("/api/v1/addresses/districts/{provinceId}")
    suspend fun getDistricts(@Path("provinceId") provinceId: Int): List<District>
    @GET("/api/v1/addresses/wards/{districtId}")
    suspend fun getWards(@Path("districtId") districtId: Int): List<Ward>
//    @POST("/api/v1/addresses")
//    suspend fun addProductToCart(@Body request: AddProductToCartRequest): BasicResponse
//
//    @PATCH("/api/v1/addresses/{productId}")
//    suspend fun adjustProductQuantityInCart(@Path("productId")productId: String, @Body request: AdjustProductQuantityRequest): BasicResponse
//
//    @DELETE("/api/v1/addresses/{productId}")
//    suspend fun deleteProductFromCart(@Path("productId") productId: String): BasicResponse
}
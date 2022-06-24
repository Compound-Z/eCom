package vn.ztech.software.ecom.api

import androidx.annotation.Keep
import retrofit2.http.*
import vn.ztech.software.ecom.api.request.AddAddressRequest
import vn.ztech.software.ecom.api.request.GetShippingOptionsReq
import vn.ztech.software.ecom.api.response.GetShippingOptionsRes
import vn.ztech.software.ecom.model.Address
import vn.ztech.software.ecom.model.District
import vn.ztech.software.ecom.model.Province
import vn.ztech.software.ecom.model.Ward

@Keep
interface IShippingApi{
    @POST("/api/v1/orders/shipping-fee")
    suspend fun getShippingOptions(@Body request: GetShippingOptionsReq): List<GetShippingOptionsRes>

}
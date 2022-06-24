package vn.ztech.software.ecom.repository

import vn.ztech.software.ecom.api.IShippingApi
import vn.ztech.software.ecom.api.request.AddAddressRequest
import vn.ztech.software.ecom.api.request.GetShippingOptionsReq
import vn.ztech.software.ecom.api.response.ShippingOption
import vn.ztech.software.ecom.model.Address
import vn.ztech.software.ecom.model.District
import vn.ztech.software.ecom.model.Province
import vn.ztech.software.ecom.model.Ward

interface IShippingRepository{
    suspend fun getShippingOptions(shippingOptionsReq: GetShippingOptionsReq): List<ShippingOption>
   
}

class ShippingRepository(private val shippingApi: IShippingApi): IShippingRepository{
    override suspend fun getShippingOptions(shippingOptionsReq: GetShippingOptionsReq): List<ShippingOption> {
        return shippingApi.getShippingOptions(shippingOptionsReq)
    }
   

}
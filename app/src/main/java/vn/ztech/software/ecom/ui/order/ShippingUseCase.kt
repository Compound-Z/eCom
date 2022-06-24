package vn.ztech.software.ecom.ui.order

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import vn.ztech.software.ecom.api.request.AddAddressRequest
import vn.ztech.software.ecom.api.request.GetShippingOptionsReq
import vn.ztech.software.ecom.api.response.ShippingOption
import vn.ztech.software.ecom.repository.IShippingRepository

interface IShippingUserCase{
    suspend fun getShippingOptions(getShippingOptionsReq: GetShippingOptionsReq): Flow<List<ShippingOption>>
}

class ShippingUseCase(private val shippingRepository: IShippingRepository): IShippingUserCase{

    override suspend fun getShippingOptions(getShippingOptionsReq: GetShippingOptionsReq): Flow<List<ShippingOption>> = flow {
        val shippingOptions = shippingRepository.getShippingOptions(getShippingOptionsReq).filter { it.name.isNotBlank() }.sortedBy { it.fee.total }
        emit(shippingOptions)
    }
}
package vn.ztech.software.ecom.ui.address

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import vn.ztech.software.ecom.api.response.BasicResponse
import vn.ztech.software.ecom.model.Address
import vn.ztech.software.ecom.repository.IAddressRepository

interface IAddressUseCase{
    suspend fun getAddresses(): Flow<Address>
//    suspend fun addAddress(productId: String): Flow<BasicResponse>
//    suspend fun editAddress(productId: String, quantity: Int): Flow<BasicResponse>
//    suspend fun deleteAddress(productId: String): Flow<BasicResponse>
}

class AddressUseCase(private val cartRepository: IAddressRepository): IAddressUseCase{
    override suspend fun getAddresses(): Flow<Address> = flow{
        val listProducts = cartRepository.getAddresses()
        emit(listProducts)
    }

//    override suspend fun addAddress(productId: String): Flow<BasicResponse> = flow {
//        emit(cartRepository.addAddress(productId))
//    }
//
//    override suspend fun editAddress(
//        productId: String,
//        quantity: Int
//    ): Flow<BasicResponse> = flow {
//        emit(cartRepository.editAddress(productId, quantity))
//    }
//
//    override suspend fun deleteAddress(productId: String): Flow<BasicResponse> = flow {
//        emit(cartRepository.deleteAddress(productId))
//    }

}
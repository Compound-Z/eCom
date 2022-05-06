package vn.ztech.software.ecom.domain.use_case.get_product_details

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import vn.ztech.software.ecom.data.remote.dto.toProductDetails
import vn.ztech.software.ecom.data.repository.ProductRepository
import vn.ztech.software.ecom.domain.model.ProductDetails

interface IGetProductDetailsUseCase{
    suspend fun getProductDetails(productId: String): Flow<ProductDetails>
}

class GetProductDetailsUseCase(private val productRepository: ProductRepository): IGetProductDetailsUseCase{
    override suspend fun getProductDetails(productId: String): Flow<ProductDetails> = flow {
        val productDetails = productRepository.getProductDetails(productId).toProductDetails()
        emit(productDetails)
    }
}
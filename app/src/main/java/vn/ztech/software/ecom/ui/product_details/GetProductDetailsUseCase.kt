package vn.ztech.software.ecom.domain.use_case.get_product_details

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import vn.ztech.software.ecom.repository.IProductRepository
import vn.ztech.software.ecom.model.ProductDetails

interface IProductDetailsUseCase{
    suspend fun getProductDetails(productId: String): Flow<ProductDetails>
}

class ProductDetailsUseCase(private val productRepository: IProductRepository): IProductDetailsUseCase{
    override suspend fun getProductDetails(productId: String): Flow<ProductDetails> = flow {
        val productDetails = productRepository.getProductDetails(productId)
        emit(productDetails)
    }
}
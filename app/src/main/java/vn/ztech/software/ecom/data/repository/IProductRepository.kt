package vn.ztech.software.ecom.data.repository

import vn.ztech.software.ecom.data.remote.api.IProductApi
import vn.ztech.software.ecom.data.remote.dto.ProductDetailsResponse
import vn.ztech.software.ecom.data.remote.dto.ProductResponse
import vn.ztech.software.ecom.domain.model.Product
import vn.ztech.software.ecom.domain.model.ProductDetails

interface IProductRepository {
    suspend fun getListProducts(): List<ProductResponse>
    suspend fun getProductDetails(productId: String): ProductDetailsResponse
}

class ProductRepository(private val productApi: IProductApi): IProductRepository{
    override suspend fun getListProducts(): List<ProductResponse> {
        return productApi.getListProducts()
    }

    override suspend fun getProductDetails(productId: String): ProductDetailsResponse {
        return productApi.getProductDetails(productId)
    }

}
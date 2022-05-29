package vn.ztech.software.ecom.data.repository

import vn.ztech.software.ecom.data.remote.api.IProductApi
import vn.ztech.software.ecom.domain.model.Product
import vn.ztech.software.ecom.domain.model.ProductDetails

interface IProductRepository {
    suspend fun getListProducts(): List<Product>
    suspend fun getProductDetails(productId: String): ProductDetails
}

class ProductRepository(private val productApi: IProductApi): IProductRepository{
    override suspend fun getListProducts(): List<Product> {
        return productApi.getListProducts()
    }

    override suspend fun getProductDetails(productId: String): ProductDetails {
        return productApi.getProductDetails(productId)
    }

}
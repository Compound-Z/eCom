package vn.ztech.software.ecom.data.remote.dto

import vn.ztech.software.ecom.domain.model.Product
import vn.ztech.software.ecom.domain.model.ProductDetails

data class ProductResponse(
    val id: Int
)
fun ProductResponse.toProduct(): Product {
    return Product(
        id = id
    )
}

data class ProductDetailsResponse(
    val id: Int,
    val name: String
)
fun ProductDetailsResponse.toProductDetails(): ProductDetails {
    return ProductDetails(
        id = id,
        name = name
    )
}

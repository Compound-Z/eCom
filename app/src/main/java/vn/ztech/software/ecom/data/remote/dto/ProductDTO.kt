package vn.ztech.software.ecom.data.remote.dto

import vn.ztech.software.ecom.domain.model.Product
import vn.ztech.software.ecom.domain.model.ProductDetails

data class ProductResponse(
    var id: String,
    var name: String = "",
//    var provider: String = "",
    var description: String = "",
    var category: String = "",
    var salePrice: Double = 0.0,
    var images: String = "",
    var avgRating: Double = 0.0
)
fun ProductResponse.toProduct(): Product {
    return Product(
        id = id,
        name = name,
//        provider = provider,
        description = description,
        category = category,
        salePrice = salePrice,
        images = images,
        avgRating = avgRating
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

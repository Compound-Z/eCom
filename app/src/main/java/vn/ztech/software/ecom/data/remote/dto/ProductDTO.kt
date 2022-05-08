package vn.ztech.software.ecom.data.remote.dto

import vn.ztech.software.ecom.domain.model.Product
import vn.ztech.software.ecom.domain.model.ProductDetails

data class ProductResponse(
    var id: String,
    var name: String = "",
    var category: String = "",
    var salePrice: Double = 0.0,
    var thumbnail: String = "",
    var avgRating: Double = 0.0
)
fun ProductResponse.toProduct(): Product {
    return Product(
        id = id,
        name = name,
        category = category,
        salePrice = salePrice,
        thumbnail = thumbnail,
        avgRating = avgRating
    )
}

data class ProductDetailsResponse(
    var id: String,
    var productGeneralInfo: Product,
    var description: String,
    var providerInfo: String, //should be another object Provider, will be fixed later
    var images: List<String>
)
fun ProductDetailsResponse.toProductDetails(): ProductDetails {
    return ProductDetails(
        id = id,
        productGeneralInfo = productGeneralInfo,
        description = description,
        providerInfo = providerInfo,
        images = images
    )
}

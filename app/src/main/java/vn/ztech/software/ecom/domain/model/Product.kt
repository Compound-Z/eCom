package vn.ztech.software.ecom.domain.model

data class Product(
    var id: String,
    var name: String = "",
//    var provider: String = "",
    var description: String = "",
    var category: String = "",
    var salePrice: Double = 0.0,
    var images: String = "",
    var avgRating: Double = 0.0
)
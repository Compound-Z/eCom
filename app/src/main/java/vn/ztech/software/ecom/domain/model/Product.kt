package vn.ztech.software.ecom.domain.model

data class Product(
    var id: String = "-1",
    var name: String = "",
    var category: String = "",
    var salePrice: Double = 0.0,
    var thumbnail: String = "",
    var avgRating: Double = 0.0
)

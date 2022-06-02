package vn.ztech.software.ecom.model

data class Product(
    val _id: String,
    val name: String,
    val description: String,
    val category: String,
    val brand: String,
    val image: String,
    val price: Int,
    val saleOffPercentage: Int,
    val freeShipping: Boolean,
    val inventory: Int,
    val saleNumber: Int,
    val numOfReviews: Int,
    val averageRating: Int,
    val featured: Boolean,
)
package vn.ztech.software.ecom.model

data class ReviewQueue(
    val _id: String,
    val createdAt: String,
    val imageUrl: String,
    val productId: String,
    val productName: String,
    val reviewRef: String,
    val updatedAt: String,
    val userId: String
)
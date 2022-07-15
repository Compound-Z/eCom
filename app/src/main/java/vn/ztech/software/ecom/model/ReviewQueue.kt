package vn.ztech.software.ecom.model

data class ReviewQueue(
    val _id: String,
    val createdAt: String,
    val imageUrl: String,
    val productId: String,
    val productName: String,
    val reviewRef: ReviewRef?,
    val updatedAt: String,
    val userId: String
)

data class ReviewRef(
    val _id: String,
    val content: String,
    val rating: Int,
    val userName: String,
    val isEdited: Boolean,
)
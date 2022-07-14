package vn.ztech.software.ecom.model

data class Review(
    val _id: String,
    val content: String,
    val createdAt: String,
    val imageUrl: String,
    val isEdited: Boolean,
    val productId: String,
    val productName: String,
    val rating: Int,
    val updatedAt: String,
    val userId: String,
    val userName: String
)
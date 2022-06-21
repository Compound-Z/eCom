package vn.ztech.software.ecom.api.response

class CartProductResponse(
    val _id: String,
    val name: String,
    val sku: String,
    val isSaling: Boolean,
    val price: Int,
    val imageUrl: String,
    val category: String,
    val saleNumber: Int,
    val weight: Int,
    val quantity: Int,
    val averageRating: Int,
    val productId: String,
)
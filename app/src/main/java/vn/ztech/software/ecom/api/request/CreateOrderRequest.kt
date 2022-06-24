package vn.ztech.software.ecom.api.request

data class CreateOrderRequest(
    val addressItemId: String,
    val note: String,
    val orderItems: List<CartItem>,
    val shippingServiceId: Int,
)

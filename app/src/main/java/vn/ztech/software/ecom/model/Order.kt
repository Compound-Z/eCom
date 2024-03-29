package vn.ztech.software.ecom.model

/**this is a minimal version of Order response from getMyOrder api*/
data class Order(
    val _id: String,
    val orderId: String,
    val billing: Billing,
    val orderItems: List<OrderItem>,
    val status: String,
    val updatedAt: String,
    val shippingDetails: ShippingDetails?,
)
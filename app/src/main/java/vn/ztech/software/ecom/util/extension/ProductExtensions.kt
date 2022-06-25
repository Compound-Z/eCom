package vn.ztech.software.ecom.util.extension

import vn.ztech.software.ecom.api.request.CartItem
import vn.ztech.software.ecom.api.response.CartProductResponse
import vn.ztech.software.ecom.model.OrderItem

fun MutableList<CartProductResponse>.toCartItems(): List<CartItem>{
    val cartItems = this.map {
        CartItem(it.productId, it.quantity)
    }
    return cartItems
}
fun MutableList<CartProductResponse>.toOrderItems(): List<OrderItem>{
    val orderItems = this.map {
        OrderItem(
            _id = "",
            imageUrl = it.imageUrl,
            name = it.name,
            price = it.price,
            productId = it.productId,
            quantity = it.quantity,
            sku = it.sku,
            weight = it.weight
        )
    }
    return orderItems
}
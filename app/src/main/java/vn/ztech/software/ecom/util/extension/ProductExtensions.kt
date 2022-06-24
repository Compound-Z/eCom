package vn.ztech.software.ecom.util.extension

import vn.ztech.software.ecom.api.request.CartItem
import vn.ztech.software.ecom.api.response.CartProductResponse

fun MutableList<CartProductResponse>.toCartItems(): List<CartItem>{
    val cartItems = this.map {
        CartItem(it.productId, it.quantity)
    }
    return cartItems
}
package vn.ztech.software.ecom.util.extension

import vn.ztech.software.ecom.api.request.CartItem
import vn.ztech.software.ecom.api.response.CartProductResponse
import vn.ztech.software.ecom.model.OrderItem
import vn.ztech.software.ecom.model.Shop
import vn.ztech.software.ecom.model.SubOrder

fun MutableList<CartProductResponse>.toCartItems(): List<CartItem>{
    val cartItems = this.map {
        CartItem(it.productId, it.quantity, -1) /**by default, if not set, shippingServiceId = -1*/
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
            weight = it.weight,
            shopId = it.shopId._id,
            shopName = it.shopId.name
        )
    }
    return orderItems
}
fun List<OrderItem>.toCartProductResponses(): List<CartProductResponse>{
    val cartProductResponses = this.map {
        CartProductResponse(
            _id = "",
            imageUrl = it.imageUrl,
            name = it.name,
            price = it.price,
            productId = it.productId,
            quantity = it.quantity,
            sku = it.sku,
            weight = it.weight,
            averageRating = -1,
            category = "",
            isSaling = true,
            saleNumber = -1,
            shopId = Shop(_id = it.shopId, name = it.shopName, __v = 0, addressItem = null, categories = listOf(), createdAt = "", imageUrl = "", numberOfProduct = 0, shippingShopId = "", updatedAt = "", userId = "", description = "")
        )
    }
    return cartProductResponses
}

fun List<CartProductResponse>.toListProductsAndShop(): List<Any> {
    val grouped = this.groupBy { it.shopId._id }
    val result = mutableListOf<Any>()
    grouped.forEach { shop ->

        /**add shop item to list, the shop info get from the first product since every product in the shop have the same shop info*/
        val firstProduct = shop.value[0]
        result.add(Shop(_id = firstProduct.shopId._id, name = firstProduct.shopId.name, __v = 0, addressItem = null, categories = listOf(), createdAt = "", imageUrl = "", numberOfProduct = 0, shippingShopId = "", updatedAt = "", userId = "", description = ""))

        shop.value.forEach { product ->
            result.add(product)
        }
    }
    return result
}

fun List<CartProductResponse>.toListSubOrders(): List<SubOrder> {
    val grouped = this.groupBy { it.shopId._id }
    val result = mutableListOf<SubOrder>()
    grouped.forEach { shop ->

        /**add shop item to list, the shop info get from the first product since every product in the shop have the same shop info*/
        val firstProduct = shop.value[0]
        result.add(
            SubOrder(
                Shop(_id = firstProduct.shopId._id, name = firstProduct.shopId.name, __v = 0, addressItem = null, categories = listOf(), createdAt = "", imageUrl = "", numberOfProduct = 0, shippingShopId = "", updatedAt = "", userId = "", description = ""),
                shop.value,
                -1, //initially, shippingServiceId is not set yet, it will be reset when user choose an shipping option returned from API
            null
            )
        )
    }
    return result
}
package vn.ztech.software.ecom.model

import vn.ztech.software.ecom.api.response.CartProductResponse
import vn.ztech.software.ecom.api.response.ShippingOption

data class SubOrder (
    val shop: Shop,
    val items: List<CartProductResponse>,
    var shippingServiceId: Int,
    var shippingOptions: List<ShippingOption>?,
)
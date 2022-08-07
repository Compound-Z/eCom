package vn.ztech.software.ecom.util.extension

import vn.ztech.software.ecom.api.response.ShippingOption

fun List<ShippingOption>.findSelectedShippingOption(shippingServiceId: Int): ShippingOption?{
    this.forEach {
        if (it.service_id==shippingServiceId) return it
    }
    return null
}
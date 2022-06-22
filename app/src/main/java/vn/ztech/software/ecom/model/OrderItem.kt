package vn.ztech.software.ecom.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import vn.ztech.software.ecom.database.utils.OrderStatus
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

@Parcelize
data class OrderItem(
    var orderId: String = "",
    var customerId: String = "",
    var items: List<CartItem> = ArrayList(),
    var itemsPrices: Map<String, Double> = mapOf(),
//    var deliveryAddress: Address = Address(),
    var shippingCharges: Double = 0.0,
    var paymentMethod: String = "",
    var orderDate: Date = Date(),
    var status: String = OrderStatus.CONFIRMED.name
) : Parcelable {
    fun toHashMap(): HashMap<String, Any> {
        return hashMapOf(
            "orderId" to orderId,
            "customerId" to customerId,
            "items" to items.map { it.toHashMap() },
            "itemsPrices" to itemsPrices,
//            "deliveryAddress" to deliveryAddress.toHashMap(),
            "shippingCharges" to shippingCharges,
            "paymentMethod" to paymentMethod,
            "orderDate" to orderDate,
            "status" to status
        )
    }
}
package vn.ztech.software.ecom.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class OrderDetails(
    val __v: Int,
    val _id: String,
    val address: AddressItem,
    val billing: Billing,
    val createdAt: String,
    val note: String,
    val orderItems: List<OrderItem>,
    val shippingDetails: ShippingDetails,
    val status: String,
    val updatedAt: String,
    val user: UserOrder,
    val shopRef: ShopRef?
):Parcelable


@Parcelize
data class Billing(
    val _id: String,
    val estimatedShippingFee: Int,
    val paymentMethod: String,
    val shippingFee: Int,
    val subTotal: Int
):Parcelable
@Parcelize
data class OrderItem(
    val _id: String,
    val imageUrl: String,
    val name: String,
    val price: Int,
    val productId: String,
    val quantity: Int,
    val sku: String,
    val weight: Int,
    val shopId: String,
    val shopName: String
):Parcelable
@Parcelize
data class ShippingDetails(
    val _id: String,
    val shippingProvider: String,
    val shippingServiceId: Int,
    val shippingServiceTypeId: Int,
    val weight: Int,
    var expectedDeliveryTime: String?,
    var shippingOrderCode: String?,
):Parcelable{
    override fun hashCode(): Int {
        /**override hashCOde fun so that NPE wont be thrown when sending object with null properties using bundle*/
        return _id.hashCode()
    }
}
@Parcelize
data class UserOrder(
    val _id: String,
    val name: String,
    val phoneNumber: String,
    val userId: String
):Parcelable


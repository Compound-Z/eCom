package vn.ztech.software.ecom.api.request

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class GetShippingOptionsReq(
    val addressItemId: String,
    val cartItems: List<CartItem>
):Parcelable
@Parcelize
data class CartItem(
    val productId: String,
    val quantity: Int
):Parcelable
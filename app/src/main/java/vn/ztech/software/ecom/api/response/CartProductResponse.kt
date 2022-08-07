package vn.ztech.software.ecom.api.response

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import vn.ztech.software.ecom.model.Shop

@Parcelize
class CartProductResponse(
    val _id: String,
    val name: String,
    val sku: String,
    val isSaling: Boolean,
    val price: Int,
    val imageUrl: String,
    val category: String,
    val saleNumber: Int,
    val weight: Int,
    val quantity: Int,
    val averageRating: Int,
    val productId: String,
    val shopId: Shop,
): Parcelable
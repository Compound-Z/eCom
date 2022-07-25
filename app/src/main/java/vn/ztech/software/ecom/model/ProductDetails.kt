package vn.ztech.software.ecom.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

data class ProductDetails(
    val __v: Int,
    val _id: String,
    val brandName: String,
    val createdAt: String,
    val description: String,
    val imageUrls: List<String>,
    val numOfReviews: Int,
    val origin: String,
    val productId: String,
    val reviews: List<Any>,
    val unit: String,
    val updatedAt: String,
    val shopId: ShopRef,
)
@Parcelize
data class ShopRef(
    val _id: String,
    val name: String,
    val imageUrl: String,
    val addressItem: AddressItem,
    val numberOfProduct: Int,
): Parcelable
//data class ProductDetails (
//    var id: String="-1",
//    var description: String = "",
//    var providerInfo: String = "", //should be another object Provider, will be fixed later
//    var images: List<String> = emptyList()
//)
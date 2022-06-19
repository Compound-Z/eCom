package vn.ztech.software.ecom.model

import android.os.Parcelable
import androidx.annotation.Keep
import kotlinx.android.parcel.Parcelize
@Keep
@Parcelize
data class Product(
    val _id: String,
    val name: String,
    val sku: String,
    val isSaling: Boolean,
    val price: Int,
    val imageUrl: String,
    val category: String,
    val saleNumber: Int,
    val weight: Int,
    val averageRating: Int,
) : Parcelable
//data class Product(
//    val _id: String,
//    val name: String,
//    val description: String,
//    val category: String,
//    val brand: String,
//    val image: String,
//    val price: Int,
//    val saleOffPercentage: Int,
//    val freeShipping: Boolean,
//    val inventory: Int,
//    val saleNumber: Int,
//    val numOfReviews: Int,
//    val averageRating: Int,
//    val featured: Boolean,
//)
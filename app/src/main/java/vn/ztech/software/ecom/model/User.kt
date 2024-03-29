package vn.ztech.software.ecom.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class User (
    val name: String,
    val userId: String,
    val role: String
): Parcelable
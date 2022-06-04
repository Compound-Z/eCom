package vn.ztech.software.ecom.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Address(
    var addressId: String = "",
    var fName: String = "",
    var lName: String = "",
    var countryISOCode: String = "",
    var streetAddress: String = "",
    var streetAddress2: String = "",
    var city: String = "",
    var state: String = "",
    var zipCode: String = "",
    var phoneNumber: String = ""
) : Parcelable {
    fun toHashMap(): HashMap<String, String> {
        return hashMapOf(
            "addressId" to addressId,
            "fName" to fName,
            "lName" to lName,
            "countryISOCode" to countryISOCode,
            "streetAddress" to streetAddress,
            "streetAddress2" to streetAddress2,
            "city" to city,
            "state" to state,
            "zipCode" to zipCode,
            "phoneNumber" to phoneNumber
        )
    }
}
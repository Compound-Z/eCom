package vn.ztech.software.ecom.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import kotlinx.android.parcel.Parcelize
import vn.ztech.software.ecom.database.local.user.ObjectListTypeConvertor
import vn.ztech.software.ecom.ui.UserType
import java.util.*
import kotlin.collections.ArrayList

@Parcelize
@Entity(tableName = "users")
data class UserData(
	@PrimaryKey
	var userId: String = "",
	var name: String = "",
	var mobile: String = "",
	var email: String = "",
	var password: String = "",
	var likes: List<String> = ArrayList(),
	@TypeConverters(ObjectListTypeConvertor::class)
	var addresses: List<Address> = ArrayList(),
	@TypeConverters(ObjectListTypeConvertor::class)
	var cart: List<CartItem> = ArrayList(),
	@TypeConverters(ObjectListTypeConvertor::class)
	var orders: List<OrderItem> = ArrayList(),
	var userType: String = UserType.CUSTOMER.name /**note: this field is "role" in the API*/
) : Parcelable {
	fun toHashMap(): HashMap<String, Any> {
		return hashMapOf(
			"userId" to userId,
			"name" to name,
			"email" to email,
			"mobile" to mobile,
			"password" to password,
			"likes" to likes,
			"addresses" to addresses.map { it.toHashMap() },
			"userType" to userType
		)
	}
}
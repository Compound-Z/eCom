package vn.ztech.software.ecom.common

object Constants {

    val BASE_URL = "https://ecom-z.herokuapp.com"
    val BASE_URL_DEBUG = "http://192.168.0.107:5000"

    fun getBaseUrl(): String{
        return BASE_URL
    }

    val TOKEN_NEAR_EXPIRED_TIME_IN_SECOND = 5*60 /**five minutes, consider the internet delay time*/

    const val VERIFY_FAILED = "pending"
    const val VERIFY_APPROVED = "approved"

    val OrderStatus = listOf<String>(
        "", //empty string for no filter query, that means get all data
        "PENDING",
        "PROCESSING",
        "CONFIRMED",
        "CANCELED",
        "RECEIVED",
    )
    const val NETWORK_PAGE_SIZE = 10
    const val REVIEW_PREVIEW_PAGE_SIZE = 3
    const val CHANNEL_ID = "666"
}
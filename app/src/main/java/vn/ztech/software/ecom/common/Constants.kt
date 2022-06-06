package vn.ztech.software.ecom.common

object Constants {

    val BASE_URL = "https://ecom-z.herokuapp.com"
    val BASE_URL_DEBUG = "http://192.168.0.107:5000"

    fun getBaseUrl(): String{
        return BASE_URL_DEBUG
    }

    val TOKEN_NEAR_EXPIRED_TIME_IN_SECOND = 5*60 /**five minutes, consider the internet delay time*/

}
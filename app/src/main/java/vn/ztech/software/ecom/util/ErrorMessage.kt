package vn.ztech.software.ecom.util

import android.util.Log
import vn.ztech.software.ecom.exception.RefreshTokenExpiredException
import vn.ztech.software.ecom.exception.ResourceException
import vn.ztech.software.ecom.exception.UnauthenticatedException
import java.net.ConnectException
import java.net.SocketTimeoutException

fun errorMessage(
    e: CustomError
): CustomError {
    Log.d("errorMessage", e.message.toString())
    when (e) {
        is UnauthenticatedException -> return e
        is RefreshTokenExpiredException -> return e
        is ResourceException -> return e
    }
    when (e.e){
        is ConnectException -> {
            e.customMessage = "Connection failed! Please check your internet!"
        }
        is SocketTimeoutException -> {
            e.customMessage = "Timeout! Please try again later!"
        }
        is CustomError->{
            e.customMessage = e.e.customMessage
        }
    }
    return e

}
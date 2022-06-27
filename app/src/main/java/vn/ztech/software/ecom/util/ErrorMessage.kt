package vn.ztech.software.ecom.util

import android.util.Log
import vn.ztech.software.ecom.exception.RefreshTokenExpiredException
import vn.ztech.software.ecom.exception.ResourceException
import vn.ztech.software.ecom.exception.UnauthenticatedException

fun errorMessage(
    e: CustomError
): CustomError {
    Log.d("errorMessage", e.message.toString())
    return when (e) {
        is UnauthenticatedException -> e
        is RefreshTokenExpiredException -> e
        is ResourceException -> e
        else -> e
    }
}
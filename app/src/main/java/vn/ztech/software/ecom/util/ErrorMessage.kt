package vn.ztech.software.ecom.util

import vn.ztech.software.ecom.exception.ResourceException

fun errorMessage(
    e: Throwable
): CustomError = when (e) {
    is ResourceException -> e
    else -> CustomError()
}
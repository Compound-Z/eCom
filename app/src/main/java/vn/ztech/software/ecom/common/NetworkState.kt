package vn.ztech.software.ecom.common

import vn.ztech.software.ecom.util.CustomError

sealed class LoadState<out T>{
    object Loading : LoadState<Nothing>()
    class Loaded<T>(val data: T): LoadState<T>()
    class Error<T>(val e: CustomError): LoadState<T>()
}
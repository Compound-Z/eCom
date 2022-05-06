package vn.ztech.software.ecom.common

sealed class LoadState<out T>{
    object Loading : LoadState<Nothing>()
    class Loaded<T>(val data: T): LoadState<T>()
    class Error<T>(val e: Throwable): LoadState<T>()
}
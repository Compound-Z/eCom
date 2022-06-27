package vn.ztech.software.ecom.common.extension

import android.util.Log
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import vn.ztech.software.ecom.common.LoadState
import vn.ztech.software.ecom.util.CustomError

fun <T> Flow<T>.toLoadState(): Flow<LoadState<T>> =
    map<T, LoadState<T>> {
        LoadState.Loaded(it)
    }.onStart{
        emit(LoadState.Loading)
    }.catch{
        Log.d("ERROR:toLoadState", it.toString())
        emit(LoadState.Error(CustomError(it)))
    }
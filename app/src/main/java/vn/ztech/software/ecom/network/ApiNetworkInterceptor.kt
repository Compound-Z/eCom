package vn.ztech.software.ecom.network

import android.util.Log
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import okhttp3.Interceptor
import okhttp3.Response
import vn.ztech.software.ecom.exception.RefreshTokenExpiredException
import vn.ztech.software.ecom.exception.ResourceException
import vn.ztech.software.ecom.util.CustomError
import java.net.HttpURLConnection
import kotlin.coroutines.CoroutineContext

class ApiNetworkInterceptor(private val gson: Gson): Interceptor, CoroutineScope {
    private val job = SupervisorJob()
    override val coroutineContext: CoroutineContext = job
    val TAG = "ApiNetworkInterceptor"
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder()
        val response = chain.proceed(request.build())
        Log.d("RESPONSE", response.peekBody(2048).string() +" " + response?.code)

        //if there is a error, throw errors
        if(response.code < HttpURLConnection.HTTP_OK || response.code >= HttpURLConnection.HTTP_BAD_REQUEST){
            var responseObj: ApiErrorMessageModel? = null
            response.body?.let {
                try {
                    responseObj = gson.fromJson(it.string(), ApiErrorMessageModel::class.java)
                    Log.d("RESPONSE OBJ", responseObj.toString())
                }catch (e: Exception){
                    throw CustomError(customMessage = "Response parsing")
                }
            }
            when(response.code) {
                HttpURLConnection.HTTP_BAD_REQUEST -> {
                    throw ResourceException(responseObj?.message?:"Bad request")
                }
                HttpURLConnection.HTTP_NOT_FOUND -> {
                    throw ResourceException(responseObj?.message?:"Not found")
                }
                HttpURLConnection.HTTP_INTERNAL_ERROR -> {
                    throw ResourceException(responseObj?.message?:"System error")
                }
                HttpURLConnection.HTTP_UNAUTHORIZED -> {
                    throw RefreshTokenExpiredException(responseObj?.message?:"Invalid refresh token")
                }
                HttpURLConnection.HTTP_UNAVAILABLE -> {
                    if(responseObj?.message == "Verify OTP failed"
                        || responseObj?.message == "Can not send OTP code"){
                        throw ResourceException(responseObj?.message.toString())
                    }else{
                        throw ResourceException("Service unavailable")
                    }
                }
            }
        }

        return response
    }
}
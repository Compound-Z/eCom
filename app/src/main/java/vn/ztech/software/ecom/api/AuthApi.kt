package vn.ztech.software.ecom.api

import androidx.annotation.Keep
import retrofit2.http.POST
import vn.ztech.software.ecom.api.response.BasicResponse
import vn.ztech.software.ecom.api.response.LogInResponse

@Keep
interface IAuthApi{
    @POST("/api/v1/auth/signup")
    suspend fun signup(): BasicResponse

    @POST("/api/v1/auth/verify-otp")
    suspend fun verify(): BasicResponse

    @POST("/api/v1/auth/login")
    suspend fun login(): LogInResponse

    @POST("/api/v1/auth/logout")
    suspend fun logout(): BasicResponse

    @POST("/api/v1/auth/refresh")
    suspend fun refresh(): String
}
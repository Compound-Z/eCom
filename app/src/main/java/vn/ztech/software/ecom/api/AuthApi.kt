package vn.ztech.software.ecom.api

import androidx.annotation.Keep
import retrofit2.http.Body
import retrofit2.http.POST
import vn.ztech.software.ecom.api.request.RefreshTokenRequest
import vn.ztech.software.ecom.api.response.BasicResponse
import vn.ztech.software.ecom.api.response.LogInResponse
import vn.ztech.software.ecom.api.response.TokenResponse

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

    @POST("/api/v1/auth/refresh-token")
    suspend fun refreshToken(@Body request: RefreshTokenRequest): TokenResponse
}
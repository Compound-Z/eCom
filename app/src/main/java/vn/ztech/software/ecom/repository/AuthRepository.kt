package vn.ztech.software.ecom.repository

import android.util.Log
import vn.ztech.software.ecom.api.IAuthApi
import vn.ztech.software.ecom.api.request.RefreshTokenRequest
import vn.ztech.software.ecom.api.response.BasicResponse
import vn.ztech.software.ecom.api.response.LogInResponse
import vn.ztech.software.ecom.api.response.TokenResponse
import vn.ztech.software.ecom.common.Constants
import vn.ztech.software.ecom.database.local.user.UserManager
import java.util.concurrent.TimeUnit

interface IAuthRepository {
    suspend fun signup(): BasicResponse
    suspend fun verifyAccount(otp: String, phoneNumber: String): BasicResponse
    suspend fun login(productId: String): LogInResponse
    suspend fun logout(): BasicResponse
    suspend fun refreshToken(refreshToken: String): TokenResponse
    fun checkNeedToRefreshToken(): Boolean
}

class AuthRepository(
    private val authApi: IAuthApi,
    private val userManager: UserManager
): IAuthRepository{
    override suspend fun signup(): BasicResponse {
        return authApi.signup()
    }

    override suspend fun verifyAccount(otp: String, phoneNumber: String): BasicResponse {
        return authApi.verify()
    }

    override suspend fun login(productId: String): LogInResponse {
        return authApi.login()
    }

    override suspend fun logout(): BasicResponse {
        return authApi.logout()
    }

    override suspend fun refreshToken(refreshToken: String): TokenResponse {
        return authApi.refreshToken(RefreshTokenRequest(refreshToken))
    }

    override fun checkNeedToRefreshToken(): Boolean {
        val refreshTokenExpire = userManager.getRefreshTokenExpires()
        val accessTokenExpire = userManager.getAccessTokenExpires()
        if ("" == refreshTokenExpire || "" == accessTokenExpire) {
            return true
        }

        val now = TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis())
        if (accessTokenExpire.toLong() < now + Constants.TOKEN_NEAR_EXPIRED_TIME_IN_SECOND){
            Log.d("ERROR:", accessTokenExpire.toLong().toString() + " " + (now + Constants.TOKEN_NEAR_EXPIRED_TIME_IN_SECOND))
            Log.d("ERROR: ", "accessTokenExpire")
            return true
        }
        if (refreshTokenExpire.toLong() < now + Constants.TOKEN_NEAR_EXPIRED_TIME_IN_SECOND){
            Log.d("ERROR: ", "refreshTokenExpire")
            return true
        }
        return false
    }
}
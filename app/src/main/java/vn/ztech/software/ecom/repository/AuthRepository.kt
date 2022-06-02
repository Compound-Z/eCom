package vn.ztech.software.ecom.repository

import vn.ztech.software.ecom.api.IAuthApi
import vn.ztech.software.ecom.api.response.BasicResponse
import vn.ztech.software.ecom.api.response.LogInResponse

interface IAuthRepository {
    suspend fun signup(): BasicResponse
    suspend fun verifyAccount(otp: String, phoneNumber: String): BasicResponse
    suspend fun login(productId: String): LogInResponse
    suspend fun logout(): BasicResponse
    suspend fun refresh(): String
}

class AuthRepository(private val authApi: IAuthApi): IAuthRepository{
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

    override suspend fun refresh(): String {
        return authApi.refresh()
    }
}
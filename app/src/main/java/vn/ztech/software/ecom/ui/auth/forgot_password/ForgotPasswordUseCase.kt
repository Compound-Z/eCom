package vn.ztech.software.ecom.ui.auth.forgot_password

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import vn.ztech.software.ecom.api.response.BasicResponse
import vn.ztech.software.ecom.api.response.LogInResponse
import vn.ztech.software.ecom.api.response.TokenResponse
import vn.ztech.software.ecom.database.local.user.UserManager
import vn.ztech.software.ecom.model.UserData
import vn.ztech.software.ecom.repository.IAuthRepository

interface IResetPasswordUseCase{
    fun sendResetPasswordRequest(phoneNumber: String, password: String): Flow<BasicResponse>
}

class ResetPasswordUseCase(private val authRepository: IAuthRepository): IResetPasswordUseCase {
    override fun sendResetPasswordRequest(phoneNumber: String, password: String): Flow<BasicResponse> = flow {
        emit(authRepository.sendResetPasswordRequest(phoneNumber, password))
    }
}
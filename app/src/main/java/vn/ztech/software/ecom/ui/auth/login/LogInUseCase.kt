package vn.ztech.software.ecom.ui.auth.login

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import vn.ztech.software.ecom.api.response.LogInResponse
import vn.ztech.software.ecom.repository.IAuthRepository

interface ILogInUseCase{
    fun login(phoneNumber: String, password: String): Flow<LogInResponse>
}

class LogInUseCase(private val authRepository: IAuthRepository): ILogInUseCase {
    override fun login(phoneNumber: String, password: String): Flow<LogInResponse> = flow {
        emit(authRepository.login(phoneNumber, password))
    }
}
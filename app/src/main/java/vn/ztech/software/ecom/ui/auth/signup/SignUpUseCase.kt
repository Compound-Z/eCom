package vn.ztech.software.ecom.ui.auth.signup

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import vn.ztech.software.ecom.api.response.BasicResponse
import vn.ztech.software.ecom.model.UserData
import vn.ztech.software.ecom.repository.IAuthRepository

interface ISignUpUseCase{
    fun sendSignUpRequest(user: UserData): Flow<BasicResponse>
}
class SignUpUseCase(private val authRepository: IAuthRepository): ISignUpUseCase {
    override fun sendSignUpRequest(user: UserData): Flow<BasicResponse> = flow {
        val result = authRepository.sendSignUpRequest(user)
        emit(result)
    }
}
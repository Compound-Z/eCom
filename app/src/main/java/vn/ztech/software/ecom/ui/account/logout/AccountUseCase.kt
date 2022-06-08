package vn.ztech.software.ecom.ui.account.logout

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import vn.ztech.software.ecom.api.response.BasicResponse
import vn.ztech.software.ecom.api.response.LogInResponse
import vn.ztech.software.ecom.api.response.TokenResponse
import vn.ztech.software.ecom.database.local.user.UserManager
import vn.ztech.software.ecom.model.UserData
import vn.ztech.software.ecom.repository.IAuthRepository

interface IAccountUseCase{
    fun logOut(): Flow<BasicResponse>
    fun clearLogs()
}

class AccountUseCase(private val authRepository: IAuthRepository, private val userManager: UserManager): IAccountUseCase {
    override fun logOut(): Flow<BasicResponse> = flow {
        emit(authRepository.logout())
    }
    override fun clearLogs() {
        userManager.clearLogs()
    }
}
package vn.ztech.software.ecomSeller.ui.main

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import vn.ztech.software.ecom.api.response.BasicResponse
import vn.ztech.software.ecom.repository.IAuthRepository

interface IMainUseCase{
    suspend fun updateFCMToken(fcmToken: String): Flow<BasicResponse>
}

class MainUseCase(private val authRepository: IAuthRepository): IMainUseCase{
    override suspend fun updateFCMToken(fcmToken: String): Flow<BasicResponse> = flow {
        emit(authRepository.updateFCMToken(fcmToken))
    }

}
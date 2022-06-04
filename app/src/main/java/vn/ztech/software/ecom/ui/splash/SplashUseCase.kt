package vn.ztech.software.ecom.ui.splash

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import vn.ztech.software.ecom.api.response.TokenResponse
import vn.ztech.software.ecom.database.user.UserManager
import vn.ztech.software.ecom.repository.IAuthRepository
import kotlin.system.measureTimeMillis

interface ISplashUseCase{
    enum class PAGE {
        SIGNUP, LOGIN, MAIN
    }
    fun nextPage(): Flow<PAGE>
    fun checkNeedToRefreshToken(): Boolean
    fun getToken(): Flow<TokenResponse>


}
class SplashUseCase(
    private val authRepository: IAuthRepository,
    private val userManager: UserManager
): ISplashUseCase {
    override fun nextPage(): Flow<ISplashUseCase.PAGE> = flow {

        lateinit var nextPage: ISplashUseCase.PAGE
        val time = measureTimeMillis {
            nextPage = userManager.nextPage()
        }

        // 少なくとも2000msスプラッシュで表示することを保証。
        // 2000ms以上更新に時間かかってるなら、さらに2000秒待たないように無視する。
        val delayTime = 2000 - time
        if (delayTime > 0) {
            delay(delayTime)
        }
        emit(nextPage)
    }

    override fun checkNeedToRefreshToken(): Boolean {
        return authRepository.checkNeedToRefreshToken()
    }

    override fun getToken(): Flow<TokenResponse> = flow {
        val tokenInfo = authRepository.refreshToken(userManager.getRefreshToken())
//        userManager.saveLatestTimestamp(System.currentTimeMillis())
        emit(tokenInfo)
    }

}
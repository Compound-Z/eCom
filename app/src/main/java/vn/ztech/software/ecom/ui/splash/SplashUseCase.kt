package vn.ztech.software.ecom.ui.splash

import vn.ztech.software.ecom.repository.IAuthRepository

interface ISplashUseCase{

}
class SplashUseCase(private val authRepository: IAuthRepository): ISplashUseCase {
}
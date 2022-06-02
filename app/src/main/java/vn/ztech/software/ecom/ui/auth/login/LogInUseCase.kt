package vn.ztech.software.ecom.ui.auth.login

import vn.ztech.software.ecom.repository.IAuthRepository

interface ILogInUseCase{
}

class LogInUseCase(private val authRepository: IAuthRepository): ILogInUseCase {
}
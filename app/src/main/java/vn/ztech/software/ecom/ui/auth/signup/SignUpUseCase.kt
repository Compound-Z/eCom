package vn.ztech.software.ecom.ui.auth.signup

import vn.ztech.software.ecom.repository.IAuthRepository

interface ISignUpUseCase{

}
class SignUpUseCase(private val authRepository: IAuthRepository): ISignUpUseCase {
}
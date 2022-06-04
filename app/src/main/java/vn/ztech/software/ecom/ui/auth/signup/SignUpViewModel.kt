package vn.ztech.software.ecom.ui.auth.signup

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import vn.ztech.software.ecom.model.UserData
import vn.ztech.software.ecom.ui.SignUpViewErrors
import vn.ztech.software.ecom.ui.UserType
import vn.ztech.software.ecom.util.*
import vn.ztech.software.ecom.util.isEmailValid
import vn.ztech.software.ecom.util.isPhoneValid

class SignUpViewModel(private val useCase: ISignUpUseCase): ViewModel() {
    private val _errorStatus = MutableLiveData<SignUpViewErrors>()
    val errorStatus: LiveData<SignUpViewErrors> get() = _errorStatus

    private val _userData = MutableLiveData<UserData>()
    val userData: LiveData<UserData> get() = _userData

    fun signUpSubmitData(
        name: String,
        mobile: String,
        email: String,
        pwd1: String,
        pwd2: String,
        isAccepted: Boolean,
        isSeller: Boolean
    ) {
        if (name.isBlank() || mobile.isBlank() || email.isBlank() || pwd1.isBlank() || pwd2.isBlank()) {
            _errorStatus.value = SignUpViewErrors.ERR_EMPTY
        } else {
            if (pwd1 != pwd2) {
                _errorStatus.value = SignUpViewErrors.ERR_PWD12NS
            } else {
                if (!isAccepted) {
                    _errorStatus.value = SignUpViewErrors.ERR_NOT_ACC
                } else {
                    var err = ERR_INIT
                    if (!isEmailValid(email)) {
                        err += ERR_EMAIL
                    }
                    if (!isPhoneValid(mobile)) {
                        err += ERR_MOBILE
                    }
                    when (err) {
                        ERR_INIT -> {
                            _errorStatus.value = SignUpViewErrors.NONE
                            val uId = getRandomString(32, "84" + mobile.trim(), 6)
                            val newData =
                                UserData(
                                    uId,
                                    name.trim(),
                                    "+84" + mobile.trim(),
                                    email.trim(),
                                    pwd1.trim(),
                                    ArrayList(),
                                    ArrayList(),
                                    ArrayList(),
                                    ArrayList(),
                                    /**if (isSeller) UserType.SELLER.name else */
                                    /**if (isSeller) UserType.SELLER.name else */
                                    UserType.CUSTOMER.name
                                )
                            _userData.value = newData
                        }
                        (ERR_INIT + ERR_EMAIL) -> _errorStatus.value = SignUpViewErrors.ERR_EMAIL
                        (ERR_INIT + ERR_MOBILE) -> _errorStatus.value = SignUpViewErrors.ERR_MOBILE
                        (ERR_INIT + ERR_EMAIL + ERR_MOBILE) -> _errorStatus.value = SignUpViewErrors.ERR_EMAIL_MOBILE
                    }
                }
            }
        }

    }
}
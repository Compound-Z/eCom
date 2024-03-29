package vn.ztech.software.ecom.ui.auth.login

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import vn.ztech.software.ecom.api.response.LogInResponse
import vn.ztech.software.ecom.api.response.Token
import vn.ztech.software.ecom.api.response.TokenResponse
import vn.ztech.software.ecom.common.LoadState
import vn.ztech.software.ecom.common.extension.toLoadState
import vn.ztech.software.ecom.database.utils.UserType
import vn.ztech.software.ecom.model.UserData
import vn.ztech.software.ecom.ui.LoginViewErrors
import vn.ztech.software.ecom.util.*
import vn.ztech.software.ecom.util.isPasswordValid
import vn.ztech.software.ecom.util.isPhoneNumberValid

class LogInViewModel(private val useCase: ILogInUseCase): ViewModel() {
    val loading = MutableLiveData<Boolean>()
    val error = MutableLiveData<CustomError?>()
    val errorInputData = MutableLiveData<LoginViewErrors>()
    val isLogInSuccessfully = MutableLiveData<Boolean>()
    var tokens: TokenResponse? = null
    var userData: UserData? = null

    //todo: cache user data
    fun login(phoneNumber: String, password: String) {
        if (isLogInInfoValid(phoneNumber, password))
            viewModelScope.launch {
                useCase.login(phoneNumber, password).flowOn(Dispatchers.IO).toLoadState().collect {
                    when (it) {
                        is LoadState.Loading -> {
                            loading.value = true
                        }
                        is LoadState.Loaded -> {
                            Log.d("LOGIN:", "LoadState.Loaded ${it.data}")

                            if(checkIdCustomer(it.data.user)){
                                userData = it.data.user
                                tokens = it.data.tokens
                                loading.value = false
                                saveLogInInfo(userData, tokens)
                                isLogInSuccessfully.value = true
                            }else{
                                loading.value = false
                                error.value = errorMessage(CustomError(customMessage = "Wrong account type, please use an Customer account to login"))
                            }
                        }
                        is LoadState.Error -> {
//                        if (it.e is TokenRefreshing) {
//                            return@collect
//                        }xxx
                            Log.d("LOGIN:", "LoadState.Error ${it.e.message}")
                            loading.value = false
                            error.value = errorMessage(it.e)
                        }
                    }
                }
            }
    }
    private fun checkIdCustomer(user: UserData): Boolean {
        return user.role == UserType.customer.name
    }
    private fun saveLogInInfo(userData: UserData?, tokens: TokenResponse?) {
        if(userData != null && tokens != null){
            Log.d("LOGIN", "LogInViewModel saveLogInInfo")
            useCase.saveLogInInfo(userData, tokens)
        }
    }

    private fun isLogInInfoValid(phoneNumber: String, password: String): Boolean {
        if (phoneNumber.isBlank() || password.isBlank()) {
            errorInputData.value = LoginViewErrors.ERR_EMPTY
            return false
        }
        if (!isPhoneNumberValid(phoneNumber)) {
            errorInputData.value = LoginViewErrors.ERR_MOBILE
            return false
        }
        if (!isPasswordValid(password)) {
            errorInputData.value = LoginViewErrors.ERR_PASSWORD
            return false
        }
        errorInputData.value = LoginViewErrors.NONE
        return true
    }
    fun clearErrors() {
        error.value = null
    }
}
package vn.ztech.software.ecom.ui.splash

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import vn.ztech.software.ecom.api.response.TokenResponse
import vn.ztech.software.ecom.common.LoadState
import vn.ztech.software.ecom.common.extension.toLoadState
import vn.ztech.software.ecom.util.CustomError
import vn.ztech.software.ecom.util.errorMessage

class SplashViewModel(private val useCase: ISplashUseCase): ViewModel() {
    val error = MutableLiveData<CustomError>()
    val tokenResponse = MutableLiveData<TokenResponse>()
    val page = MutableLiveData<ISplashUseCase.PAGE>()
    fun start() {
        viewModelScope.launch {
            useCase.nextPage().flowOn(Dispatchers.IO).toLoadState().collect {
                when (it) {
                    is LoadState.Loading -> {
                        /**
                         * do nothing since this is the Splash Screen
                         * **/
                    }
                    is LoadState.Loaded -> {
                        page.value = it.data?:ISplashUseCase.PAGE.LOGIN_SIGNUP
                    }
                    is LoadState.Error -> {
//                        if (it.e is TokenRefreshing) {
//                            return@collect
//                        }
                        error.value = errorMessage(it.e)
                    }
                }
            }
        }
    }
}
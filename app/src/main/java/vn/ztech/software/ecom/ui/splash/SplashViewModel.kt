package vn.ztech.software.ecom.ui.splash

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import vn.ztech.software.ecom.util.CustomError

class SplashViewModel(private val useCase: ISplashUseCase): ViewModel() {
    val error = MutableLiveData<CustomError>()
}
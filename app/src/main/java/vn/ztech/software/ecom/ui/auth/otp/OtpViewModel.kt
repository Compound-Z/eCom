package vn.ztech.software.ecom.ui.auth.otp

import android.app.Application
import android.util.Log
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import vn.ztech.software.ecom.api.response.VerifyOtpResponse
import vn.ztech.software.ecom.common.LoadState
import vn.ztech.software.ecom.common.StoreDataStatus
import vn.ztech.software.ecom.common.extension.toLoadState
import vn.ztech.software.ecom.util.CustomError
import vn.ztech.software.ecom.util.errorMessage
import java.util.concurrent.TimeUnit

private const val TAG = "OtpViewModel"

class OtpViewModel(private val useCase: IOtpUseCase) : ViewModel() {
	init {
	    Log.d("ERROR:","OtpViewModel created")
	}
	val otpStatus = MutableLiveData<VerifyOtpResponse>()
	val error = MutableLiveData<CustomError>()

	fun verifyOTP(phoneNumber: String, otp: String) {
		viewModelScope.launch {
			useCase.verifyOtp(phoneNumber, otp).flowOn(Dispatchers.IO).toLoadState().collect {
				when(it){
					LoadState.Loading -> {
						Log.d("OTP", "loading")
					}
					is LoadState.Loaded -> {
						Log.d("OTP", it.data.toString())
						otpStatus.value = it.data?: VerifyOtpResponse("")
					}
					is LoadState.Error -> {
						Log.d("OTP:ERROR:", it.e.toString())
						error.value = errorMessage(it.e)
					}
				}
			}
		}
	}
}
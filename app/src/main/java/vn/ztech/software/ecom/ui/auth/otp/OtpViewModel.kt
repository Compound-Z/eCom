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
						otpStatus.value = it.data?: VerifyOtpResponse("x")
					}
					is LoadState.Error -> {
						Log.d("OTP:ERROR:", it.e.toString())
						error.value = errorMessage(it.e)
					}
				}
			}
		}
	}

//	fun signUp() {
//		viewModelScope.launch {
//			authRepository.signUp(uData)
//		}
//	}
//
//	fun login(rememberMe: Boolean) {
//		viewModelScope.launch {
//			authRepository.login(uData, rememberMe)
//		}
//	}
//
//	private val callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
//
//		override fun onVerificationCompleted(credential: PhoneAuthCredential) {
//			Log.d(TAG, "onVerificationCompleted:$credential")
//			_otpStatus.value = OTPStatus.CORRECT
//			authRepository.signInWithPhoneAuthCredential(credential, isUserLoggedIn, application.applicationContext)
//		}
//
//		override fun onVerificationFailed(e: FirebaseException) {
//			Log.w(TAG, "onVerificationFailed", e)
//			_otpStatus.value = OTPStatus.INVALID_REQ
//			if (e is FirebaseAuthInvalidCredentialsException) {
//				Log.w(TAG, "onVerificationFailed, invalid request, ", e)
//			} else if (e is FirebaseTooManyRequestsException) {
//				Log.w(TAG, "onVerificationFailed, sms quota exceeded, ", e)
//			}
//		}
//
//		override fun onCodeSent(
//			verificationId: String,
//			token: PhoneAuthProvider.ForceResendingToken
//		) {
//			// Save verification ID and resending token so we can use them later
//			storedVerificationId = verificationId
//			resendToken = token
//			Log.w(TAG, "OTP SENT")
//			_isOTPSent.value = true
//			_otpStatus.value = OTPStatus.NONE
//		}
//	}
//
//	private fun verifyPhoneWithCode(verificationId: String, code: String, isUserLoggedIn: MutableLiveData<Boolean>) {
//		try {
//			val credential = PhoneAuthProvider.getCredential(verificationId, code)
//			authRepository.signInWithPhoneAuthCredential(credential, isUserLoggedIn, getApplication<ShoppingApplication>().applicationContext)
//		} catch (e: Exception) {
//			Log.d(TAG, "onVerifyWithCode: Exception Occurred: ${e.message}")
//			_otpStatus.value = OTPStatus.INVALID_REQ
//		}
//	}
}
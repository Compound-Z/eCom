package vn.ztech.software.ecom.api.request

data class VerifyOtpRequest (
    val phoneNumber: String,
    val otp: String
)
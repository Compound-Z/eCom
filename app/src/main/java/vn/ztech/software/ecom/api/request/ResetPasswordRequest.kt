package vn.ztech.software.ecom.api.request

data class ResetPasswordRequest(
    val phoneNumber: String,
    val password: String,
    val otp: String
)
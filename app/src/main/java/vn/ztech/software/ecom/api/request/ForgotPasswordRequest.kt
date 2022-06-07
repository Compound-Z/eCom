package vn.ztech.software.ecom.api.request

data class ForgotPasswordRequest(
    val phoneNumber: String,
    val password: String
)

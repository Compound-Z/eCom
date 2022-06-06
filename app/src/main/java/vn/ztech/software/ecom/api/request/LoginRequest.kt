package vn.ztech.software.ecom.api.request

data class LoginRequest (
    val phoneNumber: String,
    val password: String
)
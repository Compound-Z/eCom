package vn.ztech.software.ecom.api.response

data class TokenResponse (
    val access_token : String,
    val refresh_token : String,
)
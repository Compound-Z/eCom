package vn.ztech.software.ecom.api.response

data class TokenResponse (
    val accessToken : Token,
    val refreshToken : Token,
)
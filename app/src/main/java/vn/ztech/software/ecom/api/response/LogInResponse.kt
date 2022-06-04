package vn.ztech.software.ecom.api.response

import vn.ztech.software.ecom.model.User

data class LogInResponse (
    val user: User,
    val accessToken: Token,
    val refreshToken: Token
)
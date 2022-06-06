package vn.ztech.software.ecom.api.response

import vn.ztech.software.ecom.model.User
import vn.ztech.software.ecom.model.UserData

data class LogInResponse (
    val user: UserData,
    val tokens: TokenResponse
)
package vn.ztech.software.ecom.exception

import vn.ztech.software.ecom.util.CustomError

class RefreshTokenExpiredException(override val customMessage: String): CustomError()
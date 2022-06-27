package vn.ztech.software.ecom.exception

import vn.ztech.software.ecom.util.CustomError

class RefreshTokenExpiredException(override var customMessage: String): CustomError(customMessage = customMessage)
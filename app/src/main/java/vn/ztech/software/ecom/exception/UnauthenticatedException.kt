package vn.ztech.software.ecom.exception

import vn.ztech.software.ecom.util.CustomError

class UnauthenticatedException(override var customMessage: String = "Wrong phone number or password") : CustomError(customMessage = customMessage)
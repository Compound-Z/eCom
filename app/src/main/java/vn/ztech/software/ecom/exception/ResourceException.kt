package vn.ztech.software.ecom.exception

import vn.ztech.software.ecom.util.CustomError

class ResourceException(override var customMessage: String = "") : CustomError(customMessage)
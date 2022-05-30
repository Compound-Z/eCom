package vn.ztech.software.ecom.exception

import vn.ztech.software.ecom.util.CustomError

class ResourceException(override val customMessage: String = "") : CustomError(customMessage)
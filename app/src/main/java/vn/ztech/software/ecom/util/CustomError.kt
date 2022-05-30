package vn.ztech.software.ecom.util

import java.io.IOException

open class CustomError(open val customMessage: String = "Some error happened!"): IOException()
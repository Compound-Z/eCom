package vn.ztech.software.ecom.util.extension

import android.app.Activity
import vn.ztech.software.ecom.util.CustomError


fun Activity.showErrorDialog(e: CustomError) {
//    if (refreshTokenExpiredError(e)) {
//        return
//    }
//    if (accountErrorError(e)) {
//        return
//    }
//    if (accountPerrmissionError(e)) {
//        return
//    }
    e.showErrorDialog(this)
}
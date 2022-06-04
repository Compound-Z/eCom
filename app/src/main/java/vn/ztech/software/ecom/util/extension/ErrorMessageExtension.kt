package vn.ztech.software.ecom.util.extension

import android.app.Activity
import androidx.appcompat.app.AlertDialog
import org.koin.android.ext.android.inject
import vn.ztech.software.ecom.exception.RefreshTokenExpiredException
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

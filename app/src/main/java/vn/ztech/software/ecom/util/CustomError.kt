package vn.ztech.software.ecom.util

import android.content.Context
import androidx.appcompat.app.AlertDialog
import vn.ztech.software.ecom.R
import java.io.IOException

open class CustomError(open val customMessage: String = "Some error happened!"): IOException(){
    fun showErrorDialog(context: Context): AlertDialog {
        return AlertDialog.Builder(context)
            .setTitle(R.string.error_dialog_tittle)
            .setMessage(customMessage)
            .setPositiveButton(R.string.ok, null)
            .show()
    }
}
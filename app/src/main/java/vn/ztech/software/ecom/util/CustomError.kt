package vn.ztech.software.ecom.util

import android.content.Context
import android.content.DialogInterface
import android.util.Log
import androidx.appcompat.app.AlertDialog
import vn.ztech.software.ecom.R
import vn.ztech.software.ecom.ui.auth.otp.OtpActivity
import java.io.IOException
import java.io.Serializable
import kotlin.reflect.typeOf

open class CustomError( val e: Throwable = Throwable(), open var customMessage: String = "System error! Please try again later!"): IOException(), Serializable{
    fun showErrorDialog(context: Context, listener: DialogInterface.OnClickListener? = null): AlertDialog {
        Log.d("showErrorDialog", e.message.toString())
        return AlertDialog.Builder(context)
            .setTitle(R.string.error_dialog_tittle)
            .setMessage(customMessage)
            .setPositiveButton(R.string.ok, listener)
            .show()
    }
    override fun equals(other: Any?): Boolean {
        if (other == null) return false
        return this.customMessage == (other as CustomError).customMessage
    }


}
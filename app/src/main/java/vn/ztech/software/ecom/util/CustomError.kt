package vn.ztech.software.ecom.util

import android.content.Context
import android.util.Log
import androidx.appcompat.app.AlertDialog
import vn.ztech.software.ecom.R
import java.io.IOException
import kotlin.reflect.typeOf

open class CustomError(private val e: Throwable = Throwable(), open val customMessage: String = "Some error happened!"): IOException(){
    init {
        Log.d("CustomError", e.message.toString())
    }
    fun showErrorDialog(context: Context): AlertDialog {
        Log.d("showErrorDialog", e.message.toString())
        return AlertDialog.Builder(context)
            .setTitle(R.string.error_dialog_tittle)
            .setMessage(customMessage)
            .setPositiveButton(R.string.ok, null)
            .show()
    }


}
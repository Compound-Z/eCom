package vn.ztech.software.ecom.ui

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager

enum class SignUpViewErrors { NONE, ERR_EMAIL, ERR_MOBILE, ERR_EMAIL_MOBILE, ERR_EMPTY, ERR_NOT_ACC, ERR_PWD12NS }
enum class LoginViewErrors { NONE, ERR_EMPTY, ERR_MOBILE, ERR_PASSWORD }
enum class UserType { CUSTOMER, SELLER }

class MyOnFocusChangeListener : View.OnFocusChangeListener {
    override fun onFocusChange(v: View?, hasFocus: Boolean) {
        if (v != null) {
            val inputManager =
                v.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            if (!hasFocus) {

                inputManager.hideSoftInputFromWindow(v.windowToken, 0)
            } else {
                inputManager.toggleSoftInputFromWindow(v.windowToken, 0, 0)

            }
        }
    }
}
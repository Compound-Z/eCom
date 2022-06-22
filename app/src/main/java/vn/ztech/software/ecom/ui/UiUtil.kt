package vn.ztech.software.ecom.ui

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager

enum class SignUpViewErrors { NONE, ERR_EMAIL, ERR_MOBILE, ERR_EMAIL_MOBILE, ERR_EMPTY, ERR_NOT_ACC, ERR_PWD12NS, ERR_PW_INVALID }
enum class LoginViewErrors { NONE, ERR_EMPTY, ERR_MOBILE, ERR_PASSWORD, ERR_RETYPE_PASSWORD }
enum class UserType { CUSTOMER, SELLER }
enum class OTPErrors { NONE, ERROR}
enum class AddAddressViewErrors { EMPTY, ERR_FNAME_EMPTY, ERR_LNAME_EMPTY, ERR_STR1_EMPTY, ERR_CITY_EMPTY, ERR_STATE_EMPTY, ERR_ZIP_EMPTY, ERR_ZIP_INVALID, ERR_PHONE_INVALID, ERR_PHONE_EMPTY }

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
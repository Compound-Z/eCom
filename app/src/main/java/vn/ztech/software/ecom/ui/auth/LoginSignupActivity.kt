package vn.ztech.software.ecom.ui.auth

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import vn.ztech.software.ecom.R
import vn.ztech.software.ecom.ui.auth.login.LoginFragment
import vn.ztech.software.ecom.ui.auth.signup.SignupFragment
import vn.ztech.software.ecom.ui.splash.ISplashUseCase

class LoginSignupActivity : AppCompatActivity() {
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		val page = intent.getSerializableExtra("PAGE")
		setContentView(R.layout.activity_signup)

		page?.let{
			when(page){
				ISplashUseCase.PAGE.LOGIN -> {
					supportFragmentManager.
					beginTransaction().
					replace(R.id.nav_host_fragment, LoginFragment(), "LoginFragment").
					commit()
				}
				else -> {}
			}
		}

	}
}
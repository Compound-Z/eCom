package vn.ztech.software.ecom.ui.splash

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import org.koin.androidx.viewmodel.ext.android.viewModel
import vn.ztech.software.ecom.R
import vn.ztech.software.ecom.exception.RefreshTokenExpiredException
import vn.ztech.software.ecom.ui.auth.LoginSignupActivity
import vn.ztech.software.ecom.ui.main.MainActivity
import vn.ztech.software.ecom.util.extension.showErrorDialog

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {

	private val viewModel: SplashViewModel by viewModel()

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_splash)

		viewModel.error.observe(this, Observer {
			it ?: return@Observer
			if(it is RefreshTokenExpiredException){
				openLogInSignUpActivity(ISplashUseCase.PAGE.LOGIN)
			}else{
				showErrorDialog(it)
			}
		})
		viewModel.page.observe(this) { page: ISplashUseCase.PAGE ->
			when (page) {
				ISplashUseCase.PAGE.LOGIN,
				ISplashUseCase.PAGE.SIGNUP -> {
					openLogInSignUpActivity(page)
				}
				ISplashUseCase.PAGE.MAIN -> {
					/**for testing only. todo: uncomment this*/
//					if (viewModel.checkNeedToRefreshToken()) {
//						viewModel.getToken()
//					} else {
						startActivity(
							Intent(
								this@SplashActivity,
								MainActivity::class.java
							)
						)
						finish()
//					}
				}
			}
		}

		viewModel.tokenResponse.observe(this, Observer {
			it ?: return@Observer
			if (it.accessToken.token != "") {
				startActivity(
					Intent(
						this@SplashActivity,
						MainActivity::class.java
					)
				)
				finish()
			}
//			} else {
//				val intent = Intent(this@SplashActivity, LoginActivity::class.java)
//				intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
//				startActivity(intent)
//				finish()
//			}
		})


	}

	private fun openLogInSignUpActivity(page: ISplashUseCase.PAGE){
		val intent = Intent(this@SplashActivity, LoginSignupActivity::class.java)
		intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK
		intent.putExtra("PAGE", page)
		startActivity(intent)
		finish()
	}

	override fun onStart() {
		super.onStart()
		viewModel.start()
	}

	override fun onStop() {
		super.onStop()
		viewModel.error.value = null
	}
}
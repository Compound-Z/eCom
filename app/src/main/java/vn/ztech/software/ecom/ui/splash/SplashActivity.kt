package vn.ztech.software.ecom.ui.splash

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import org.koin.androidx.viewmodel.ext.android.viewModel
import vn.ztech.software.ecom.R
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
			showErrorDialog(it)
		})
		viewModel.page.observe(this) { page: ISplashUseCase.PAGE ->
			when (page) {
				ISplashUseCase.PAGE.LOGIN,
				ISplashUseCase.PAGE.SIGNUP -> {
					val intent = Intent(this@SplashActivity, LoginSignupActivity::class.java)
					intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
					intent.putExtra("PAGE", page)
					startActivity(intent)
					finish()
				}
				ISplashUseCase.PAGE.MAIN -> {
//						if (viewModel.checkNeedToRefreshToken()) {
//							viewModel.getToken()
//						} else {
//							if (viewModel.getAccount()) {
					startActivity(
						Intent(
							this@SplashActivity,
							MainActivity::class.java
						)
					)
//								finish()
//							}
//						}
				}
			}
		}

//		viewModel.tokenResponse.observe(this, Observer {
//			it ?: return@Observer
//			if (it.access_token != "") {
//				if (viewModel.checkAdditional()) {
//					val url =  "${BuildConfig.MYLIXIL_URL}${BuildConfig.JANRAIN_CLIENT_ID}/forms?return_url=${BuildConfig.REDIRECT_URI}"
//					val builder = CustomTabsIntent.Builder()
//					val customTabsIntent = builder.build()
//					customTabsIntent.launchUrl(this, Uri.parse(url))
//				} else {
//					if (viewModel.getAccount()) {
//						startActivity(
//							Intent(
//								this@SplashActivity,
//								MainActivity::class.java
//							)
//						)
//						finish()
//					}
//				}
//			} else {
//				val intent = Intent(this@SplashActivity, LoginActivity::class.java)
//				intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
//				startActivity(intent)
//				finish()
//			}
//		})


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
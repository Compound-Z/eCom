package vn.ztech.software.ecom.ui.auth.otp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import vn.ztech.software.ecom.R
import vn.ztech.software.ecom.databinding.ActivityOtpBinding
import vn.ztech.software.ecom.model.UserData
import vn.ztech.software.ecom.ui.auth.LoginSignupActivity
import vn.ztech.software.ecom.ui.splash.ISplashUseCase
import vn.ztech.software.ecom.util.CustomError
import vn.ztech.software.ecom.util.extension.showErrorDialog
import org.koin.androidx.viewmodel.ext.android.viewModel

class OtpActivity : AppCompatActivity() {
	private lateinit var binding: ActivityOtpBinding

	private val viewModel: OtpViewModel by viewModel()

	private lateinit var fromWhere: String
	private var userData: UserData? = null
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		binding = ActivityOtpBinding.inflate(layoutInflater)
		setContentView(binding.root)
		Log.d("ERROR:","OtpActivity onCreate")
		userData = intent.getParcelableExtra("USER_DATA")
		fromWhere = intent.getStringExtra("from").toString()
		if (userData == null) {
			/**if this type of error happen, return back to SignUpActivity*/
			showErrorDialog(CustomError()) { _, _ ->
				finish()
			}
		}else{
			setViews()
			setObservers()
		}
	}


	private fun setObservers() {
		viewModel.otpStatus.observe(this) {
			when (it.message) {
				"pending" ->  {
					val contextView = binding.loaderLayout.loaderCard
					Snackbar.make(contextView, R.string.otp_verify_failed, Snackbar.LENGTH_INDEFINITE).show()
				}
				"approved" -> {
					val logInIntent = Intent(this, LoginSignupActivity::class.java)
					logInIntent.putExtra("PAGE", ISplashUseCase.PAGE.LOGIN)
					logInIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
						.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
						.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
					startActivity(logInIntent)
					finish()
				}
			}
		}

		viewModel.error.observe(this){
			it?.let {
				showErrorDialog(it)
			}
		}
	}

	private fun setViews() {
		binding.otpVerifyError.visibility = View.GONE
		binding.otpVerifyBtn.setOnClickListener {
			onVerify()
		}
	}

	private fun onVerify() {
		val otp = binding.otpOtpEditText.text.toString()
		viewModel.verifyOTP(userData?.phoneNumber!!, otp)

	}
}
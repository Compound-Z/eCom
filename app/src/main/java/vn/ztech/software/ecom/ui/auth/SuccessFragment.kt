package vn.ztech.software.ecom.ui.success

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import vn.ztech.software.ecom.R
import vn.ztech.software.ecom.databinding.FragmentSignupSuccessBinding
import vn.ztech.software.ecom.ui.auth.LoginSignupActivity
import vn.ztech.software.ecom.ui.splash.ISplashUseCase

interface SignUpSuccessFragmentListener{
	fun onDialogDismiss()
}
class SignUpSuccessFragment(val listener: SignUpSuccessFragmentListener) : DialogFragment() {

	private lateinit var binding: FragmentSignupSuccessBinding

	override fun onCreateView(
		inflater: LayoutInflater,
		container: ViewGroup?,
		savedInstanceState: Bundle?
	): View? {
		binding = FragmentSignupSuccessBinding.inflate(layoutInflater)
		return binding.root
	}

	override fun onStart() {
		super.onStart()
		/**set full screen*/
		val params = dialog?.window?.attributes
		params?.width = WindowManager.LayoutParams.MATCH_PARENT
		params?.height = WindowManager.LayoutParams.MATCH_PARENT
		dialog?.window?.attributes = params
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		binding.redirectHomeTimerTv.text =
			getString(R.string.redirect_login_timer_text, "5")
		countDownTimer.start()

		binding.backToHomeBtn.setOnClickListener {
			countDownTimer.cancel()
			listener.onDialogDismiss()

		}
	}

	private val countDownTimer = object : CountDownTimer(5000, 1000) {
		override fun onTick(millisUntilFinished: Long) {
			Log.d("SUCCESS","onTick")
			val sec = millisUntilFinished / 1000
			binding.redirectHomeTimerTv.text =
				getString(R.string.redirect_login_timer_text, sec.toString())
		}

		override fun onFinish() {
			Log.d("SUCCESS","onFinish")
			listener.onDialogDismiss()
		}
	}
}
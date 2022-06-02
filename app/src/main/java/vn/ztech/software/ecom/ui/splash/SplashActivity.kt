package vn.ztech.software.ecom.ui.splash

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import vn.ztech.software.ecom.R
import org.koin.androidx.viewmodel.ext.android.viewModel
import vn.ztech.software.ecom.util.extension.showErrorDialog

class SplashActivity : AppCompatActivity() {

	private val viewModel: SplashViewModel by viewModel()

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_splash)

		viewModel.error.observe(this, Observer {
			it ?: return@Observer
			showErrorDialog(it)
		})
	}
}
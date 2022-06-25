package vn.ztech.software.ecom.ui.account.logout

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import vn.ztech.software.ecom.R
import vn.ztech.software.ecom.databinding.FragmentAccountBinding
import org.koin.androidx.viewmodel.ext.android.viewModel
import vn.ztech.software.ecom.databinding.FragmentLoginBinding
import vn.ztech.software.ecom.exception.RefreshTokenExpiredException
import vn.ztech.software.ecom.ui.BaseFragment
import vn.ztech.software.ecom.ui.auth.LoginSignupActivity
import vn.ztech.software.ecom.ui.splash.ISplashUseCase
import vn.ztech.software.ecom.util.extension.showErrorDialog

private const val TAG = "AccountFragment"

class AccountFragment : BaseFragment<FragmentAccountBinding>() {

	private val viewModel: AccountViewModel by viewModel()
	override fun setViewBinding(): FragmentAccountBinding {
		return FragmentAccountBinding.inflate(layoutInflater)
	}

	override fun setUpViews() {
		super.setUpViews()
		binding.accountTopAppBar.topAppBar.title = getString(R.string.account_fragment_title)
//		binding.accountProfileTv.setOnClickListener {
//			Log.d(TAG, "Profile Selected")
//			findNavController().navigate(R.id.action_accountFragment_to_profileFragment)
//		}
//		binding.accountOrdersTv.setOnClickListener {
//			Log.d(TAG, "Orders Selected")
//			findNavController().navigate(R.id.action_accountFragment_to_ordersFragment)
//		}
//		binding.accountAddressTv.setOnClickListener {
//			Log.d(TAG, "Address Selected")
//			findNavController().navigate(R.id.action_accountFragment_to_addressFragment)
//		}
		binding.accountSignOutTv.setOnClickListener {
			Log.d(TAG, "Sign Out Selected")
			showSignOutDialog()
		}
	}

	override fun observeView() {
		super.observeView()
		viewModel.loading.observe(viewLifecycleOwner){
			if(it){
				handleLoadingDialog(true, R.string.logging_out)
			}else{
				handleLoadingDialog(false, R.string.logging_out)
			}
		}
		viewModel.error.observe(viewLifecycleOwner){
			it ?: return@observe
			handleError(it)
		}
		viewModel.isLogOutSuccessfully.observe(viewLifecycleOwner){
			if (it){
				goLogIn()
			}
		}
	}

	private fun showSignOutDialog() {
		context?.let {
			MaterialAlertDialogBuilder(it)
				.setTitle(getString(R.string.sign_out_dialog_title_text))
				.setMessage(getString(R.string.sign_out_dialog_message_text))
				.setNegativeButton(getString(R.string.pro_cat_dialog_cancel_btn)) { dialog, _ ->
					dialog.cancel()
				}
				.setPositiveButton(getString(R.string.dialog_sign_out_btn_text)) { dialog, _ ->
					viewModel.logOut()
				}
				.show()
		}
	}

	private fun goLogIn() {
		val logInIntent = Intent(context, LoginSignupActivity::class.java)
			.putExtra("PAGE", ISplashUseCase.PAGE.LOGIN)
			.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
		startActivity(logInIntent)
	}


}
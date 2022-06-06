package vn.ztech.software.ecom.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import vn.ztech.software.ecom.R
import vn.ztech.software.ecom.ui.auth.otp.OtpActivity

abstract class BaseFragment<VBinding : ViewBinding>: Fragment() {

    protected lateinit var binding: VBinding
    protected abstract fun setViewBinding(): VBinding
    protected val focusChangeListener = MyOnFocusChangeListener()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        init()
    }
    private fun init() {
        binding = setViewBinding()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d("ERROR:","BaseFragment onVixxxxewCreated")

        setUpViews()
        observeView()
    }

    open fun setUpViews() {}

    open fun observeView() {}

    fun launchOtpActivity(from: String, extras: Bundle) {
        val intent = Intent(context, OtpActivity::class.java).putExtra(
            "from",
            from
        ).putExtras(extras)
        startActivity(intent)
    }

    fun handleLoadingDialog(show: Boolean, messageId: Int){
        val loaderLayout = activity?.findViewById<ConstraintLayout>(R.id.loader_layout)
        val loadingMessage = loaderLayout?.findViewById<TextView>(R.id.loading_message)

        loaderLayout?.visibility =if(show) View.VISIBLE else View.GONE
        loadingMessage?.text = context?.getString(messageId)
    }

}
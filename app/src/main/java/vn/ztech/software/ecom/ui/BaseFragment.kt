package vn.ztech.software.ecom.ui

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding

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
        setUpViews()
        observeView()
    }

    open fun setUpViews() {}

    open fun observeView() {}

    //todo: this will be enable later
//    fun launchOtpActivity(from: String, extras: Bundle) {
//        val intent = Intent(context, OtpActivity::class.java).putExtra(
//            "from",
//            from
//        ).putExtras(extras)
//        startActivity(intent)
//    }

}
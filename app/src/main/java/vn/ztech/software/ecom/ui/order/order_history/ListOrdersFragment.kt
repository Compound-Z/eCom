package vn.ztech.software.ecom.ui.order.order_history

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import vn.ztech.software.ecom.R
import vn.ztech.software.ecom.databinding.FragmentListOrderBinding
import vn.ztech.software.ecom.ui.BaseFragment
import org.koin.androidx.viewmodel.ext.android.viewModel
import vn.ztech.software.ecom.model.Order

const val TAG = "ListOrdersFragment"
class ListOrdersFragment() : BaseFragment<FragmentListOrderBinding>() {
    private val viewModel: ListOrdersViewModel by viewModel()
    lateinit var statusFilter: String
    lateinit var adapter: ListOrderAdapter
    interface OnClickListener{
        fun onClickButtonViewDetails(orderId: String)
    }
    private lateinit var callBack: OnClickListener
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.takeIf { it.containsKey("statusFilter") }?.apply {
            Log.d(TAG, getString("statusFilter").toString())
            if(viewModel.orders.value == null) viewModel.getOrders(getString("statusFilter"))
            else viewModel.getOrders(getString("statusFilter"), isLoadingEnabled = false)
            statusFilter = getString("statusFilter").toString()
            viewModel.statusFilter.value = statusFilter
        }
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    override fun setViewBinding(): FragmentListOrderBinding {
        return FragmentListOrderBinding.inflate(layoutInflater)
    }

    override fun setUpViews() {
        super.setUpViews()
        setupAdapter(viewModel.orders.value)

    }
    private fun setupAdapter(_orders: List<Order>?){
        val orders = _orders?: emptyList()
        adapter = ListOrderAdapter(requireContext(), orders, object : ListOrderAdapter.OnClickListener{
            override fun onClickButtonViewDetail(order: Order) {
                if(order.status == "CONFIRMED"){
                    /**if the status of the order is CONFIRMED, this button is for the customer to Mark this order as received when they have actually received the order*/
                    MaterialAlertDialogBuilder(requireContext())
                        .setTitle("Mark order as Received")
                        .setMessage("By proceeding this action, you will confirm that you this order have been delivered successfully to you!")
                        .setNeutralButton(getString(R.string.no)) { dialog, _ ->
                            dialog.cancel()
                        }
                        .setPositiveButton(getString(R.string.yes)) { dialog, _ ->
                            viewModel.receivedOrder(order._id)
                            dialog.cancel()
                        }
                        .show()
                }else{
                    callBack.onClickButtonViewDetails(order._id)
                }
            }

            override fun onClick(order: Order) {
                callBack.onClickButtonViewDetails(order._id)
            }

            override fun onCopyClipBoardClicked(orderId: String) {
                copyToClipBoard(orderId)
            }
        })
        binding.listOrders.adapter = adapter
    }

    private fun copyToClipBoard(orderId: String) {
        val clipboard = requireContext().getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clip: ClipData = ClipData.newPlainText("orderId", orderId)
        clipboard.setPrimaryClip(clip)
        toastCenter("Copied $orderId")
    }
    fun toastCenter(message: String){
        Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).apply {
            setGravity(Gravity.CENTER, 0, 0)
        }.show()
    }
    override fun observeView() {
        super.observeView()
        viewModel.loading.observe(viewLifecycleOwner){
            when (it) {
                true -> {
                    binding.loaderLayout.loaderFrameLayout.visibility = View.VISIBLE
                    binding.loaderLayout.circularLoader.showAnimationBehavior
                }
                false -> {
                    binding.loaderLayout.circularLoader.hideAnimationBehavior
                    binding.loaderLayout.loaderFrameLayout.visibility = View.GONE
                }
            }
        }
        viewModel.orders.observe(viewLifecycleOwner){
            it?.let {
                binding.listOrders.adapter?.apply {
                    adapter.orders = it
                    notifyDataSetChanged()
                }
            }
        }
        viewModel.order.observe(viewLifecycleOwner){
            it?.let {
                viewModel.getOrders(viewModel.statusFilter.value)
            }
        }
        viewModel.error.observe(viewLifecycleOwner){
            it?.let {
                handleError(it)
            }
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        callBack = parentFragment as OnClickListener
    }
    override fun onResume() {
        super.onResume()
        viewModel.getOrders(viewModel.statusFilter.value)
    }
    override fun onStop() {
        super.onStop()
        viewModel.clearErrors()
    }
}
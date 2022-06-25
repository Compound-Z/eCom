package vn.ztech.software.ecom.ui.order.order_history

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import vn.ztech.software.ecom.R
import vn.ztech.software.ecom.databinding.FragmentListOrderBinding
import vn.ztech.software.ecom.ui.BaseFragment
import org.koin.androidx.viewmodel.ext.android.viewModel
import vn.ztech.software.ecom.model.Order

const val TAG = "ListOrdersFragment"
class ListOrdersFragment(val onClickListener: OnClickListener) : BaseFragment<FragmentListOrderBinding>() {
    private val viewModel: ListOrdersViewModel by viewModel()
    lateinit var statusFilter: String
    lateinit var adapter: ListOrderAdapter
    interface OnClickListener{
        fun onClickButtonViewDetails(orderId: String)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.takeIf { it.containsKey("statusFilter") }?.apply {
            Log.d(TAG, getString("statusFilter").toString())
            viewModel.getOrders(getString("statusFilter"))
            statusFilter = getString("statusFilter").toString()
        }
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
                onClickListener.onClickButtonViewDetails(order._id)
            }
        })
        binding.listOrders.adapter = adapter
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
        viewModel.error.observe(viewLifecycleOwner){
            it?.let {
                handleError(it)
            }
        }
    }
}
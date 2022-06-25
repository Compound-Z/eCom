package vn.ztech.software.ecom.ui.order.order_history

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayoutMediator
import vn.ztech.software.ecom.R
import vn.ztech.software.ecom.common.Constants
import vn.ztech.software.ecom.databinding.FragmentListOrderBinding
import vn.ztech.software.ecom.databinding.FragmentOrderHistoryBinding
import vn.ztech.software.ecom.ui.BaseFragment


class OrderHistoryFragment : BaseFragment<FragmentOrderHistoryBinding>() {
    private lateinit var listOrdersFragmentAdapter: ListOrdersFragmentAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        listOrdersFragmentAdapter = ListOrdersFragmentAdapter(this@OrderHistoryFragment, object : ListOrdersFragment.OnClickListener{
            override fun onClickButtonViewDetails(orderId: String) {
                findNavController().navigate(
                    R.id.action_orderHistoryFragment_to_orderDetailsFragment,
                    bundleOf(
                        "fromWhere" to "OrderHistoryFragment",
                        "orderId" to orderId,
                    )
                )
            }

        })
        binding.pager.adapter = listOrdersFragmentAdapter
        TabLayoutMediator(binding.tabLayout, binding.pager) {tab, pos->
            if(pos==0) tab.text =  "All"
            else tab.text =  Constants.OrderStatus[pos]
        }.attach()
    }

    override fun setViewBinding(): FragmentOrderHistoryBinding {
        return FragmentOrderHistoryBinding.inflate(layoutInflater)
    }

    override fun setUpViews() {
        super.setUpViews()
        binding.ordersAppBar.topAppBar.title = getString(R.string.order_details_fragment_title)

    }

    override fun observeView() {
        super.observeView()
    }

    class ListOrdersFragmentAdapter(fragment: OrderHistoryFragment, val onClickListener: ListOrdersFragment.OnClickListener) : FragmentStateAdapter(fragment) {

        override fun getItemCount(): Int {
            return Constants.OrderStatus.size
        }

        override fun createFragment(position: Int): Fragment {
            val fragment = ListOrdersFragment(onClickListener = onClickListener)
            fragment.arguments = Bundle().apply {
                putString("statusFilter", Constants.OrderStatus[position])
            }
            return fragment
        }
    }

}
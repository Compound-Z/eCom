package vn.ztech.software.ecom.ui.order

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import vn.ztech.software.ecom.R
import vn.ztech.software.ecom.api.response.CartProductResponse
import vn.ztech.software.ecom.databinding.FragmentOrderBinding
import vn.ztech.software.ecom.ui.BaseFragment
private const val TAG = "OrdersFragment"
class OrderFragment : BaseFragment<FragmentOrderBinding>() {

    private lateinit var productsAdapter: OrderProductsAdapter
    private lateinit var products: List<CartProductResponse>

    override fun setViewBinding(): FragmentOrderBinding {
        return FragmentOrderBinding.inflate(layoutInflater)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //get list products from cart
        val bundledProducts = arguments?.getParcelableArrayList<CartProductResponse>("products") as ArrayList<CartProductResponse>
        bundledProducts.let {
            products = bundledProducts.toList()
            if (context != null) {
                setProductsAdapter(products)
                binding.orderDetailsProRecyclerView.adapter = productsAdapter
            }
        }
            Log.d(TAG, products.size.toString())
    }


    override fun setUpViews() {
        super.setUpViews()
        binding.orderDetailAppBar.topAppBar.title = getString(R.string.order_details_fragment_title)
        binding.orderDetailAppBar.topAppBar.setNavigationOnClickListener { findNavController().navigateUp() }
        binding.loaderLayout.loaderFrameLayout.visibility = View.GONE
//        binding.orderDetailsConstraintGroup.visibility = View.GONE


    }

    override fun observeView() {
        super.observeView()
    }

    private fun setProductsAdapter(products: List<CartProductResponse>) {
        productsAdapter = OrderProductsAdapter(requireContext(), products)
    }
}
package vn.ztech.software.ecom.ui.order

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import org.koin.androidx.viewmodel.ext.android.viewModel
import vn.ztech.software.ecom.R
import vn.ztech.software.ecom.api.response.CartProductResponse
import vn.ztech.software.ecom.databinding.FragmentOrderBinding
import vn.ztech.software.ecom.model.Address
import vn.ztech.software.ecom.model.AddressItem
import vn.ztech.software.ecom.ui.BaseFragment
import vn.ztech.software.ecom.ui.address.AddressViewModel
import vn.ztech.software.ecom.ui.cart.CartViewModel
import vn.ztech.software.ecom.util.extension.showErrorDialog

private const val TAG = "OrdersFragment"
class OrderFragment : BaseFragment<FragmentOrderBinding>() {

    private lateinit var productsAdapter: OrderProductsAdapter
    private lateinit var products: List<CartProductResponse>

    private val addressViewModel: AddressViewModel by viewModel()
    private val cartViewModel: CartViewModel by viewModel()
    override fun setViewBinding(): FragmentOrderBinding {
        return FragmentOrderBinding.inflate(layoutInflater)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //get list products from cart
        xxxxxxxxxxxxxxxx how to have products when navigate from address?
        val bundledProducts = arguments?.getParcelableArrayList<CartProductResponse?>("products") as ArrayList<CartProductResponse>
        val bundledAddressItem = arguments?.getParcelable<AddressItem?>("ADDRESS_ITEM")
        bundledProducts.let {
            products = bundledProducts.toList()
            if (context != null) {
                setProductsAdapter(products)
                binding.orderDetailsProRecyclerView.adapter = productsAdapter
            }
        }
        bundledAddressItem?.let {
            addressViewModel.currentSelectedAddressItem.value = it
        }

        //get list address
        addressViewModel.getAddresses()
    }


    override fun setUpViews() {
        super.setUpViews()
        binding.orderDetailAppBar.topAppBar.title = getString(R.string.order_details_fragment_title)
        binding.orderDetailAppBar.topAppBar.setNavigationOnClickListener { findNavController().navigateUp() }
        binding.loaderLayout.loaderFrameLayout.visibility = View.GONE
        binding.segmentAddress.addressCard.setOnClickListener{
            navigateToAddressFragment()
        }
//        binding.orderDetailsConstraintGroup.visibility = View.GONE


    }

    private fun navigateToAddressFragment() {
        findNavController().navigate(R.id.action_orderFragment_to_addressFragment)
    }

    override fun observeView() {
        super.observeView()
        addressViewModel.loading.observe(viewLifecycleOwner){
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
        addressViewModel.addresses.observe(viewLifecycleOwner){
            updateSegmentAddress(it)
        }

        addressViewModel.currentSelectedAddressItem.observe(viewLifecycleOwner){
            updateSegmentAddress(it)
        }
        addressViewModel.error.observe(viewLifecycleOwner){
            it?.let {
                showErrorDialog(it)
            }
        }

    }

    private fun updateSegmentAddress(it: Address?) {
        it?.let {
            if (it.addresses.isEmpty()){
                //show remind note
                binding.segmentAddress.tvNoAddress.visibility = View.VISIBLE
            }else{
                binding.segmentAddress.tvNoAddress.visibility = View.GONE
                val defaultAddress = it.getDefaultAddress()
                defaultAddress?.let {
                    binding.segmentAddress.tvNameAndPhoneNumber.text = "${defaultAddress.receiverName} | ${defaultAddress.receiverPhoneNumber}"
                    binding.segmentAddress.tvDetailedAddress.text = defaultAddress.detailedAddress
                }
            }
        }
    }
    private fun updateSegmentAddress(addressItem: AddressItem?) {
        addressItem?.let {
                binding.segmentAddress.tvNoAddress.visibility = View.GONE
                binding.segmentAddress.tvNameAndPhoneNumber.text = "${addressItem.receiverName} | ${addressItem.receiverPhoneNumber}"
                binding.segmentAddress.tvDetailedAddress.text = addressItem.detailedAddress
        }
    }

    private fun setProductsAdapter(products: List<CartProductResponse>) {
        productsAdapter = OrderProductsAdapter(requireContext(), products)
    }
}

private fun Address.getDefaultAddress(): AddressItem? {
    val defaultAddressId = this.defaultAddressId
    this.addresses.forEach { addressItem ->
        if (addressItem._id.equals(defaultAddressId)){
            return addressItem
        }
    }
    return null
}


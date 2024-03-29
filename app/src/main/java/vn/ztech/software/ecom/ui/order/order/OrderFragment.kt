package vn.ztech.software.ecom.ui.order.order

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import org.koin.androidx.viewmodel.ext.android.viewModel
import vn.ztech.software.ecom.R
import vn.ztech.software.ecom.api.request.GetShippingOptionsReq
import vn.ztech.software.ecom.api.response.CartProductResponse
import vn.ztech.software.ecom.api.response.ShippingOption
import vn.ztech.software.ecom.databinding.FragmentOrderBinding
import vn.ztech.software.ecom.model.Address
import vn.ztech.software.ecom.model.AddressItem
import vn.ztech.software.ecom.model.SubOrder
import vn.ztech.software.ecom.ui.BaseFragment
import vn.ztech.software.ecom.ui.address.AddressViewModel
import vn.ztech.software.ecom.ui.cart.CartViewModel
import vn.ztech.software.ecom.ui.order.OrderProductsAdapter
import vn.ztech.software.ecom.util.extension.toCartItems
import vn.ztech.software.ecom.util.extension.toCurrency
import vn.ztech.software.ecom.util.extension.toListSubOrders
import vn.ztech.software.ecom.util.extension.toOrderItems

private const val TAG = "OrdersFragment"
class OrderFragment : BaseFragment<FragmentOrderBinding>()  {

    private lateinit var subOrdersAdapter: SubOrdersAdapter
    private val addressViewModel: AddressViewModel by viewModel()
    private val cartViewModel: CartViewModel by viewModel()
    private val orderViewModel: OrderViewModel by viewModel()
    override fun setViewBinding(): FragmentOrderBinding {
        return FragmentOrderBinding.inflate(layoutInflater)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //get list products from cart
        val bundledProducts = arguments?.getParcelableArrayList<CartProductResponse>("products") as ArrayList<CartProductResponse>?
        val bundledAddressItem = arguments?.getParcelable<AddressItem?>("ADDRESS_ITEM") as AddressItem?
        if(bundledProducts != null) {
            cartViewModel.products.value = bundledProducts
        }else{
            cartViewModel.getListProductsInCart()
        }
        if(bundledAddressItem != null) {
            addressViewModel.currentSelectedAddressItem.value = bundledAddressItem
        }else{
            //get list address
            addressViewModel.getAddresses()
        }
    }


    override fun setUpViews() {
        super.setUpViews()
        binding.orderDetailAppBar.topAppBar.title = getString(R.string.order_details_fragment_title)
        binding.orderDetailAppBar.topAppBar.setNavigationOnClickListener { findNavController().navigateUp() }
        binding.loaderLayout.loaderFrameLayout.visibility = View.GONE
        binding.segmentAddress.addressCard.setOnClickListener{
            navigateToAddressFragment()
        }

        binding.layoutCalculateFee.btPlaceOrder.setOnClickListener {
            orderViewModel.createOrder(orderViewModel.products.value, orderViewModel.currentSelectedAddress.value, orderViewModel.currentSelectedShippingOption.value)
        }


    }

    private fun navigateToAddressFragment() {
        findNavController().navigate(R.id.action_orderFragment_to_addressFragment)
    }

    override fun observeView() {
        super.observeView()
        cartViewModel.loading.observe(viewLifecycleOwner){
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

        cartViewModel.products.observe(viewLifecycleOwner) { products ->
            products?.let {
                orderViewModel.products.value = products
            }
//            setProductsAdapter(products)
        }
        orderViewModel.subOrders.observe(viewLifecycleOwner) {
            it?.let {
                setSubOrdersAdapter(it)
            }
        }

        cartViewModel.error.observe(viewLifecycleOwner){
            it?.let {
                handleError(it)
            }
        }

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
            it?.let { orderViewModel.currentSelectedAddress.value = it}
        }
        addressViewModel.error.observe(viewLifecycleOwner){
            it?.let {
                handleError(it)
            }
        }

        orderViewModel.loading.observe(viewLifecycleOwner){
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
        orderViewModel.products.observe(viewLifecycleOwner){
            it?.let {
                orderViewModel.subOrders.value = it.toListSubOrders()
            }
            if(orderViewModel.checkIfCanGetShippingOptions()){
                getShippingOptions(orderViewModel.subOrders.value)
//                orderViewModel.getShippingOptions(
//                    GetShippingOptionsReq(
//                        orderViewModel.currentSelectedAddress.value?._id?:"",
//                        orderViewModel.products.value?.toCartItems()?: emptyList()
//                    )
//                )
            }
        }
        orderViewModel.currentSelectedAddress.observe(viewLifecycleOwner){
            if(orderViewModel.checkIfCanGetShippingOptions()){
                getShippingOptions(orderViewModel.subOrders.value)
//                orderViewModel.getShippingOptions(
//                    GetShippingOptionsReq(
//                        orderViewModel.currentSelectedAddress.value?._id?:"",
//                        orderViewModel.products.value?.toCartItems()?: emptyList()
//                    )
//                )
            }
        }

        //todo: iplm
//        orderViewModel.loadingShipping.observe(viewLifecycleOwner){
//            if(it){
//                binding.layoutListShippingOptions.loaderLayout.loaderFrameLayout.visibility = View.VISIBLE
//                binding.loaderLayout.circularLoader.showAnimationBehavior
//
//                //calculate cost area
//                binding.layoutCalculateFee.loaderLayout.loaderFrameLayout.visibility = View.VISIBLE
//                binding.layoutCalculateFee.loaderLayout.circularLoader.showAnimationBehavior
//            }else{
//                binding.layoutListShippingOptions.loaderLayout.loaderFrameLayout.visibility = View.GONE
//                binding.loaderLayout.circularLoader.hideAnimationBehavior
//
//                //calculate cost area
//                binding.layoutCalculateFee.loaderLayout.loaderFrameLayout.visibility = View.GONE
//                binding.layoutCalculateFee.loaderLayout.circularLoader.hideAnimationBehavior
//            }
//        }
        orderViewModel.loadShippingOptionsDone.observe(viewLifecycleOwner){
            it?.let {
                binding.recyclerViewSubOrder.adapter?.notifyDataSetChanged()
//                setShippingOptionsAdapter(it)
            }
        }

        orderViewModel.orderCost.observe(viewLifecycleOwner){
            it?.apply {
                if (productsCost>0) binding.layoutCalculateFee.tvProductsCost.text = productsCost.toCurrency()
                if (shippingFee>0) binding.layoutCalculateFee.tvShippingFee.text = shippingFee.toCurrency()
                if (totalCost>0) binding.layoutCalculateFee.tvTotalCost.text = totalCost.toCurrency()
                binding.layoutCalculateFee.btPlaceOrder.isEnabled = true
            }
            if (it == null){
                binding.layoutCalculateFee.btPlaceOrder.isEnabled = false
            }
        }
        orderViewModel.createdOrder.observe(viewLifecycleOwner){
            it?.let {
                findNavController().navigate(R.id.action_orderFragment_to_orderSuccessFragment,
                bundleOf("orderDetails" to it))
            }
        }
        orderViewModel.error.observe(viewLifecycleOwner){
            it?.let {
                handleError(it)
            }
        }
    }

    private fun getShippingOptions(subOrders: List<SubOrder>?) {
        subOrders?.forEach {
            orderViewModel.getShippingOptions(
                    it.shop._id,
                    GetShippingOptionsReq(
                        orderViewModel.currentSelectedAddress.value?._id?:"",
                        it.items.toMutableList().toCartItems()
                    )
                )
        }
    }

    private fun setShippingOptionsAdapter(it: List<ShippingOption>) {
        //todo:
//        shippingOptionsAdapter = ShippingOptionsAdapter(requireContext(), it)
//        shippingOptionsAdapter.onClickListener = object : ShippingOptionsAdapter.OnClickListener {
//            override fun onClick(shippingOption: ShippingOption) {
//                orderViewModel.currentSelectedShippingOption.value = shippingOption
//                orderViewModel.calculateCost()
//            }
//        }
//        binding.layoutListShippingOptions.recyclerShippingOptions.adapter = shippingOptionsAdapter
    }

    private fun updateSegmentAddress(it: Address?) {
        if (it == null){
            //show remind note
            binding.segmentAddress.tvNoAddress.visibility = View.VISIBLE
        }else {
            if (it.addresses.isNullOrEmpty()){
                //show remind note
                binding.segmentAddress.tvNoAddress.visibility = View.VISIBLE
            }else{
                binding.segmentAddress.tvNoAddress.visibility = View.GONE
                val defaultAddress = it.getDefaultAddress()
                defaultAddress?.let {
                    binding.segmentAddress.tvNameAndPhoneNumber.text = "${defaultAddress.receiverName} | ${defaultAddress.receiverPhoneNumber}"
                    binding.segmentAddress.tvDetailedAddress.text = defaultAddress.detailedAddress
                    addressViewModel.currentSelectedAddressItem.value = defaultAddress
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

    private fun setSubOrdersAdapter(subOrders: List<SubOrder>) {
        subOrdersAdapter = SubOrdersAdapter(requireContext(), subOrders)
        subOrdersAdapter.onClickListener = object : SubOrdersAdapter.OnClickListener{
            override fun onSelectShippingOption(shopId: String, serviceId: Int) {
                orderViewModel.setSelectedShippingOptionToSubOrder(shopId, serviceId)
                orderViewModel.calculateCost()
                Log.d("SubOrders", orderViewModel.subOrders.value.toString())
            }

        }
        binding.recyclerViewSubOrder.adapter = subOrdersAdapter
    }

    override fun onStop() {
        super.onStop()
        orderViewModel.clearErrors()
        addressViewModel.clearErrors()
        cartViewModel.clearErrors()
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


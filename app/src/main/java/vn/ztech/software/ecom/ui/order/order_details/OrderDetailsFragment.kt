package vn.ztech.software.ecom.ui.order.order_details

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.core.os.bundleOf
import org.koin.androidx.viewmodel.ext.android.viewModel
import androidx.navigation.fragment.findNavController
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.layout_shipping_card.view.*
import vn.ztech.software.ecom.R
import vn.ztech.software.ecom.databinding.FragmentOrderDetailsBinding
import vn.ztech.software.ecom.model.*
import vn.ztech.software.ecom.ui.BaseFragment
import vn.ztech.software.ecom.ui.main.MainActivity
import vn.ztech.software.ecom.ui.order.OrderProductsAdapter
import vn.ztech.software.ecom.util.CustomError
import vn.ztech.software.ecom.util.errorMessage
import vn.ztech.software.ecom.util.extension.getFullAddress
import vn.ztech.software.ecom.util.extension.toCartProductResponses
import vn.ztech.software.ecom.util.extension.toCurrency
import vn.ztech.software.ecom.util.extension.toDateTimeString

const val TAG = "OrderDetailsFragment"
class OrderDetailsFragment : BaseFragment<FragmentOrderDetailsBinding>() {

    private val viewModel: OrderDetailsViewModel by viewModel()
	private lateinit var productsAdapter: OrderProductsAdapter
    var isLaunchedFromNoti = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val fromWhere = arguments?.getString("fromWhere")?:"OrdersFragment"
        when(fromWhere){
            "OrderHistoryFragment"->{
                arguments?.takeIf { it.containsKey("orderId") }?.apply {
                    viewModel.getOrderDetails(getString("orderId"))
                }
            }
            "OrderSuccessFragment"->{
                /**get argument passed from OrderSuccessFragment*/
                val orderDetails = arguments?.getParcelable<OrderDetails?>("orderDetails")
                if (orderDetails == null){
                    errorMessage(CustomError(customMessage = "System error: can not find orderDetails"))
                }else{
                    viewModel.orderDetails.value = orderDetails
                }
            }
            "MainActivity"->{
                arguments?.takeIf { it.containsKey("orderId") }?.apply {
                    viewModel.getOrderDetails(getString("orderId"))
                    isLaunchedFromNoti = true
                }
            }
            }
    }

    override fun setViewBinding(): FragmentOrderDetailsBinding {
        return FragmentOrderDetailsBinding.inflate(layoutInflater)
    }

    override fun setUpViews() {
        super.setUpViews()
        if(isLaunchedFromNoti){
            activity?.onBackPressedDispatcher?.addCallback(viewLifecycleOwner,object :
                OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    (activity as MainActivity).binding.homeBottomNavigation.selectedItemId = R.id.orderFragment
                    findNavController().navigateUp()
                }
            })
        }
        binding.orderDetailAppBar.topAppBar.title = getString(R.string.order_details_fragment_title)
		binding.orderDetailAppBar.topAppBar.setNavigationOnClickListener {
            if (isLaunchedFromNoti){
                /**this is a trick to force orderFragment to re-render, otherwise it will be blank, another solution should be research to replace this :((, but i have no time, so temporarily accept this*/
                (activity as MainActivity).binding.homeBottomNavigation.selectedItemId = R.id.accountFragment
            }
            findNavController().navigateUp()
        }
		binding.loaderLayout.loaderFrameLayout.visibility = View.GONE
//
		if (context != null) {
			setProductsAdapter(viewModel.orderDetails.value?.orderItems)
			binding.orderDetailsProRecyclerView.adapter = productsAdapter
		}
        binding.tvOrderStatus.text = viewModel.orderDetails.value?.status?:"unknown"
        setUpShippingViews(viewModel.orderDetails.value?.user, viewModel.orderDetails.value?.address, viewModel.orderDetails.value?.shopRef, viewModel.orderDetails.value?.shippingDetails)
        setUpBillingViews(viewModel.orderDetails.value?.billing, viewModel.orderDetails.value?.orderItems)
        binding.btCancelOrder.setOnClickListener {
            showCancelDialog(viewModel.orderDetails.value?._id)
        }
        binding.btRebuyOrder.setOnClickListener {
            findNavController().navigate(
                R.id.action_orderDetailsFragment_to_orderFragment,
                bundleOf("products" to viewModel.orderDetails.value?.orderItems?.toCartProductResponses(),
                "ADDRESS_ITEM" to viewModel.orderDetails.value?.address)
            )
        }
        binding.btMarkAsReceived.setOnClickListener {
            MaterialAlertDialogBuilder(requireContext())
                .setTitle("Mark order as Received")
                .setMessage("By proceeding this action, you will confirm that you this order have been delivered successfully to you!")
                .setNeutralButton(getString(R.string.no)) { dialog, _ ->
                    dialog.cancel()
                }
                .setPositiveButton(getString(R.string.yes)) { dialog, _ ->
                    viewModel.receivedOrder(viewModel.orderDetails.value?._id)
                    dialog.cancel()
                }
                .show()
        }
    }

    private fun setUpBillingViews(billing: Billing?, orderItems: List<OrderItem>?) {
        if(billing == null || orderItems == null){
            binding.layoutBilling.adCard.visibility = View.GONE
        }else{
            binding.layoutBilling.adCard.visibility = View.VISIBLE
            binding.layoutBilling.apply {
                priceItemsAmountTv.text = billing.subTotal.toCurrency()
                priceShippingAmountTv.text = billing.shippingFee.toCurrency()
                priceTotalAmountTv.text = (billing.subTotal + billing.shippingFee).toCurrency()
                tvNumberItems.text = "Items(${orderItems.size})"
            }
        }
    }

    private fun setUpShippingViews(user: UserOrder?, address: AddressItem?, shop: ShopRef?, shippingDetails: ShippingDetails?) {
        if (user == null || address == null){
            binding.orderDetailsShippingAddLayout.shippingCard.visibility = View.GONE
        }else{
            binding.orderDetailsShippingAddLayout.shippingCard.visibility = View.VISIBLE
            binding.orderDetailsShippingAddLayout.apply {
                tvReceiver.text = address.receiverName
                tvPhoneNumber.text = address.receiverPhoneNumber
                tvAddress.text = address.getFullAddress()

                shop?.addressItem?.let {
                    tvFromAddresContent.text = it.getFullAddress()
                }

                shippingDetails?.let {
                    if (!it.expectedDeliveryTime.isNullOrEmpty()){
                        groupShippingDetails.visibility = View.VISIBLE
                        tvExpectedDeliveryTimeContent.text = it.expectedDeliveryTime?.toDateTimeString()
                        tvShippingOrderCodeContent.text = it.shippingOrderCode
                        tvShippingOrderCodeContent.setOnClickListener { view->
                            copyToClipBoard(it.shippingOrderCode?:"")
                        }
                    }
                }

            }
        }
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
        viewModel.orderDetails.observe(viewLifecycleOwner){
            it?.let {
                  updateOrderStatusUI(it.status)
                  setUpViews()
            }
        }
        viewModel.cancelOrderStatus.observe(viewLifecycleOwner){
            it?.let {
                if (it){
                    Snackbar.make(binding.root, "Cancel order successfully :(", Snackbar.LENGTH_LONG).show()
                }else{
                    Snackbar.make(binding.root, "Cancel order failed :D", Snackbar.LENGTH_LONG).show()
                }
            }
        }
        viewModel.error.observe(viewLifecycleOwner){
            it?.let {
                handleError(it)
            }
        }
    }

    private fun updateOrderStatusUI(status: String) {
        binding.apply {
            tvOrderStatus.text = status
            when(status){
                "PENDING"->{
                    tvOrderStatus.setTextColor(Color.BLUE)
                    tvOrderStatus.background = resources.getDrawable(R.drawable.rounded_bg_blue)
                    btCancelOrder.visibility = View.VISIBLE
                    btRebuyOrder.visibility = View.GONE
                    btMarkAsReceived.visibility = View.GONE
                }
                "CANCELED"->{
                    tvOrderStatus.setTextColor(Color.RED)
                    tvOrderStatus.background = resources.getDrawable(R.drawable.rounded_bg_red)
                    btCancelOrder.visibility = View.GONE
                    btRebuyOrder.visibility = View.VISIBLE
                    btMarkAsReceived.visibility = View.GONE

                }
                "PROCESSING"->{
                    tvOrderStatus.setTextColor(resources.getColor(R.color.dark_yellow))
                    tvOrderStatus.background = resources.getDrawable(R.drawable.rounded_bg_yellow)
                    btCancelOrder.visibility = View.VISIBLE
                    btRebuyOrder.visibility = View.GONE
                    btMarkAsReceived.visibility = View.GONE

                }
                "CONFIRMED"->{
                    tvOrderStatus.setTextColor(Color.GREEN)
                    tvOrderStatus.background = resources.getDrawable(R.drawable.rounded_bg_green)
                    btCancelOrder.visibility = View.GONE
                    btRebuyOrder.visibility = View.GONE
                    btMarkAsReceived.visibility = View.VISIBLE
                }
                "RECEIVED"->{
                    tvOrderStatus.setTextColor(Color.GREEN)
                    tvOrderStatus.background = resources.getDrawable(R.drawable.rounded_bg_green)
                    btCancelOrder.visibility = View.GONE
                    btRebuyOrder.visibility = View.VISIBLE
                    btMarkAsReceived.visibility = View.GONE

                }
            }
        }
    }

    private fun setProductsAdapter(itemsList: List<OrderItem>?) {
		val items = itemsList ?: emptyList()
		productsAdapter = OrderProductsAdapter(requireContext(), items)
        binding.orderDetailsProRecyclerView.adapter = productsAdapter
	}
    private fun showCancelDialog(orderId: String?) {
        context?.let {
            MaterialAlertDialogBuilder(it)
                .setTitle(getString(R.string.delete_dialog_title_text))
                .setMessage(getString(R.string.canceled_order_message_text))
                .setNeutralButton(getString(R.string.no)) { dialog, _ ->
                    dialog.cancel()
                }
                .setPositiveButton(getString(R.string.yes)) { dialog, _ ->
                    viewModel.cancelOrder(orderId)
                    dialog.cancel()
                }
                .show()
        }
    }

    override fun onStop() {
        super.onStop()
        viewModel.clearErrors()
    }
//	private lateinit var orderId: String
//	override fun onCreateView(
//		inflater: LayoutInflater,
//		container: ViewGroup?,
//		savedInstanceState: Bundle?
//	): View? {
//		binding = FragmentOrderDetailsBinding.inflate(layoutInflater)
//		orderId = arguments?.getString("orderId").toString()
//		viewModel.getOrderDetailsByOrderId(orderId)
//		setViews()
//		setObservers()
//		return binding.root
//	}
//
//	private fun setViews() {
//		binding.orderDetailAppBar.topAppBar.title = getString(R.string.order_details_fragment_title)
//		binding.orderDetailAppBar.topAppBar.setNavigationOnClickListener { findNavController().navigateUp() }
//		binding.loaderLayout.loaderFrameLayout.visibility = View.GONE
//		binding.orderDetailsConstraintGroup.visibility = View.GONE
//
//		if (context != null) {
//			setProductsAdapter(viewModel.selectedOrder.value?.items)
//			binding.orderDetailsProRecyclerView.adapter = productsAdapter
//		}
//	}
//
//	private fun setObservers() {
//		viewModel.storeDataStatus.observe(viewLifecycleOwner) { status ->
//			when (status) {
//				StoreDataStatus.LOADING -> {
//					binding.loaderLayout.loaderFrameLayout.visibility = View.VISIBLE
//					binding.loaderLayout.circularLoader.showAnimationBehavior
//					binding.orderDetailsConstraintGroup.visibility = View.GONE
//				}
//				else -> {
//					binding.loaderLayout.circularLoader.hideAnimationBehavior
//					binding.loaderLayout.loaderFrameLayout.visibility = View.GONE
//				}
//			}
//		}
//		viewModel.selectedOrder.observe(viewLifecycleOwner) { orderData ->
//			if (orderData != null) {
//				binding.orderDetailsConstraintGroup.visibility = View.VISIBLE
//				setAllViews(orderData)
//				val items = orderData.items
//				val likeList = viewModel.userLikes.value ?: emptyList()
//				val prosList = viewModel.orderProducts.value ?: emptyList()
//				productsAdapter.apply {
//					data = items
//					proList = prosList
//					likesList = likeList
//				}
//				binding.orderDetailsProRecyclerView.adapter = productsAdapter
//				binding.orderDetailsProRecyclerView.adapter?.notifyDataSetChanged()
//			} else {
//				binding.loaderLayout.loaderFrameLayout.visibility = View.VISIBLE
//				binding.loaderLayout.circularLoader.showAnimationBehavior
//				binding.orderDetailsConstraintGroup.visibility = View.GONE
//			}
//		}
//	}
//
//	private fun setAllViews(orderData: UserData.OrderItem) {
//		Log.d("OrderDetail", "set all views called")
//		if (viewModel.isUserASeller) {
//			binding.orderChangeStatusBtn.visibility = View.VISIBLE
//			binding.orderChangeStatusBtn.setOnClickListener {
//				val statusString = orderData.status.split(" ")[0]
//				val pos = OrderStatus.values().map { it.name }.indexOf(statusString)
//				showDialogWithItems(pos, orderData.orderId)
//			}
//		} else {
//			binding.orderChangeStatusBtn.visibility = View.GONE
//		}
//		val calendar = Calendar.getInstance()
//		calendar.time = orderData.orderDate
//		binding.orderDetailsShippingAddLayout.shipDateValueTv.text = getString(
//			R.string.order_date_text,
//			Month.values()[(calendar.get(Calendar.MONTH))].name,
//			calendar.get(Calendar.DAY_OF_MONTH).toString(),
//			calendar.get(Calendar.YEAR).toString()
//		)
//		binding.orderDetailsShippingAddLayout.shipAddValueTv.text =
//			getCompleteAddress(orderData.deliveryAddress)
//		binding.orderDetailsShippingAddLayout.shipCurrStatusValueTv.text = orderData.status
//
//		setPriceCard(orderData)
//	}
//
//	private fun setPriceCard(orderData: UserData.OrderItem) {
//		binding.orderDetailsPaymentLayout.priceItemsLabelTv.text = getString(
//			R.string.price_card_items_string,
//			getItemsCount(orderData.items).toString()
//		)
//		val itemsPriceTotal = getItemsPriceTotal(orderData.itemsPrices, orderData.items)
//		binding.orderDetailsPaymentLayout.priceItemsAmountTv.text =
//			getString(
//				R.string.price_text,
//				itemsPriceTotal.toString()
//			)
//		binding.orderDetailsPaymentLayout.priceShippingAmountTv.text =
//			getString(R.string.price_text, "0")
//		binding.orderDetailsPaymentLayout.priceChargesAmountTv.text =
//			getString(R.string.price_text, "0")
//		binding.orderDetailsPaymentLayout.priceTotalAmountTv.text =
//			getString(R.string.price_text, (itemsPriceTotal + orderData.shippingCharges).toString())
//	}
//

//
//	private fun showDialogWithItems(checkedOption: Int = 0, orderId: String) {
//		val categoryItems: Array<String> = OrderStatus.values().map { it.name }.toTypedArray()
//		var checkedItem = checkedOption
//		context?.let {
//			MaterialAlertDialogBuilder(it)
//				.setTitle(getString(R.string.status_dialog_title))
//				.setSingleChoiceItems(categoryItems, checkedItem) { _, which ->
//					checkedItem = which
//				}
//				.setNegativeButton(getString(R.string.pro_cat_dialog_cancel_btn)) { dialog, _ ->
//					dialog.cancel()
//				}
//				.setPositiveButton(getString(R.string.pro_cat_dialog_ok_btn)) { dialog, _ ->
//					if (checkedItem == -1) {
//						dialog.cancel()
//					} else {
//						viewModel.onSetStatusOfOrder(orderId, categoryItems[checkedItem])
//					}
//					dialog.cancel()
//				}
//				.show()
//		}
//	}
//
//	private fun getItemsCount(cartItems: List<UserData.CartItem>): Int {
//		var totalCount = 0
//		cartItems.forEach {
//			totalCount += it.quantity
//		}
//		return totalCount
//	}
//
//	private fun getItemsPriceTotal(
//		priceList: Map<String, Double>,
//		cartItems: List<UserData.CartItem>
//	): Double {
//		var totalPrice = 0.0
//		priceList.forEach { (itemId, price) ->
//			totalPrice += price * (cartItems.find { it.itemId == itemId }?.quantity ?: 1)
//		}
//		return totalPrice
//	}
}
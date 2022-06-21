package vn.ztech.software.ecom.ui.cart

import android.annotation.SuppressLint
import android.app.Application
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.Typeface
import android.os.Bundle
import android.util.Log
import android.view.View
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import org.koin.androidx.viewmodel.ext.android.viewModel
import vn.ztech.software.ecom.R
import vn.ztech.software.ecom.api.response.CartProductResponse
import vn.ztech.software.ecom.databinding.FragmentCartBinding
import vn.ztech.software.ecom.databinding.LayoutCircularLoaderBinding
import vn.ztech.software.ecom.model.Product
import vn.ztech.software.ecom.ui.BaseFragment

private const val TAG = "CartFragment"

class CartFragment : BaseFragment<FragmentCartBinding>() {

    private val viewModel: CartViewModel by viewModel()
    private lateinit var itemsAdapter: CartItemAdapter


    override fun setViewBinding(): FragmentCartBinding {
        return FragmentCartBinding.inflate(layoutInflater)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getListProductsInCart()
    }

    override fun setUpViews() {
        super.setUpViews()
        binding.loaderLayout.loaderFrameLayout.visibility = View.VISIBLE
        binding.loaderLayout.circularLoader.showAnimationBehavior
        binding.cartAppBar.topAppBar.title = getString(R.string.cart_fragment_label)
        binding.cartEmptyTextView.visibility = View.GONE
        binding.cartCheckOutBtn.setOnClickListener {
//            navigateToSelectAddress()
        }
        if (context != null) {
            setItemsAdapter(viewModel.products.value?: emptyList())
//            concatAdapter = ConcatAdapter(itemsAdapter, PriceCardAdapter())
            binding.cartProductsRecyclerView.adapter = itemsAdapter
        }
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
        viewModel.products.observe(viewLifecycleOwner) { products ->
                updateAdapter(products)
        }
        viewModel.deleteProductStatus.observe(viewLifecycleOwner) {
            it?.let {
                if (it){
                    viewModel.getListProductsInCart(false)
                }
            }
        }
        viewModel.addProductStatus.observe(viewLifecycleOwner){
            it?.let {
                if (it){
                    viewModel.getListProductsInCart(false)
                }
            }
        }
        viewModel.adjustProductStatus.observe(viewLifecycleOwner){
            it?.let {
                if (it){
                    viewModel.getListProductsInCart(false)
                }
            }
        }

    }
    private fun setItemsAdapter(products: List<CartProductResponse>) {
        itemsAdapter = CartItemAdapter(requireContext(), products)
        itemsAdapter.onClickListener = object : CartItemAdapter.OnClickListener {

            override fun onDeleteClick(itemId: String, itemBinding: LayoutCircularLoaderBinding) {
                Log.d(TAG, "onDelete: initiated")
                showDeleteDialog(itemId, itemBinding)
            }

            override fun onPlusClick(itemId: String) {
                Log.d(TAG, "onPlus: Increasing quantity ${itemId}")
                viewModel.addProductToCart(itemId, false)
            }

            override fun onMinusClick(itemId: String, currQuantity: Int,itemBinding: LayoutCircularLoaderBinding) {
                Log.d(TAG, "onMinus: decreasing quantity ${itemId} ${currQuantity}")
                if (currQuantity == 1) {
                    showDeleteDialog(itemId, itemBinding)
                } else {
                    viewModel.adjustProductQuantity(itemId, currQuantity - 1)
                }
            }
        }
    }

    private fun updateAdapter(products: List<CartProductResponse>) {
        Log.d(TAG, "update"+ products.size.toString())
        if(products.isEmpty()){
            binding.cartProductsRecyclerView.visibility = View.GONE
            binding.cartEmptyTextView.visibility = View.VISIBLE

        }else{
            binding.cartProductsRecyclerView.visibility = View.VISIBLE
            binding.cartEmptyTextView.visibility = View.GONE
        }
        itemsAdapter.apply {
            this.products = products
        }
//        concatAdapter = ConcatAdapter(itemsAdapter, PriceCardAdapter())
        binding.cartProductsRecyclerView.adapter = itemsAdapter
        binding.cartProductsRecyclerView.adapter?.notifyDataSetChanged()
    }

    override fun onStop() {
        super.onStop()
        viewModel.deleteProductStatus.value = false
        viewModel.adjustProductStatus.value = false
        viewModel.addProductStatus.value = false
    }

//
//        orderViewModel.dataStatus.observe(viewLifecycleOwner) { status ->
//            when (status) {
//                StoreDataStatus.LOADING -> {
//                    binding.cartProductsRecyclerView.visibility = View.GONE
//                    binding.cartCheckOutBtn.visibility = View.GONE
//                    binding.cartEmptyTextView.visibility = View.GONE
//                    binding.loaderLayout.loaderFrameLayout.visibility = View.VISIBLE
//                    binding.loaderLayout.circularLoader.showAnimationBehavior
//                }
//                else -> {
//                    binding.loaderLayout.circularLoader.hideAnimationBehavior
//                    binding.loaderLayout.loaderFrameLayout.visibility = View.GONE
//                }
//            }
//        }
//        orderViewModel.dataStatus.observe(viewLifecycleOwner) { status ->
//            if (status != null && status != StoreDataStatus.LOADING) {
//                orderViewModel.cartProducts.observe(viewLifecycleOwner) { itemList ->
//                    if (itemList.isNotEmpty()) {
//                        updateAdapter()
//                        binding.cartEmptyTextView.visibility = View.GONE
//                        binding.cartProductsRecyclerView.visibility = View.VISIBLE
//                        binding.cartCheckOutBtn.visibility = View.VISIBLE
//                    } else if (itemList.isEmpty()) {
//                        binding.cartProductsRecyclerView.visibility = View.GONE
//                        binding.cartCheckOutBtn.visibility = View.GONE
//                        binding.loaderLayout.loaderFrameLayout.visibility = View.GONE
//                        binding.loaderLayout.circularLoader.hideAnimationBehavior
//                        binding.cartEmptyTextView.visibility = View.VISIBLE
//                    }
//                }
//            }
//        }
//        orderViewModel.cartItems.observe(viewLifecycleOwner) { items ->
//            if (items.isNotEmpty()) {
//                updateAdapter()
//            }
//        }
//        orderViewModel.priceList.observe(viewLifecycleOwner) {
//            if (it.isNotEmpty()) {
//                updateAdapter()
//            }
//        }
//        orderViewModel.userLikes.observe(viewLifecycleOwner) {
//            if (it.isNotEmpty()) {
//                updateAdapter()
//            }
//        }

//    private lateinit var itemsAdapter: CartItemAdapter
//    private lateinit var concatAdapter: ConcatAdapter
//


//

//
//
//
//    private fun navigateToSelectAddress() {
//        findNavController().navigate(R.id.action_cartFragment_to_selectAddressFragment)
//    }
//
    private fun showDeleteDialog(itemId: String, itemBinding: LayoutCircularLoaderBinding) {
        context?.let {
            MaterialAlertDialogBuilder(it)
                .setTitle(getString(R.string.delete_dialog_title_text))
                .setMessage(getString(R.string.delete_cart_item_message_text))
                .setNegativeButton(getString(R.string.pro_cat_dialog_cancel_btn)) { dialog, _ ->
                    dialog.cancel()
                    itemBinding.loaderFrameLayout.visibility = View.GONE
                }
                .setPositiveButton(getString(R.string.delete_dialog_delete_btn_text)) { dialog, _ ->
                    itemBinding.loaderFrameLayout.visibility = View.VISIBLE
                    viewModel.deleteProductFromCart(itemId)
                    dialog.dismiss()
                }.setOnCancelListener {
                    itemBinding.loaderFrameLayout.visibility = View.GONE
                }
                .show()
        }
    }
//
//    inner class PriceCardAdapter : RecyclerView.Adapter<PriceCardAdapter.ViewHolder>() {
//
//        inner class ViewHolder(private val priceCardBinding: LayoutPriceCardBinding) :
//            RecyclerView.ViewHolder(priceCardBinding.root) {
//            fun bind() {
//                priceCardBinding.priceItemsLabelTv.text = getString(
//                    R.string.price_card_items_string,
//                    orderViewModel.getItemsCount().toString()
//                )
//                priceCardBinding.priceItemsAmountTv.text =
//                    getString(R.string.price_text, orderViewModel.getItemsPriceTotal().toString())
//                priceCardBinding.priceShippingAmountTv.text = getString(R.string.price_text, "0")
//                priceCardBinding.priceChargesAmountTv.text = getString(R.string.price_text, "0")
//                priceCardBinding.priceTotalAmountTv.text =
//                    getString(R.string.price_text, orderViewModel.getItemsPriceTotal().toString())
//            }
//        }
//
//        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
//            return ViewHolder(
//                LayoutPriceCardBinding.inflate(
//                    LayoutInflater.from(parent.context),
//                    parent,
//                    false
//                )
//            )
//        }
//
//        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
//            holder.bind()
//        }
//
//        override fun getItemCount() = 1
//    }

}
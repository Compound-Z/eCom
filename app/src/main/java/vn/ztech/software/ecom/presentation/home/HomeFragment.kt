package vn.ztech.software.ecom.presentation.home

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import vn.ztech.software.ecom.R
import vn.ztech.software.ecom.common.StoreDataStatus
import vn.ztech.software.ecom.databinding.FragmentHomeBinding
import vn.ztech.software.ecom.domain.model.Product
import vn.ztech.software.ecom.presentation.common.ItemDecorationRecyclerViewPadding
import vn.ztech.software.ecom.presentation.home.ListProductsAdapter.OnClickListener
import org.koin.androidx.viewmodel.ext.android.viewModel

private const val TAG = "HomeFragment"


class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private val viewModel: HomeViewModel by viewModel()
    private lateinit var listProductsAdapter: ListProductsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(layoutInflater)
        setViews()
        setObservers()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getProducts()
    }

    private fun setViews() {
        setHomeTopAppBar()
        if (context != null) {
            setUpProductAdapter(viewModel.allProducts.value)
            binding.productsRecyclerView.apply {
                val gridLayoutManager = GridLayoutManager(context, 2)
                gridLayoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                    override fun getSpanSize(position: Int): Int {
                        return when (listProductsAdapter.getItemViewType(position)) {
                            2 -> 2 //ad
                            else -> {
                                val proCount = listProductsAdapter.data.count { it is Product }
                                val adCount = listProductsAdapter.data.size - proCount
                                val totalCount = proCount + (adCount * 2)
                                // product, full for last item
                                if (position + 1 == listProductsAdapter.data.size && totalCount % 2 == 1) 2 else 1
                            }
                        }
                    }
                }
                layoutManager = gridLayoutManager
                adapter = listProductsAdapter
                val itemDecoration = ItemDecorationRecyclerViewPadding()
                if (itemDecorationCount == 0) {
                    addItemDecoration(itemDecoration)
                }
            }
        }
        //feature: this will be add when the app supports seller.
//        if (!viewModel.isUserASeller) {
//            binding.homeFabAddProduct.visibility = View.GONE
//        }
//        binding.homeFabAddProduct.setOnClickListener {
//            showDialogWithItems(ProductCategories, 0, false)
//        }
        binding.loaderLayout.loaderFrameLayout.visibility = View.VISIBLE
        binding.loaderLayout.circularLoader.showAnimationBehavior
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun setObservers() {
        viewModel.storeDataStatus.observe(viewLifecycleOwner) { status ->
            if(status == StoreDataStatus.LOADING) {
                binding.loaderLayout.loaderFrameLayout.visibility = View.VISIBLE
                binding.loaderLayout.circularLoader.showAnimationBehavior
                binding.productsRecyclerView.visibility = View.GONE
            }else{
                binding.loaderLayout.circularLoader.hideAnimationBehavior
                binding.loaderLayout.loaderFrameLayout.visibility = View.GONE
            }
        }
        viewModel.allProducts.observe(viewLifecycleOwner) { listProducts->
            if (listProducts.isNotEmpty()) {
                binding.loaderLayout.circularLoader.hideAnimationBehavior
                binding.loaderLayout.loaderFrameLayout.visibility = View.GONE
                binding.productsRecyclerView.visibility = View.VISIBLE
                binding.productsRecyclerView.adapter?.apply {
                    listProductsAdapter.data =
                        getMixedDataList(listProducts, getAdsList())
                    notifyDataSetChanged()
                }
            }
        }
    }

    private fun setHomeTopAppBar() {
//        var lastInput = ""
//        val debounceJob: Job? = null
//        val uiScope = CoroutineScope(Dispatchers.Main + SupervisorJob())
//        binding.homeTopAppBar.topAppBar.inflateMenu(R.menu.home_app_bar_menu)
//        binding.homeTopAppBar.homeSearchEditText.onFocusChangeListener = focusChangeListener
//        binding.homeTopAppBar.homeSearchEditText.doAfterTextChanged { editable ->
//            if (editable != null) {
//                val newtInput = editable.toString()
//                debounceJob?.cancel()
//                if (lastInput != newtInput) {
//                    lastInput = newtInput
//                    uiScope.launch {
//                        delay(500)
//                        if (lastInput == newtInput) {
//                            performSearch(newtInput)
//                        }
//                    }
//                }
//            }
//        }
//        binding.homeTopAppBar.homeSearchEditText.setOnEditorActionListener { textView, actionId, _ ->
//            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
//                textView.clearFocus()
//                val inputManager =
//                    requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
//                inputManager.hideSoftInputFromWindow(textView.windowToken, 0)
//                performSearch(textView.text.toString())
//                true
//            } else {
//                false
//            }
//        }
//        binding.homeTopAppBar.searchOutlinedTextLayout.setEndIconOnClickListener {
//            it.clearFocus()
//            binding.homeTopAppBar.homeSearchEditText.setText("")
//            val inputManager =
//                requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
//            inputManager.hideSoftInputFromWindow(it.windowToken, 0)
////			viewModel.filterProducts("All")
//        }
//        binding.homeTopAppBar.topAppBar.setOnMenuItemClickListener { menuItem ->
//            setAppBarItemClicks(menuItem)
//        }
    }

    private fun setAppBarItemClicks(menuItem: MenuItem): Boolean {
        return true //this line should be deleted when the codes below is uncommented
//        return when (menuItem.itemId) {
//            R.id.home_filter -> {
//                val extraFilters = arrayOf("All", "None")
//                val categoryList = ProductCategories.plus(extraFilters)
//                val checkedItem = categoryList.indexOf(viewModel.filterCategory.value)
//                showDialogWithItems(categoryList, checkedItem, true)
//                true
//            }
//            else -> false
//        }
    }

    private fun setUpProductAdapter(productsList: List<Product>?) {
        listProductsAdapter = ListProductsAdapter(productsList ?: emptyList(), requireContext())
        listProductsAdapter.onClickListener =  object : OnClickListener {
            override fun onClick(productData: Product) {
                findNavController().navigate(
                    R.id.action_seeProduct,
                    bundleOf("productId" to productData.id)
                )
            }

            override fun onDeleteClick(productData: Product) {
//                Log.d(TAG, "onDeleteProduct: initiated for ${productData.productId}")
//                showDeleteDialog(productData.name, productData.productId)
            }

            override fun onEditClick(productId: String) {
//                Log.d(TAG, "onEditProduct: initiated for $productId")
//                navigateToAddEditProductFragment(isEdit = true, productId = productId)
            }

            override fun onLikeClick(productId: String) {
//                Log.d(TAG, "onToggleLike: initiated for $productId")
//                viewModel.toggleLikeByProductId(productId)
            }

            override fun onAddToCartClick(productData: Product) {
                Log.d(TAG, "onToggleCartAddition: initiated")
//                viewModel.toggleProductInCart(productData)
            }
        }
        listProductsAdapter.bindImageButtons = object : ListProductsAdapter.BindImageButtons {

            override fun setCartButton(productId: String, imgView: ImageView) {
//                if (viewModel.isProductInCart(productId)) {
//                    imgView.setImageResource(R.drawable.ic_remove_shopping_cart_24)
//                } else {
//                    imgView.setImageResource(R.drawable.ic_add_shopping_cart_24)
//                }
            }

        }
    }

    private fun getMixedDataList(data: List<Product>, adsList: List<Int>): List<Any> {
        val itemsList = mutableListOf<Any>()
        itemsList.addAll(data.sortedBy { it.id })
        var currPos = 0
        if (itemsList.size >= 4) {
            adsList.forEach label@{ ad ->
                if (itemsList.size > currPos + 1) {
                    itemsList.add(currPos, ad)
                } else {
                    return@label
                }
                currPos += 5
            }
        }
        return itemsList
    }
    private fun getAdsList(): List<Int> {
        //feature: for now, ad is not supported
        return listOf()
    }

}
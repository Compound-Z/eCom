package vn.ztech.software.ecom.ui.category

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import org.koin.androidx.viewmodel.ext.android.viewModel
import vn.ztech.software.ecom.R
import vn.ztech.software.ecom.common.StoreDataStatus
import vn.ztech.software.ecom.databinding.FragmentAccountBinding
import vn.ztech.software.ecom.databinding.FragmentCategoryBinding
import vn.ztech.software.ecom.databinding.FragmentHomeBinding
import vn.ztech.software.ecom.model.Category
import vn.ztech.software.ecom.model.Product
import vn.ztech.software.ecom.ui.BaseFragment
import vn.ztech.software.ecom.ui.common.ItemDecorationRecyclerViewPadding
import vn.ztech.software.ecom.ui.category.ListCategoriesAdapter

class CategoryFragment : BaseFragment<FragmentCategoryBinding>() {
    override fun setViewBinding(): FragmentCategoryBinding {
        return FragmentCategoryBinding.inflate(layoutInflater)
    }
    
    private val viewModel: CategoryViewModel by viewModel()
    private lateinit var listCategoriesAdapter: ListCategoriesAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getCategories()
    }
    override fun setUpViews() {
        super.setUpViews()
        setHomeTopAppBar()
        if (context != null) {
            setUpCategoryAdapter(viewModel.allCategories.value)
            binding.categoriesRecyclerView.apply {
                val gridLayoutManager = GridLayoutManager(context, 3)
                val proCount = listCategoriesAdapter.data.count { it is Category }
                val adCount = listCategoriesAdapter.data.size - proCount
                val totalCount = proCount + (adCount * 3)
                gridLayoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                    override fun getSpanSize(position: Int): Int {
                        return when (listCategoriesAdapter.getItemViewType(position)) {
                            2 -> 2 //ad
                            else -> {

                                // product, full for last item
                                if (position + 1 == listCategoriesAdapter.data.size && totalCount % 2 == 1) 2 else 1
                            }
                        }
                    }
                }
                layoutManager = gridLayoutManager
                adapter = listCategoriesAdapter
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

        binding.swipeRefresh.setOnRefreshListener {
            viewModel.getCategories()
        }
    }
//
    @SuppressLint("NotifyDataSetChanged")
    override fun observeView() {
        super.observeView()
        viewModel.storeDataStatus.observe(viewLifecycleOwner) { status ->
            if(status == StoreDataStatus.LOADING) {
                binding.loaderLayout.loaderFrameLayout.visibility = View.VISIBLE
                binding.loaderLayout.circularLoader.showAnimationBehavior
                binding.categoriesRecyclerView.visibility = View.GONE
            }else{
                binding.loaderLayout.circularLoader.hideAnimationBehavior
                binding.loaderLayout.loaderFrameLayout.visibility = View.GONE
                binding.swipeRefresh.isRefreshing = false
            }
        }
        viewModel.allCategories.observe(viewLifecycleOwner) { listCategories->
            if (listCategories.isNotEmpty()) {
                binding.loaderLayout.circularLoader.hideAnimationBehavior
                binding.loaderLayout.loaderFrameLayout.visibility = View.GONE
                binding.categoriesRecyclerView.visibility = View.VISIBLE
                binding.categoriesRecyclerView.adapter?.apply {
                    listCategoriesAdapter.data = listCategories
                    notifyDataSetChanged()
                }
            }
        }
    }
//
    private fun setHomeTopAppBar() {
        var lastInput = ""
        binding.categoryTopAppBar.homeSearchEditText.setOnEditorActionListener { textView, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                textView.clearFocus()
                val inputManager =
                    requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                inputManager.hideSoftInputFromWindow(textView.windowToken, 0)
                performSearch(textView.text.toString())
                true
            } else {
                false
            }
        }
        binding.categoryTopAppBar.searchOutlinedTextLayout.setEndIconOnClickListener {
            it.clearFocus()
            binding.categoryTopAppBar.homeSearchEditText.setText("")
            val inputManager =
                requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputManager.hideSoftInputFromWindow(it.windowToken, 0)
        }
    }

    private fun performSearch(searchWords: String) {
        viewModel.searchProducts(searchWords)
    }
    

    private fun setUpCategoryAdapter(categoriesList: List<Category>?) {
        listCategoriesAdapter = ListCategoriesAdapter(categoriesList ?: emptyList(), requireContext())
        listCategoriesAdapter.onClickListener =  object : ListCategoriesAdapter.OnClickListener {
            override fun onClick(categoryData: Category) {
                findNavController().navigate(
                    R.id.action_category_2_list_products,
                    bundleOf("category" to categoryData)
                )
            }
        }
    }

}
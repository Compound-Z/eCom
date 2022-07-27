package vn.ztech.software.ecom.ui.shop.categories

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import vn.ztech.software.ecom.R
import vn.ztech.software.ecom.common.StoreDataStatus
import vn.ztech.software.ecom.databinding.FragmentCategoryBinding
import vn.ztech.software.ecom.databinding.FragmentListCategoriesInShopBinding
import vn.ztech.software.ecom.model.Category
import vn.ztech.software.ecom.ui.BaseFragment
import vn.ztech.software.ecom.ui.category.CategoryViewModel
import vn.ztech.software.ecom.ui.category.ListCategoriesAdapter
import vn.ztech.software.ecom.ui.common.ItemDecorationRecyclerViewPadding
import vn.ztech.software.ecom.ui.shop.ShopViewModel


class ListCategoriesInShopFragment : BaseFragment<FragmentListCategoriesInShopBinding>() {
    override fun setViewBinding(): FragmentListCategoriesInShopBinding {
        return FragmentListCategoriesInShopBinding.inflate(layoutInflater)
    }

    private val viewModel: ListCategoriesInShopViewModel by viewModel()
    private val shopViewMode: ShopViewModel by sharedViewModel()
    private lateinit var listCategoriesAdapter: ListCategoriesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.getCategories(shopViewMode.shopId.value)
    }

    override fun setUpViews() {
        super.setUpViews()
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
            binding.categoryTopAppBar.topAppBar.title = "${listCategories.size} categories"
        }
        viewModel.error.observe(viewLifecycleOwner){
            it ?: return@observe
            handleError(it)
        }
    }


    private fun setUpCategoryAdapter(categoriesList: List<Category>?) {
        listCategoriesAdapter = ListCategoriesAdapter(categoriesList ?: emptyList(), requireContext())
        listCategoriesAdapter.onClickListener =  object : ListCategoriesAdapter.OnClickListener {
            override fun onClick(categoryData: Category) {
                findNavController().navigate(
                    R.id.listProductsOfCategoryInShopFragment,
                    bundleOf("category" to categoryData)
                )
            }
        }
    }
    override fun onStop() {
        super.onStop()
        viewModel.clearErrors()
    }
}
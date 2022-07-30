package vn.ztech.software.ecom.ui.shop

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.net.toUri
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayoutMediator
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import vn.ztech.software.ecom.R
import vn.ztech.software.ecom.databinding.FragmentShopBinding
import vn.ztech.software.ecom.model.Product
import vn.ztech.software.ecom.model.Shop
import vn.ztech.software.ecom.ui.BaseFragment2
import vn.ztech.software.ecom.ui.review.ListReviewQueueFragment
import vn.ztech.software.ecom.ui.review.ListReviewReviewedFragment
import vn.ztech.software.ecom.ui.review.MyReviewFragment
import vn.ztech.software.ecom.ui.shop.categories.ListCategoriesInShopFragment
import vn.ztech.software.ecom.ui.shop.info.ShopInfoFragment
import vn.ztech.software.ecom.ui.shop.products.ListProductsInShopFragment
import vn.ztech.software.ecom.util.extension.removeUnderline


class ShopFragment : BaseFragment2<FragmentShopBinding>(), ListProductsInShopFragment.OnClickListener {
    private val viewModel: ShopViewModel by sharedViewModel()
    private lateinit var childFragment: ChildFragmentAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.takeIf { it.containsKey("shopId") }?.let {
            val shopId = arguments?.getString("shopId")
            /**only update shop header when this shop is different from the previous shop*/
            if (viewModel.shop.value == null || shopId != viewModel.shopId.value){
                viewModel.getShopInfo(shopId)
            }
            viewModel.shopId.value = shopId
        }
        setUpUI()
    }

    private fun setUpUI() {
        childFragment =
            ChildFragmentAdapter(this@ShopFragment)
        binding.pager.adapter = childFragment
        TabLayoutMediator(binding.tabLayout, binding.pager) {tab, pos->
            when(pos){
                0->{
                    tab.text = "Products"
                }
                1->{
                    tab.text = "Categories"
                }
                2->{
                    tab.text = "Info"
                }
            }
        }.attach()
    }

    override fun setUpViews() {
        super.setUpViews()
        binding.shopAppBar.topAppBar.title = "View shop"
        binding.shopAppBar.topAppBar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }
        binding.layoutShop.setOnClickListener {
            findNavController().navigate(
                R.id.action_shopFragment_to_shopInfoFragment
            )
        }
        binding.btViewShop.setOnClickListener {
            findNavController().navigate(
                R.id.action_shopFragment_to_shopInfoFragment
            )
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
        viewModel.shop.observe(viewLifecycleOwner){
            it?.let { updateUI(it) }
        }
        viewModel.error.observe(viewLifecycleOwner){
            it?.let {
                binding.loaderLayout.circularLoader.hideAnimationBehavior
                binding.loaderLayout.loaderFrameLayout.visibility = View.GONE
                handleError(it)
            }
        }
    }

    private fun updateUI(it: Shop) {
        if (it.imageUrl.isNotEmpty()) {
            val imgUrl = it.imageUrl.toUri().buildUpon().scheme("https").build()
            Glide.with(requireContext())
                .asBitmap()
                .load(imgUrl)
                .into(binding.ivShop)
        }

        binding.tvShopName.text = it.name.removeUnderline()
        binding.tvNumberOfProduct.text = "${it.numberOfProduct} products"
        binding.tvShopAddress.text = "${it.addressItem?.province?.name}"
        binding.btViewShop.setOnClickListener {
            //todo: navigate to shopDetail tab
        }
    }

    class ChildFragmentAdapter(fragment: ShopFragment) : FragmentStateAdapter(fragment) {

        override fun getItemCount(): Int {
            return 3
        }

        override fun createFragment(position: Int): Fragment {
            when(position){
                0->{
                    return ListProductsInShopFragment()
                }
                1->{
                    return ListCategoriesInShopFragment()
                }
                2->{
                    return ShopInfoFragment()
                }
            }
            return ListProductsInShopFragment()
        }
    }
    override fun setViewBinding(): FragmentShopBinding {
        return FragmentShopBinding.inflate(layoutInflater)
    }

    override fun onItemClick(product: Product) {
        Log.d("XXXX", product.toString())
        findNavController().navigate(
            R.id.action_shopFragment_to_productDetailsFragment,
            bundleOf("product" to product, "ADD_TO_CART_BUTTON_ENABLED" to true)
        )
    }

    override fun onStop() {
        super.onStop()
        viewModel.clearErrors()
    }

}
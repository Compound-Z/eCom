package vn.ztech.software.ecom.ui.shop

import android.os.Bundle
import android.view.View
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayoutMediator
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import vn.ztech.software.ecom.databinding.FragmentShopBinding
import vn.ztech.software.ecom.model.Shop
import vn.ztech.software.ecom.ui.BaseFragment2
import vn.ztech.software.ecom.ui.review.ListReviewQueueFragment
import vn.ztech.software.ecom.ui.review.ListReviewReviewedFragment
import vn.ztech.software.ecom.ui.review.MyReviewFragment
import vn.ztech.software.ecom.ui.shop.categories.ListCategoriesInShopFragment
import vn.ztech.software.ecom.ui.shop.info.ShopInfoFragment
import vn.ztech.software.ecom.ui.shop.products.ListProductsInShopFragment
import vn.ztech.software.ecom.util.extension.removeUnderline


class ShopFragment : BaseFragment2<FragmentShopBinding>() {
    private val viewModel: ShopViewModel by sharedViewModel()
    private lateinit var childFragment: ChildFragmentAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.takeIf { it.containsKey("shopId") }?.let {
            viewModel.shopId.value = arguments?.getString("shopId")
        }
        if(viewModel.shop.value == null ){
            viewModel.getShopInfo(viewModel.shopId.value)
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

}
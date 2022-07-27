package vn.ztech.software.ecom.ui.product_details

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.net.toUri
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.PagerSnapHelper
import com.bumptech.glide.Glide
import org.koin.androidx.viewmodel.ext.android.viewModel
import vn.ztech.software.ecom.R
import vn.ztech.software.ecom.common.StoreDataStatus
import vn.ztech.software.ecom.databinding.FragmentProductDetailsBinding
import vn.ztech.software.ecom.databinding.ItemPreviewReviewSellerBinding
import vn.ztech.software.ecom.model.Review
import vn.ztech.software.ecom.ui.BaseFragment2
import vn.ztech.software.ecom.ui.cart.CartViewModel
import vn.ztech.software.ecom.ui.cart.DialogAddToCartSuccessFragment
import vn.ztech.software.ecom.ui.main.MainActivity
import vn.ztech.software.ecom.util.extension.round1Decimal
import vn.ztech.software.ecom.util.extension.showErrorDialog
import vn.ztech.software.ecom.util.extension.toDateTimeString

class ProductDetailsFragment2 : BaseFragment2<FragmentProductDetailsBinding>(),
    DialogAddToCartSuccessFragment.OnClick {
    val TAG = "ProductDetailsFragment"
    private val viewModel: ProductDetailsViewModel by viewModel()
    private val cartViewModel: CartViewModel by viewModel()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val productId = arguments?.getString("productId") as String?
        viewModel.getProduct(productId?:"")
        viewModel.getProductDetails(productId?:"")
        if (viewModel.reviews.value == null || viewModel.reviews.value?.isEmpty() == true){
            viewModel.getReviewsOfThisProduct(productId?:"")
        }
    }

    override fun setUpViews() {
        super.setUpViews()
        binding.proDetailsAddCartBtn.visibility = View.VISIBLE
        binding.proDetailsAddCartBtn.setOnClickListener {
            addToCart(viewModel.product.value?._id)
        }
        binding.proDetailsAddCartBtn.visibility = View.VISIBLE
        binding.layoutViewsGroup.visibility = View.GONE
    }

    override fun observeView() {
        super.observeView()
        setObservers()

    }

    private fun setObservers() {
        viewModel.storeDataStatus.observe(viewLifecycleOwner) {
            when (it) {
                StoreDataStatus.DONE -> {
                    binding.loaderLayout.loaderFrameLayout.visibility = View.GONE
                    binding.proDetailsLayout.visibility = View.VISIBLE
                }
                else -> {
//                    binding.proDetailsLayout.visibility = View.GONE
                    binding.loaderLayout.loaderFrameLayout.visibility = View.VISIBLE
                }
            }
        }
        viewModel.loadingProduct.observe(viewLifecycleOwner){
            if (it){
                binding.loaderLayout.loaderFrameLayout.visibility = View.VISIBLE
            }else{
                binding.loaderLayout.loaderFrameLayout.visibility = View.GONE
                binding.proDetailsLayout.visibility = View.VISIBLE
            }
        }
        viewModel.product.observe(viewLifecycleOwner){
            if(viewModel.checkIsDataReady()){
                setViews()
            }
        }
        viewModel.productDetails.observe(viewLifecycleOwner){
            if(viewModel.checkIsDataReady()){
                setViews()
            }
        }
        viewModel.reviews.observe(viewLifecycleOwner){
            it?.let {
                addReviewUI(it)
            }
        }
        viewModel.error.observe(viewLifecycleOwner){
            it?.let {
                handleError(it)
            }
        }

        cartViewModel.loading.observe(viewLifecycleOwner){
            it?.let {
                if (it){
                    binding.loaderLayout.loaderFrameLayout.visibility = View.VISIBLE
                }else{
                    binding.loaderLayout.loaderFrameLayout.visibility = View.GONE
                }
            }
        }
        cartViewModel.addProductStatus.observe(viewLifecycleOwner){
            it?.let {
                if (it){
                    showBottomDialogSuccess()
                }
            }
        }
        cartViewModel.error.observe(viewLifecycleOwner){
            it?.let {
                showErrorDialog(it)
            }
        }
    }

    private fun addReviewUI(it: List<Review>) {

        if(viewModel.hasNextPage.value == true) {
            binding.btViewAllReview.apply {
                visibility = View.VISIBLE
                setOnClickListener {
                    findNavController().navigate(
                        R.id.action_productDetailsFragment2_to_listReviewOfProductFragment,
                        bundleOf(
                            "product" to viewModel.product.value
                        )
                    )
                }
            }
        } else {
            binding.btViewAllReview.apply {
                visibility = View.GONE

            }
        }

        val layoutReviewItems = binding.layoutReviewItems
        layoutReviewItems.removeAllViews()
        it.forEach {
            val view = ItemPreviewReviewSellerBinding.inflate(layoutInflater)
            view.tvUserName.text = it.userName
            view.ratingBar.rating = it.rating.toFloat()
            view.tvReviewContent.text = it.content
            view.tvDateTime.text = it.updatedAt.toDateTimeString()

            layoutReviewItems.addView(view.root)
        }
    }


    private fun setViews() {
        binding.layoutViewsGroup.visibility = View.VISIBLE
        binding.addProAppBar.topAppBar.title = viewModel.product.value?.name
        binding.addProAppBar.topAppBar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }

        setImagesView()

        binding.proDetailsTitleTv.text = viewModel.product.value?.name.toString()
        binding.proDetailsRatingBar.rating = (viewModel.product.value?.averageRating ?: 0.0f)
        binding.tvAverageRating.text = "${viewModel.product.value?.averageRating?.round1Decimal().toString()}"
        binding.tvSoldNumber.text = "Sold: ${viewModel.product.value?.saleNumber.toString()}"

        binding.proDetailsPriceTv.text = resources.getString(
            R.string.pro_details_price_value,
            viewModel.product.value?.price
        )
        binding.proDetailsSpecificsText.text = viewModel.productDetails.value?.description ?: ""
        binding.categoryValue.text = viewModel.product.value?.category
        binding.unitValue.text = viewModel.productDetails.value?.unit
        binding.brandValue.text = viewModel.productDetails.value?.brandName
        binding.originValue.text = viewModel.productDetails.value?.origin

        binding.ratingBar.rating = viewModel.product.value?.averageRating?:0f
        binding.tvAverageRating2.text = "${viewModel.product.value?.averageRating} / 5"
        binding.numOfReview.text = "(${viewModel.product.value?.numberOfRating} reviews)"

        /**shop info*/
        if (viewModel.productDetails.value?.shopId?.imageUrl?.isNotEmpty() == true) {
            val imgUrl = viewModel.productDetails.value?.shopId?.imageUrl?.toUri()?.buildUpon()?.scheme("https")?.build()
            Glide.with(requireContext())
                .asBitmap()
                .load(imgUrl)
                .into(binding.layoutShop.ivShop)
        }
        binding.layoutShop.tvShopName.text = viewModel.productDetails.value?.shopId?.name
        binding.layoutShop.tvShopAddress.text = viewModel.productDetails.value?.shopId?.name
        binding.layoutShop.tvNumberOfProduct.text = "${viewModel.productDetails.value?.shopId?.numberOfProduct} products"
        binding.layoutShop.btViewShop.setOnClickListener {
            findNavController().navigate(
                R.id.action_productDetailsFragment_to_shopFragment,
                bundleOf(
                    "shopId" to viewModel.productDetails.value?.shopId?._id
                )
            )
        }
    }

    private fun setImagesView() {
        if (context != null) {
            binding.proDetailsImagesRecyclerview.isNestedScrollingEnabled = false
            val adapter = ProductImagesAdapter(
                requireContext(),
                viewModel.productDetails.value?.imageUrls ?: emptyList()
            )
            binding.proDetailsImagesRecyclerview.adapter = adapter
            val rad = resources.getDimension(R.dimen.radius)
            val dotsHeight = resources.getDimensionPixelSize(R.dimen.dots_height)
            val inactiveColor = ContextCompat.getColor(requireContext(), R.color.gray)
            val activeColor = ContextCompat.getColor(requireContext(), R.color.blue_accent_300)
            val itemDecoration =
                DotsIndicatorDecoration(rad, rad * 4, dotsHeight, inactiveColor, activeColor)
            binding.proDetailsImagesRecyclerview.addItemDecoration(itemDecoration)
            binding.proDetailsImagesRecyclerview.adapter
            binding.proDetailsImagesRecyclerview.onFlingListener = null;
            PagerSnapHelper().attachToRecyclerView(binding.proDetailsImagesRecyclerview)
        }
    }
    private fun addToCart(_id: String?) {
        cartViewModel.addProductToCart(_id)
    }
    private fun showBottomDialogSuccess() {
        viewModel.product.value?.let {
            DialogAddToCartSuccessFragment(it, this@ProductDetailsFragment2).show(parentFragmentManager,"DialogAddToCartSuccessFragment")
        }
    }

    override fun onButtonGoToCartClicked() {
        cartViewModel.addProductStatus.value = false /**refresh live data value to the original, so that the bottom sheet will not show up when navigate back to this fragment*/
        (activity as MainActivity).binding.homeBottomNavigation.selectedItemId = R.id.cartFragment
    }
    override fun onStop() {
        super.onStop()
        viewModel.clearErrors()
    }

    override fun setViewBinding(): FragmentProductDetailsBinding {
        return FragmentProductDetailsBinding.inflate(layoutInflater)
    }

}
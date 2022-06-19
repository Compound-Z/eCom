package vn.ztech.software.ecom.ui.product_details

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.PagerSnapHelper
import vn.ztech.software.ecom.R
import vn.ztech.software.ecom.databinding.FragmentProductDetailsBinding
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.component.getScopeId
import vn.ztech.software.ecom.common.StoreDataStatus
import vn.ztech.software.ecom.model.Product

class ProductDetailsFragment : Fragment() {
    private lateinit var binding: FragmentProductDetailsBinding
    private val viewModel: ProductDetailsViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProductDetailsBinding.inflate(layoutInflater)

//        if (viewModel.isSeller()) {
//            binding.proDetailsAddCartBtn.visibility = View.GONE
//        } else {
            binding.proDetailsAddCartBtn.visibility = View.VISIBLE
            binding.proDetailsAddCartBtn.setOnClickListener {
//                if (viewModel.isItemInCart.value == true) {
//                    navigateToCartFragment()
//                } else {
//                    onAddToCart()
//                    if (viewModel.errorStatus.value?.isEmpty() == true) {
//                        viewModel.addItemStatus.observe(viewLifecycleOwner) { status ->
//                            if (status == AddObjectStatus.DONE) {
//                                makeToast("Product Added To Cart")
//                                viewModel.checkIfInCart()
//                            }
//                        }
//                    }
//                }
            }
//        }

        binding.loaderLayout.loaderFrameLayout.background =
            ResourcesCompat.getDrawable(resources, R.color.white, null)

        binding.layoutViewsGroup.visibility = View.GONE
        binding.proDetailsAddCartBtn.visibility = View.GONE
        setObservers()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val product = arguments?.getParcelable("product") as Product?
        viewModel.product.value = product
        viewModel.getProductDetails(product?._id?:"")
    }

    private fun setObservers() {
        viewModel.storeDataStatus.observe(viewLifecycleOwner) {
            when (it) {
                StoreDataStatus.DONE -> {
                    binding.loaderLayout.loaderFrameLayout.visibility = View.GONE
                    binding.proDetailsLayout.visibility = View.VISIBLE
                }
                else -> {
                    binding.proDetailsLayout.visibility = View.GONE
                    binding.loaderLayout.loaderFrameLayout.visibility = View.VISIBLE
                }
            }
        }
        viewModel.productDetails.observe(viewLifecycleOwner){
            setViews()
        }
//        viewModel.isItemInCart.observe(viewLifecycleOwner) {
//            if (it == true) {
//                binding.proDetailsAddCartBtn.text =
//                    getString(R.string.pro_details_go_to_cart_btn_text)
//            } else {
//                binding.proDetailsAddCartBtn.text =
//                    getString(R.string.pro_details_add_to_cart_btn_text)
//            }
//        }
//        viewModel.errorStatus.observe(viewLifecycleOwner) {
//            if (it.isNotEmpty())
//                modifyErrors(it)
//        }
    }

    private fun setViews() {
        binding.layoutViewsGroup.visibility = View.VISIBLE
        binding.proDetailsAddCartBtn.visibility = View.VISIBLE
        binding.addProAppBar.topAppBar.title = viewModel.product.value?.name
        binding.addProAppBar.topAppBar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }
        binding.addProAppBar.topAppBar.inflateMenu(R.menu.app_bar_menu)
        binding.addProAppBar.topAppBar.overflowIcon?.setTint(
            ContextCompat.getColor(
                requireContext(),
                R.color.gray
            )
        )

        setImagesView()

        binding.proDetailsTitleTv.text = viewModel.product.value?.name.toString()
//
        binding.proDetailsRatingBar.rating = (viewModel.product.value?.averageRating ?: 0.0).toFloat()
        binding.tvNumberOfReviews.text = "(${viewModel.productDetails.value?.numOfReviews.toString()})"
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
        //todo: add provider textview
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
            PagerSnapHelper().attachToRecyclerView(binding.proDetailsImagesRecyclerview)
        }
    }
}
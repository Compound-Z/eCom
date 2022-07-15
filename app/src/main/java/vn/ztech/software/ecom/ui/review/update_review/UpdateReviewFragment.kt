package vn.ztech.software.ecom.ui.review.update_review

import android.os.Bundle
import android.view.View
import androidx.core.net.toUri
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import org.koin.androidx.viewmodel.ext.android.viewModel
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import vn.ztech.software.ecom.R
import vn.ztech.software.ecom.databinding.FragmentCreateReviewBinding
import vn.ztech.software.ecom.databinding.FragmentUpdateReviewBinding
import vn.ztech.software.ecom.ui.BaseFragment2


class UpdateReviewFragment : BaseFragment2<FragmentUpdateReviewBinding>() {
    private val viewModel: UpdateReviewViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.takeIf { it.containsKey("reviewId") }?.let {
            viewModel.reviewId = arguments?.getString("reviewId")
        }
        arguments?.takeIf { it.containsKey("imageUrl") }?.let {
            viewModel.imageUrl = arguments?.getString("imageUrl")
        }
        arguments?.takeIf { it.containsKey("productName") }?.let {
            viewModel.productName = arguments?.getString("productName")
        }
        arguments?.takeIf { it.containsKey("rating") }?.let {
            viewModel.rating = arguments?.getFloat("rating")
        }
        arguments?.takeIf { it.containsKey("reviewContent") }?.let {
            viewModel.reviewContent = arguments?.getString("reviewContent")
        }
    }

    override fun setUpViews() {
        super.setUpViews()
        binding.topAppBarCreateReview.topAppBar.title = "Create review"
        binding.topAppBarCreateReview.topAppBar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }

        if ( ! viewModel.imageUrl.isNullOrEmpty()) {
            val imgUrl = viewModel.imageUrl!!.toUri().buildUpon().scheme("https").build()
            Glide.with(requireContext())
                .asBitmap()
                .load(imgUrl)
                .into(binding.ivProduct)
            binding.ivProduct.clipToOutline = true
        }

        binding.tvProductName.text = viewModel.productName

        binding.ratingBar.rating = viewModel.rating?:5f
        binding.etReview.setText(viewModel.reviewContent?:"")
        binding.btReview.setOnClickListener {
//            findNavController().navigate(
//                R.id.action_createReviewFragment_to_createReviewSuccessFragment
//            )
            onConfirmReview()
        }
        binding.etReview.onFocusChangeListener = focusChangeListener
    }

    private fun onConfirmReview() {
        if (binding.etReview.text.isNullOrEmpty() || (binding.etReview.text?.length ?: 0) < 12){
            binding.etReview.error = "Please leave a review here, min length at least 12 characters"
        }else{
            binding.etReview.error = null
            showConfirmDialog(viewModel.reviewId, binding.ratingBar.rating, binding.etReview.text.toString())
        }
    }

    private fun showConfirmDialog(reviewId: String?, rating: Float, text: String) {
        context?.let {
            MaterialAlertDialogBuilder(it)
                .setTitle(getString(R.string.confirm_review_dialog_title_text))
                .setMessage(getString(R.string.confirm_review_dialog_message_text))
                .setNegativeButton(getString(R.string.pro_cat_dialog_cancel_btn)) { dialog, _ ->
                    dialog.cancel()
                }
                .setPositiveButton(getString(R.string.dialog_confirm_btn_text)) { dialog, _ ->
                    viewModel.updateReview(reviewId, rating.toInt(), text)
                }
                .show()
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
        viewModel.review.observe(viewLifecycleOwner){
            it?.let {
                showDialogSuccess()
            }
        }
        viewModel.error.observe(viewLifecycleOwner){
            it?.let {
                binding.loaderLayout.circularLoader.hideAnimationBehavior
                binding.loaderLayout.loaderFrameLayout.visibility = View.GONE
                handleError(it)
            }
        }
    }

    private fun showDialogSuccess() {
        binding.etReview.onFocusChangeListener = null

        findNavController().navigate(
            R.id.action_updateReviewFragment_to_createReviewSuccessFragment
        )
    }

    override fun setViewBinding(): FragmentUpdateReviewBinding {
        return FragmentUpdateReviewBinding.inflate(layoutInflater)
    }
}
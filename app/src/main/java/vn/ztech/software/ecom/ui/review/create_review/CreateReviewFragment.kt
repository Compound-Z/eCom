package vn.ztech.software.ecom.ui.review.create_review

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.core.net.toUri
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import org.koin.androidx.viewmodel.ext.android.viewModel
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import vn.ztech.software.ecom.R
import vn.ztech.software.ecom.databinding.FragmentCreateReviewBinding
import vn.ztech.software.ecom.ui.BaseFragment2


class CreateReviewFragment : BaseFragment2<FragmentCreateReviewBinding>() {
    private val viewModel: CreateReviewViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.takeIf { it.containsKey("productId") }?.let {
            viewModel.productId = arguments?.getString("productId")
        }
        arguments?.takeIf { it.containsKey("reviewQueueId") }?.let {
            viewModel.reviewQueueId = arguments?.getString("reviewQueueId")
        }
        arguments?.takeIf { it.containsKey("imageUrl") }?.let {
            viewModel.imageUrl = arguments?.getString("imageUrl")
        }
        arguments?.takeIf { it.containsKey("productName") }?.let {
            viewModel.productName = arguments?.getString("productName")
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
            showConfirmDialog(binding.ratingBar.rating, binding.etReview.text.toString())
        }
    }

    private fun showConfirmDialog(rating: Float, text: String) {
        context?.let {
            MaterialAlertDialogBuilder(it)
                .setTitle(getString(R.string.confirm_review_dialog_title_text))
                .setMessage(getString(R.string.confirm_review_dialog_message_text))
                .setNegativeButton(getString(R.string.pro_cat_dialog_cancel_btn)) { dialog, _ ->
                    dialog.cancel()
                }
                .setPositiveButton(getString(R.string.dialog_confirm_btn_text)) { dialog, _ ->
                    viewModel.createReview(viewModel.productId, viewModel.reviewQueueId, rating.toInt(), text)
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
            R.id.action_createReviewFragment_to_createReviewSuccessFragment
        )
    }

    override fun setViewBinding(): FragmentCreateReviewBinding {
        return FragmentCreateReviewBinding.inflate(layoutInflater)
    }
}
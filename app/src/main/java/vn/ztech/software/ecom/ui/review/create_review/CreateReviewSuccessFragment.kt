package vn.ztech.software.ecom.ui.review.create_review

import android.os.Bundle
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import vn.ztech.software.ecom.R
import vn.ztech.software.ecom.databinding.FragmentCreateReviewSuccessBinding
import vn.ztech.software.ecom.model.OrderDetails
import vn.ztech.software.ecom.ui.BaseFragment

class CreateReviewSuccessFragment : BaseFragment<FragmentCreateReviewSuccessBinding>() {
    var orderDetails: OrderDetails? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
    override fun setUpViews() {
        super.setUpViews()
        binding.btOkey.setOnClickListener {
            findNavController().navigate(
                R.id.action_createReviewSuccessFragment_to_myReviewFragment,
            )
        }
    }
    override fun setViewBinding(): FragmentCreateReviewSuccessBinding {
        return FragmentCreateReviewSuccessBinding.inflate(layoutInflater)
    }
}
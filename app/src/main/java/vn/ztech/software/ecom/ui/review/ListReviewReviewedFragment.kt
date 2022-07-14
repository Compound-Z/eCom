package vn.ztech.software.ecom.ui.review

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import vn.ztech.software.ecom.R
import vn.ztech.software.ecom.databinding.FragmentListReviewReviewedBinding
import vn.ztech.software.ecom.ui.BaseFragment2
import vn.ztech.software.ecom.util.CustomError
import vn.ztech.software.ecom.util.extension.getFirstNumber

class ListReviewReviewedFragment : BaseFragment2<FragmentListReviewReviewedBinding>() {
    private val viewModel: ListReviewReviewedViewModel by viewModel()
    lateinit var adapter: ListReviewReviewedAdapter
    interface OnClickListener{
        fun onClickProduct(productId: String)
    }
    lateinit var listener: OnClickListener
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.getReviews("REVIEWED")
    }

    override fun setUpViews() {
        super.setUpViews()
        setupAdapter()
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
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.reviews.observe(viewLifecycleOwner){
                it?.let {
                    binding.listReviews.adapter?.apply {
                        adapter.submitData(viewLifecycleOwner.lifecycle,it)
                    }
                }
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

    private fun setupAdapter() {
        adapter = ListReviewReviewedAdapter(requireContext(), object : ListReviewReviewedAdapter.OnClickListener{
            override fun onClick(productId: String) {
               listener.onClickProduct(productId)
            }
        })
        adapter.stateRestorationPolicy = RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY
        binding.listReviews.adapter = adapter
        adapter.addLoadStateListener {loadState->
            // show empty list
            if (loadState.refresh is androidx.paging.LoadState.Loading ||
                loadState.append is androidx.paging.LoadState.Loading){
                binding.loaderLayout.circularLoader.showAnimationBehavior
                binding.loaderLayout.loaderFrameLayout.visibility = View.VISIBLE
            }
            else {
                binding.loaderLayout.circularLoader.hideAnimationBehavior
                binding.loaderLayout.loaderFrameLayout.visibility = View.GONE

                // If we have an error, show a toast
                val errorState = when {
                    loadState.append is androidx.paging.LoadState.Error -> loadState.append as androidx.paging.LoadState.Error
                    loadState.prepend is androidx.paging.LoadState.Error ->  loadState.prepend as androidx.paging.LoadState.Error
                    loadState.refresh is androidx.paging.LoadState.Error -> loadState.refresh as androidx.paging.LoadState.Error
                    else -> null
                }
                errorState?.let {
                    handleError(CustomError(it.error, it.error.message?:"System error"))
                }

            }
        }
    }
    override fun onAttach(context: Context) {
        super.onAttach(context)
        listener = parentFragment as OnClickListener
    }
    override fun setViewBinding(): FragmentListReviewReviewedBinding {
        return FragmentListReviewReviewedBinding.inflate(layoutInflater)
    }
}
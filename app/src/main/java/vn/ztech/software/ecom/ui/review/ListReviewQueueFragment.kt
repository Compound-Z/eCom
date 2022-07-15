package vn.ztech.software.ecom.ui.review

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import vn.ztech.software.ecom.databinding.FragmentListReviewQueueBinding
import vn.ztech.software.ecom.ui.BaseFragment2
import vn.ztech.software.ecom.util.CustomError

class ListReviewQueueFragment() : BaseFragment2<FragmentListReviewQueueBinding>(){
    private val viewModel: ListReviewQueueViewModel by viewModel()
    lateinit var adapter: ListReviewQueueAdapter
    interface OnClickListener{
        fun onClickProduct(productId: String)
        fun onClickCreateReview(productId: String, reviewQueueId: String, imageUrl: String, productName: String)
    }
    lateinit var listener: OnClickListener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.getReviews("")
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
                    viewModel.existed = true
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
        adapter = ListReviewQueueAdapter(requireContext(), object : ListReviewQueueAdapter.OnClickListener{
            override fun onClick(productId: String) {
                listener.onClickProduct(productId)
            }

            override fun onClickWriteReview(productId: String, reviewQueueId: String, imageUrl: String, productName: String) {
                listener.onClickCreateReview(productId, reviewQueueId, imageUrl, productName)
//
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

    override fun onResume() {
        super.onResume()
        if (viewModel.existed) viewModel.getReviews("",isLoadingEnabled = false)
    }
    override fun onAttach(context: Context) {
        super.onAttach(context)
        listener = parentFragment as OnClickListener
    }
    override fun setViewBinding(): FragmentListReviewQueueBinding {
        return FragmentListReviewQueueBinding.inflate(layoutInflater)
    }

}
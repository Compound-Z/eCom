package vn.ztech.software.ecom.ui.review

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import androidx.viewpager.widget.ViewPager
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayoutMediator
import vn.ztech.software.ecom.R
import vn.ztech.software.ecom.databinding.FragmentMyReviewBinding
import vn.ztech.software.ecom.ui.BaseFragment2
import org.koin.androidx.viewmodel.ext.android.viewModel


class MyReviewFragment : BaseFragment2<FragmentMyReviewBinding>(), ListReviewQueueFragment.OnClickListener, ListReviewReviewedFragment.OnClickListener {
    private lateinit var listReviewFragmentAdapter: ListReviewFragmentAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setUpUI()
    }

    private fun setUpUI() {
        listReviewFragmentAdapter = ListReviewFragmentAdapter(this@MyReviewFragment)
        binding.pager.adapter = listReviewFragmentAdapter
        TabLayoutMediator(binding.tabLayout, binding.pager) {tab, pos->
            if(pos==0) tab.text =  "To review"
            else tab.text =  "Reviewed"
        }.attach()
    }
    override fun setUpViews() {
        super.setUpViews()
        binding.topAppBarListReview.topAppBar.title = "List reviews queue"
        binding.topAppBarListReview.topAppBar.setNavigationOnClickListener{
            findNavController().navigateUp()
        }
    }

    override fun observeView() {
        super.observeView()
    }

    class ListReviewFragmentAdapter(fragment: MyReviewFragment) : FragmentStateAdapter(fragment) {

        override fun getItemCount(): Int {
            return 2
        }

        override fun createFragment(position: Int): Fragment {
            if(position == 1){
                val fragment = ListReviewReviewedFragment()
                return fragment
            }else{
                /**should return list review queue*/
                val fragment = ListReviewQueueFragment()
                return fragment
            }

        }
    }

    override fun setViewBinding(): FragmentMyReviewBinding {
        return FragmentMyReviewBinding.inflate(layoutInflater)
    }

    override fun onClickProduct(productId: String) {
        findNavController().navigate(
            R.id.action_myReviewFragment_to_productDetailsFragment,
            bundleOf(
                "productId" to productId
            )
        )
    }

    override fun onClickCreateReview(productId: String, reviewQueueId: String, imageUrl: String, productName: String) {
        findNavController().navigate(
                    R.id.action_myReviewFragment_to_createReviewFragment,
                    bundleOf(
                        "productId" to productId,
                        "reviewQueueId" to reviewQueueId,
                        "imageUrl" to imageUrl,
                        "productName" to productName,
                    )
        )
    }
}
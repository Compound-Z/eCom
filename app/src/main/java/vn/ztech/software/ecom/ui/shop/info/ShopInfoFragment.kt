package vn.ztech.software.ecom.ui.shop.info

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import vn.ztech.software.ecom.R
import vn.ztech.software.ecom.databinding.FragmentShopInfoBinding
import vn.ztech.software.ecom.model.Shop
import vn.ztech.software.ecom.ui.BaseFragment2
import org.koin.androidx.viewmodel.ext.android.viewModel
import vn.ztech.software.ecom.ui.shop.ShopViewModel
import vn.ztech.software.ecom.util.extension.toYear


class ShopInfoFragment : BaseFragment2<FragmentShopInfoBinding>() {
    val viewModel: ShopInfoViewModel by viewModel()
    val viewModelShop: ShopViewModel by sharedViewModel()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

            viewModel.shop.value = viewModelShop.shop.value
    }
    override fun setViewBinding(): FragmentShopInfoBinding {
        return FragmentShopInfoBinding.inflate(layoutInflater)
    }

    override fun setUpViews() {
        super.setUpViews()
        viewModel.shop.value?.let {
            updateUI()
        }
    }

    private fun updateUI() {
        binding.tvFromWhenContent.text = viewModel.shop.value?.createdAt?.toYear()
        binding.tvNumberOfProductContent.text = viewModel.shop.value?.numberOfProduct?.toString()
        binding.tvDescriptionContent.text = viewModel.shop.value?.description
    }

    override fun observeView() {
        super.observeView()
    }

}
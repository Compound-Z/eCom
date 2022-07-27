package vn.ztech.software.ecom.ui.shop.info

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import vn.ztech.software.ecom.R
import vn.ztech.software.ecom.databinding.FragmentShopInfoBinding
import vn.ztech.software.ecom.model.Shop
import vn.ztech.software.ecom.ui.BaseFragment2


class ShopInfoFragment : BaseFragment2<FragmentShopInfoBinding>() {
    var shop: Shop? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.takeIf { it.containsKey("shop") }?.let {
            shop = arguments?.getParcelable<Shop>("shop")
        }
    }
    override fun setViewBinding(): FragmentShopInfoBinding {
        return FragmentShopInfoBinding.inflate(layoutInflater)
    }

    override fun setUpViews() {
        super.setUpViews()
        shop?.let {
            updateUI()
        }
    }

    private fun updateUI() {
        TODO("Not yet implemented")
    }

    override fun observeView() {
        super.observeView()
    }

}
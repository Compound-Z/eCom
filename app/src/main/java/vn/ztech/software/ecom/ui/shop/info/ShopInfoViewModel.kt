package vn.ztech.software.ecom.ui.shop.info

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import vn.ztech.software.ecom.model.Shop

class ShopInfoViewModel: ViewModel() {
    val shop = MutableLiveData<Shop>()
}
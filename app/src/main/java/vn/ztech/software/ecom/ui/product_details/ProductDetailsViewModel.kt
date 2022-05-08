package vn.ztech.software.ecom.ui.product_details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import vn.ztech.software.ecom.common.LoadState
import vn.ztech.software.ecom.common.StoreDataStatus
import vn.ztech.software.ecom.common.extension.toLoadState
import vn.ztech.software.ecom.domain.model.ProductDetails
import vn.ztech.software.ecom.domain.use_case.get_product_details.IProductDetailsUseCase;

private const val TAG = "ProductViewModel"

class ProductDetailsViewModel(
    private val productDetailsUseCase: IProductDetailsUseCase
    ) : ViewModel(){
    private val _productData = MutableLiveData<ProductDetails?>()
    val productData: LiveData<ProductDetails?> get() = _productData

    private val _storeDataStatus = MutableLiveData<StoreDataStatus>()
    val storeDataStatus: LiveData<StoreDataStatus> get() = _storeDataStatus

//    private val _errorStatus = MutableLiveData<List<AddItemErrors>>()
//    val errorStatus: LiveData<List<AddItemErrors>> get() = _errorStatus

    fun getProductDetails(id: String) {
        viewModelScope.launch {
            productDetailsUseCase.getProductDetails(id).flowOn(Dispatchers.IO).toLoadState().collect {
                when(it){
                    LoadState.Loading -> {
                        _storeDataStatus.value = StoreDataStatus.LOADING
                    }
                    is LoadState.Loaded -> {
                        _storeDataStatus.value = StoreDataStatus.DONE
                        _productData.value = it.data
                    }
                    is LoadState.Error -> {
                        _storeDataStatus.value = StoreDataStatus.ERROR
                        _productData.value = ProductDetails()
                    }
                }
            }
        }
    }
}
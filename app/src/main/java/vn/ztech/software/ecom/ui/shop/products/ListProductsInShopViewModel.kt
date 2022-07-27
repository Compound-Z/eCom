package vn.ztech.software.ecom.ui.shop.products

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import vn.ztech.software.ecom.common.LoadState
import vn.ztech.software.ecom.common.StoreDataStatus
import vn.ztech.software.ecom.common.extension.toLoadState
import vn.ztech.software.ecom.model.Product
import vn.ztech.software.ecom.util.CustomError
import vn.ztech.software.ecom.util.errorMessage
private const val TAG = "ListProductsInShopViewModel"
class ListProductsInShopViewModel(
    private val listProductsInShopUseCase: IListProductsInShopUseCase
): ViewModel() {
    private var _allProducts = MutableLiveData<PagingData<Product>>()
    val allProducts: LiveData<PagingData<Product>> get() = _allProducts

    private val _storeDataStatus = MutableLiveData<StoreDataStatus>()
    val storeDataStatus: LiveData<StoreDataStatus> get() = _storeDataStatus

    val error = MutableLiveData<CustomError>()

    fun getProducts(shopId: String?){
        if (shopId == null){
            error.value = errorMessage(CustomError(customMessage = "System error"))
            return
        }
        viewModelScope.launch {
            listProductsInShopUseCase.getListProductsInShop(shopId).cachedIn(viewModelScope).flowOn(Dispatchers.IO).toLoadState().collect {
                when(it){
                    LoadState.Loading -> {
                        _storeDataStatus.value = StoreDataStatus.LOADING
                    }
                    is LoadState.Loaded -> {
                        _storeDataStatus.value = StoreDataStatus.DONE
                        _allProducts.value = it.data
                        Log.d(TAG, "LOADED")
                    }
                    is LoadState.Error -> {
                        _storeDataStatus.value = StoreDataStatus.ERROR
                        error.value = errorMessage(it.e)
                        Log.d(TAG +" ERROR:", it.e.message.toString())
                    }
                }
            }
        }
    }

//    fun search(searchWords: String){
//        viewModelScope.launch {
//            listProductsUseCase.search(searchWords).flowOn(Dispatchers.IO).toLoadState().collect {
//                when(it){
//                    LoadState.Loading -> {
//                        _storeDataStatus.value = StoreDataStatus.LOADING
//                    }
//                    is LoadState.Loaded -> {
//                        _storeDataStatus.value = StoreDataStatus.DONE
//                        _allProducts.value = it.data
//                        Log.d(TAG, "SEARCH LOADED")
//                    }
//                    is LoadState.Error -> {
//                        _storeDataStatus.value = StoreDataStatus.ERROR
//                        error.value = errorMessage(it.e)
//                        Log.d(TAG +" SEARCH ERROR:", it.e.message.toString())
//                    }
//                }
//            }
//        }
//    }

    fun clearErrors() {
        error.value = null
    }
}
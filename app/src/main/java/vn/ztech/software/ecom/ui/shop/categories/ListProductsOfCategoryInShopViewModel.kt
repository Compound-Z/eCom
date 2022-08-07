package vn.ztech.software.ecom.ui.shop.categories

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
import vn.ztech.software.ecom.model.Category
import vn.ztech.software.ecom.model.Product
import vn.ztech.software.ecom.util.CustomError
import vn.ztech.software.ecom.util.errorMessage


class ListProductsOfCategoryInShopViewModel(
    private val listProductsOfCategoryInShopUseCase: IListProductsOfCategoryInShopUseCase
): ViewModel() {
    private val TAG = "ListProductsOfCategoryInShopViewModel"
    val currentSelectedCategory = MutableLiveData<Category>()
    val products = MutableLiveData<PagingData<Product>>()

    private val _storeDataStatus = MutableLiveData<StoreDataStatus>()
    val storeDataStatus: LiveData<StoreDataStatus> get() = _storeDataStatus

    val error = MutableLiveData<CustomError>()

    fun getProductsOfCategoryInShop(shopId: String?, categoryName: String?){
        if (shopId == null){
            error.value = errorMessage(CustomError(customMessage = "System error"))
            return
        }
        if (categoryName == null){
            error.value = errorMessage(CustomError(customMessage = "System error"))
            return
        }
        viewModelScope.launch {
            listProductsOfCategoryInShopUseCase.getListProductsOfCategoryInShop(shopId, categoryName).cachedIn(viewModelScope).flowOn(
                Dispatchers.IO).toLoadState().collect {
                when(it){
                    LoadState.Loading -> {
                        _storeDataStatus.value = StoreDataStatus.LOADING
                    }
                    is LoadState.Loaded -> {
                        _storeDataStatus.value = StoreDataStatus.DONE
                        products.value = it.data
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

    fun searchProductsOfCategoryInShop(shopId: String?, searchWordsCategory: String?, searchWordsProduct: String?){
        if (shopId == null || searchWordsCategory == null || searchWordsProduct == null){
            error.value = errorMessage(CustomError(customMessage = "System error"))
            return
        }
        viewModelScope.launch {
            listProductsOfCategoryInShopUseCase.searchListProductsOfCategoryInShop(shopId, searchWordsCategory, searchWordsProduct).cachedIn(viewModelScope).flowOn(Dispatchers.IO).toLoadState().collect {
                when(it){
                    LoadState.Loading -> {
                        _storeDataStatus.value = StoreDataStatus.LOADING
                    }
                    is LoadState.Loaded -> {
                        _storeDataStatus.value = StoreDataStatus.DONE
                        products.value = it.data
                        Log.d(TAG, "SEARCH LOADED")
                    }
                    is LoadState.Error -> {
                        _storeDataStatus.value = StoreDataStatus.ERROR
                        error.value = errorMessage(it.e)
                        Log.d(TAG +" SEARCH ERROR:", it.e.message.toString())
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
//                        products.value = it.data
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
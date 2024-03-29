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
import vn.ztech.software.ecom.model.ProductDetails
import vn.ztech.software.ecom.domain.use_case.get_product_details.IProductDetailsUseCase;
import vn.ztech.software.ecom.model.Product
import vn.ztech.software.ecom.model.Review
import vn.ztech.software.ecom.ui.home.IListProductUseCase
import vn.ztech.software.ecom.ui.review.IReviewUseCase
import vn.ztech.software.ecom.util.CustomError
import vn.ztech.software.ecom.util.errorMessage

private const val TAG = "ProductViewModel"

class ProductDetailsViewModel(
    private val productDetailsUseCase: IProductDetailsUseCase,
    private val listProductsUseCase: IListProductUseCase,
    private val listReviewUseCase: IReviewUseCase,
    ) : ViewModel(){
    val loadingProduct = MutableLiveData<Boolean>()
    val product = MutableLiveData<Product>()

    private val _productDetails = MutableLiveData<ProductDetails?>()
    val productDetails: LiveData<ProductDetails?> get() = _productDetails
    private val _storeDataStatus = MutableLiveData<StoreDataStatus>()
    val storeDataStatus: LiveData<StoreDataStatus> get() = _storeDataStatus

    val reviews = MutableLiveData<List<Review>>()
    val hasNextPage = MutableLiveData<Boolean>()
    val error = MutableLiveData<CustomError>()

    fun getProductDetails(id: String) {
        viewModelScope.launch {
            productDetailsUseCase.getProductDetails(id).flowOn(Dispatchers.IO).toLoadState().collect {
                when(it){
                    LoadState.Loading -> {
                        _storeDataStatus.value = StoreDataStatus.LOADING
                    }
                    is LoadState.Loaded -> {
                        _storeDataStatus.value = StoreDataStatus.DONE
                        _productDetails.value = it.data
                    }
                    is LoadState.Error -> {
                        _storeDataStatus.value = StoreDataStatus.ERROR
                        _productDetails.value = null
                        error.value = errorMessage(it.e)

                    }
                }
            }
        }
    }
    fun getProduct(productId: String) {
        viewModelScope.launch {
            listProductsUseCase.getOneProduct(productId).flowOn(Dispatchers.IO).toLoadState().collect {
                when(it){
                    LoadState.Loading -> {
                        loadingProduct.value = true
                    }
                    is LoadState.Loaded -> {
                        loadingProduct.value = false
                        product.value = it.data
                    }
                    is LoadState.Error -> {
                        loadingProduct.value = false
                        error.value = errorMessage(it.e)

                    }
                }
            }
        }
    }

    fun getReviewsOfThisProduct(productId: String) {
        viewModelScope.launch {
            listReviewUseCase.getListReviewPreviewOfAProduct(productId, null).flowOn(Dispatchers.IO).toLoadState().collect {
                when(it){
                    LoadState.Loading -> {
                        loadingProduct.value = true
                    }
                    is LoadState.Loaded -> {
                        loadingProduct.value = false
                        hasNextPage.value = it.data.hasNextPage
                        reviews.value = it.data.docs
                    }
                    is LoadState.Error -> {
                        loadingProduct.value = false
                        error.value = errorMessage(it.e)
                    }
                }
            }
        }
    }
    fun clearErrors() {
        error.value = null
    }

    fun checkIsDataReady(): Boolean {
        return (product.value!=null && productDetails.value!=null)
    }
}
package vn.ztech.software.ecom.ui.review.create_review

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
import vn.ztech.software.ecom.common.extension.toLoadState
import vn.ztech.software.ecom.model.Review
import vn.ztech.software.ecom.model.ReviewQueue
import vn.ztech.software.ecom.ui.review.IReviewUseCase
import vn.ztech.software.ecom.ui.review.ReviewUseCase
import vn.ztech.software.ecom.util.CustomError
import vn.ztech.software.ecom.util.errorMessage

class CreateReviewViewModel(private val reviewUseCase: IReviewUseCase): ViewModel() {
    val loading = MutableLiveData<Boolean>()
    val review = MutableLiveData<Review>()
    var productId: String? = null
    var reviewQueueId: String? = null
    var imageUrl: String? = null
    var productName: String? = null
    val error = MutableLiveData<CustomError>()

    fun createReview(productId: String?, reviewQueueId: String?, rating: Int, reviewContent: String, isLoadingEnabled: Boolean = true) {
        if(productId==null || reviewQueueId==null){
            error.value = errorMessage(CustomError(customMessage = "System error!"))
            return
        }
        viewModelScope.launch {
            reviewUseCase.createReview(productId, reviewQueueId, rating, reviewContent).flowOn(Dispatchers.IO).toLoadState().collect {
                when (it) {
                    LoadState.Loading -> {
                        if (isLoadingEnabled) loading.value = true
                    }
                    is LoadState.Loaded -> {
                        review.value = it.data
                        loading.value = false
                    }
                    is LoadState.Error -> {
                        loading.value = false
                        error.value = errorMessage(it.e)
                    }
                }
            }
        }
    }
}
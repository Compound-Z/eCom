package vn.ztech.software.ecom.ui.review

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
import vn.ztech.software.ecom.util.CustomError
import vn.ztech.software.ecom.util.errorMessage

class ListReviewReviewedViewModel(val reviewUseCase: IReviewUseCase): ViewModel() {
    val loading = MutableLiveData<Boolean>()
    val reviews = MutableLiveData<PagingData<ReviewQueue>>()
    val error = MutableLiveData<CustomError>()
    fun getReviews(filter: String, isLoadingEnabled: Boolean = true) {
        viewModelScope.launch {
            reviewUseCase.getMyReviewQueue(filter).cachedIn(viewModelScope).flowOn(Dispatchers.IO).toLoadState().collect {
                when (it) {
                    LoadState.Loading -> {
                        if (isLoadingEnabled) loading.value = true
                    }
                    is LoadState.Loaded -> {
                        reviews.value = it.data
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
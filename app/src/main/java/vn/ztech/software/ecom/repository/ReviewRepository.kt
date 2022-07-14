package vn.ztech.software.ecom.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import vn.ztech.software.ecom.api.IReviewApi
import vn.ztech.software.ecomSeller.api.request.CreateReviewRequest
import vn.ztech.software.ecom.api.request.GetMyReviewQueueRequest
import vn.ztech.software.ecomSeller.api.request.GetReviewsRequest
import vn.ztech.software.ecomSeller.api.request.UpdateReviewRequest
import vn.ztech.software.ecom.api.response.PagedGetReviewResponse
import vn.ztech.software.ecom.common.Constants
import vn.ztech.software.ecom.model.Review
import vn.ztech.software.ecom.model.ReviewQueue
import vn.ztech.software.ecomSeller.repository.ReviewOfAProductPagingSource
import vn.ztech.software.ecomSeller.repository.ReviewPagingSource

interface IReviewRepository {
    suspend fun getAllReview(starFilter: Int?): Flow<PagingData<Review>>
    suspend fun getListReviewOfAProduct(productId: String, startFilter: Int?): Flow<PagingData<Review>>
    suspend fun getListReviewPreviewOfAProduct(productId: String, starFilter: Int?): PagedGetReviewResponse
    suspend fun getMyReviewQueue(filter: String): Flow<PagingData<ReviewQueue>>
    suspend fun createReview(productId: String, reviewQueueId: String, rating: Int, content: String): Review
    suspend fun updateReview(reviewId: String, rating: Int, content: String): Review
}

class ReviewRepository(private val reviewApi: IReviewApi): IReviewRepository {
    override suspend fun getAllReview(starFilter: Int?): Flow<PagingData<Review>> {
        return Pager(
            config = PagingConfig(
                pageSize = Constants.NETWORK_PAGE_SIZE,
                enablePlaceholders = false,
            ),
            pagingSourceFactory = {
                ReviewPagingSource(GetReviewsRequest(starFilter = starFilter),reviewApi)
            },
            initialKey = 1
        ).flow
    }

    override suspend fun getListReviewOfAProduct(
        productId: String,
        starFilter: Int?
    ): Flow<PagingData<Review>> {
        return Pager(
            config = PagingConfig(
                pageSize = Constants.NETWORK_PAGE_SIZE,
                enablePlaceholders = false,
            ),
            pagingSourceFactory = {
                ReviewOfAProductPagingSource(productId, GetReviewsRequest(starFilter = starFilter),reviewApi)
            },
            initialKey = 1
        ).flow
    }

    override suspend fun getListReviewPreviewOfAProduct(
        productId: String,
        starFilter: Int?
    ): PagedGetReviewResponse {
        return reviewApi.getListReviewOfAProduct(productId, GetReviewsRequest(starFilter = starFilter, pageSize = Constants.REVIEW_PREVIEW_PAGE_SIZE))
    }


    override suspend fun getMyReviewQueue(filter: String): Flow<PagingData<ReviewQueue>> {
        return Pager(
            config = PagingConfig(
                pageSize = Constants.NETWORK_PAGE_SIZE,
                enablePlaceholders = false,
            ),
            pagingSourceFactory = {
                ReviewQueuePagingSource(GetMyReviewQueueRequest(filter),reviewApi)
            },
            initialKey = 1
        ).flow
    }

    override suspend fun createReview(
        productId: String,
        reviewQueueId: String,
        rating: Int,
        content: String
    ): Review {
        return reviewApi.createReview(CreateReviewRequest(reviewQueueId = reviewQueueId, productId = productId, rating = rating, content = content))
    }

    override suspend fun updateReview(reviewId: String, rating: Int, content: String): Review {
        return reviewApi.editReview(reviewId, UpdateReviewRequest(content = content, rating = rating))
    }
}
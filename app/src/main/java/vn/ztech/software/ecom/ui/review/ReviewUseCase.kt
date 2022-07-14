package vn.ztech.software.ecom.ui.review

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import vn.ztech.software.ecom.model.Review
import vn.ztech.software.ecom.model.ReviewQueue
import vn.ztech.software.ecom.api.response.PagedGetReviewResponse
import vn.ztech.software.ecom.repository.IReviewRepository


interface IReviewUseCase{
    suspend fun getAllReview(starFilter: Int?): Flow<PagingData<Review>>
    suspend fun getListReviewOfAProduct(productId: String, startFilter: Int?): Flow<PagingData<Review>>
    suspend fun getMyReviewQueue(startFilter: String): Flow<PagingData<ReviewQueue>>
    suspend fun createReview(productId: String, reviewQueueId: String, rating: Int, content: String): Review
    suspend fun updateReview(reviewId: String, rating: Int, content: String): Review
    suspend fun getListReviewPreviewOfAProduct(productId: String, starFilter: Int?): Flow<PagedGetReviewResponse>
}

class ReviewUseCase( private val reviewRepository: IReviewRepository): IReviewUseCase {
    override suspend fun getAllReview(starFilter: Int?): Flow<PagingData<Review>>
    = reviewRepository.getAllReview(starFilter)
    override suspend fun getListReviewOfAProduct(
        productId: String,
        starFilter: Int?
    ) = reviewRepository.getListReviewOfAProduct(productId, starFilter)

    override suspend fun getMyReviewQueue(starFilter: String): Flow<PagingData<ReviewQueue>>
    = reviewRepository.getMyReviewQueue(starFilter)

    override suspend fun createReview(
        productId: String,
        reviewQueueId: String,
        rating: Int,
        content: String
    ): Review {
        return reviewRepository.createReview(productId,reviewQueueId,rating,content)
    }

    override suspend fun updateReview(reviewId: String, rating: Int, content: String): Review {
        return reviewRepository.updateReview(reviewId,rating,content)
    }

    override suspend fun getListReviewPreviewOfAProduct(
        productId: String,
        starFilter: Int?
    ): Flow<PagedGetReviewResponse> = flow {
        emit(reviewRepository.getListReviewPreviewOfAProduct(productId = productId, starFilter = starFilter))
    }

}
package vn.ztech.software.ecom.api

import androidx.annotation.Keep
import retrofit2.http.*
import vn.ztech.software.ecom.api.request.GetMyReviewQueueRequest
import vn.ztech.software.ecom.api.response.PagedGetReviewResponse
import vn.ztech.software.ecom.model.Review
import vn.ztech.software.ecomSeller.api.request.*
import vn.ztech.software.ecomSeller.api.response.PagedGetMyReviewQueueResponse

@Keep
interface IReviewApi{
    @POST("/api/v1/reviews/all")
    suspend fun getAllReview(@Body request: GetReviewsRequest): PagedGetReviewResponse

    @POST("/api/v1/reviews/my-review-queue")
    suspend fun getMyReviewQueue(@Body request: GetMyReviewQueueRequest): PagedGetMyReviewQueueResponse

    @POST("/api/v1/reviews")
    suspend fun createReview(@Body request: CreateReviewRequest): Review

    @PATCH("/api/v1/reviews/{reviewId}")
    suspend fun editReview(@Path("reviewId") reviewId: String, @Body request: UpdateReviewRequest): Review

    @POST("/api/v1/reviews/{productId}")
    suspend fun getListReviewOfAProduct(@Path("productId") productId: String, @Body request: GetReviewsRequest): PagedGetReviewResponse

}
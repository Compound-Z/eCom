package vn.ztech.software.ecom.ui.review

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import vn.ztech.software.ecom.databinding.ItemReviewReviewedBinding
import vn.ztech.software.ecom.model.ReviewQueue
import vn.ztech.software.ecom.util.extension.toDateTimeString

class ListReviewReviewedAdapter(val context: Context,
                                val onClickListener: OnClickListener
) : PagingDataAdapter<ReviewQueue, ListReviewReviewedAdapter.ViewHolder>(ReviewQueueComparator) {

    inner class ViewHolder(private val binding: ItemReviewReviewedBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(review: ReviewQueue) {
            review.reviewRef?.let {
                binding.tvUserName.text = review.reviewRef.userName
                binding.tvReviewContent.text = review.reviewRef.content
                binding.ratingBar.rating = review.reviewRef.rating.toFloat()
                binding.btEdit.visibility = if(it.isEdited) View.GONE else View.VISIBLE
            }
            binding.tvProductName.text = review.productName
            binding.tvDateTime.text = review.updatedAt.toDateTimeString()

            if (review.imageUrl.isNotEmpty()) {
                val imgUrl = review.imageUrl.toUri().buildUpon().scheme("https").build()
                Glide.with(context)
                    .asBitmap()
                    .load(imgUrl)
                    .into(binding.ivProduct)
                binding.ivProduct.clipToOutline = true
            }
            binding.ivProduct.setOnClickListener {
                onClickListener.onClick(review.productId)
            }
            binding.tvProductName.setOnClickListener {
                onClickListener.onClick(review.productId)
            }
            binding.tvReviewContent.setOnClickListener {
                onClickListener.onClick(review.productId)
            }
            binding.btEdit.setOnClickListener {
                onClickListener.onClickEdit(review.reviewRef?._id?:"", review.imageUrl, review.productName, review.reviewRef?.rating?.toFloat()?:5f, review.reviewRef?.content?:"")
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemReviewReviewedBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position)!!) //todo: should be changed back to ?.let
    }

    interface OnClickListener{
        fun onClick(productId: String)
        fun onClickEdit(reviewId: String, imageUrl: String, productName: String, rating: Float, reviewContent: String)
    }

    object ReviewQueueComparator: DiffUtil.ItemCallback<ReviewQueue>() {
        override fun areItemsTheSame(oldItem: ReviewQueue, newItem: ReviewQueue): Boolean {
            // Id is unique.
            return oldItem._id == newItem._id
        }

        override fun areContentsTheSame(oldItem: ReviewQueue, newItem: ReviewQueue): Boolean {
            return oldItem.reviewRef?.content == newItem.reviewRef?.content
        }
    }
}
package vn.ztech.software.ecom.ui.review

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import vn.ztech.software.ecom.databinding.ItemReviewQueueBinding
import vn.ztech.software.ecom.model.ReviewQueue
import vn.ztech.software.ecom.util.extension.toDateTimeString

class ListReviewQueueAdapter(val context: Context,
                             val onClickListener: OnClickListener
) : PagingDataAdapter<ReviewQueue, ListReviewQueueAdapter.ViewHolder>(ReviewQueueComparator) {

    inner class ViewHolder(private val binding: ItemReviewQueueBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(review: ReviewQueue) {
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

            binding.btWriteReview.setOnClickListener {
                onClickListener.onClickWriteReview(review.productId, review._id, review.imageUrl, review.productName)
            }
           
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemReviewQueueBinding.inflate(
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
        fun onClickWriteReview(productId: String, reviewQueueId: String, imageUrl: String, productName: String)
    }

    object ReviewQueueComparator: DiffUtil.ItemCallback<ReviewQueue>() {
        override fun areItemsTheSame(oldItem: ReviewQueue, newItem: ReviewQueue): Boolean {
            // Id is unique.
            return oldItem._id == newItem._id
        }

        override fun areContentsTheSame(oldItem: ReviewQueue, newItem: ReviewQueue): Boolean {
            return oldItem._id == newItem._id
        }
    }
}
package vn.ztech.software.ecom.ui.home

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.net.toUri
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

//import com.vishalgaur.shoppingapp.data.ShoppingAppSessionManager

import vn.ztech.software.ecom.R
import vn.ztech.software.ecom.databinding.ItemProductListBinding
import vn.ztech.software.ecom.databinding.LayoutHomeAdBinding
import vn.ztech.software.ecom.model.Product

class ListProductsAdapter(private val context: Context) :
	PagingDataAdapter<Product,RecyclerView.ViewHolder>(ProductComparator) {
	lateinit var onClickListener: OnClickListener

	inner class ItemViewHolder(binding: ItemProductListBinding) :
		RecyclerView.ViewHolder(binding.root) {
		private val proName = binding.productNameTv
		private val proPrice = binding.productPriceTv
		private val productCard = binding.productCard
		private val productImage = binding.productImageView
		private val proDeleteButton = binding.productDeleteButton
		private val proEditBtn = binding.productEditButton
//		private val proMrp = binding.productActualPriceTv
//		private val proOffer = binding.productOfferValueTv
		private val proRatingBar = binding.productRatingBar
		private val proLikeButton = binding.productLikeCheckbox
		private val proCartButton = binding.productAddToCartButton
		private val tvSaleNumber = binding.tvSaleNumber

		fun bind(productData: Product) {
			productCard.setOnClickListener {
				onClickListener.onClick(productData)
			}
			proName.text = productData.name
			proPrice.text =
				context.getString(R.string.pro_details_price_value, productData.price.toString())
			proRatingBar.rating = productData.averageRating.toFloat()
			tvSaleNumber.text = "Sold: ${productData.saleNumber.toString()}"
			if (productData.imageUrl.isNotEmpty()) {
				val imgUrl = productData.imageUrl.toUri().buildUpon().scheme("https").build()
				Glide.with(context)
					.asBitmap()
					.load(imgUrl)
					.into(productImage)

				productImage.clipToOutline = true
			}

//feature: this should be uncommented when the app supports seller
//			if (sessionManager.isUserSeller()) {
//				proLikeButton.visibility = View.GONE
//				proCartButton.visibility = View.GONE
//				proEditBtn.setOnClickListener {
//					onClickListener.onEditClick(productData.productId)
//				}
//
//				proDeleteButton.setOnClickListener {
//					onClickListener.onDeleteClick(productData)
//				}
//			} else {
				proEditBtn.visibility = View.GONE
				proDeleteButton.visibility = View.GONE

		}
	}

	inner class AdViewHolder(binding: LayoutHomeAdBinding) : RecyclerView.ViewHolder(binding.root) {
		val adImageView: ImageView = binding.adImageView
	}

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
		return when (viewType) {
			VIEW_TYPE_AD -> AdViewHolder(
				LayoutHomeAdBinding.inflate(
					LayoutInflater.from(parent.context),
					parent,
					false
				)
			)
			else -> ItemViewHolder(
				ItemProductListBinding.inflate(
					LayoutInflater.from(parent.context),
					parent,
					false
				)
			)
		}
	}

	override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
		when (val proData = getItem(position)) {
//			is Int -> (holder as AdViewHolder).adImageView.setImageResource(proData)
			is Product -> (holder as ItemViewHolder).bind(proData)
		}
	}


	companion object {
		const val VIEW_TYPE_PRODUCT = 1
		const val VIEW_TYPE_AD = 2
	}

	override fun getItemViewType(position: Int): Int {
		return when (getItem(position)) {
//			is Int -> VIEW_TYPE_AD
			is Product -> VIEW_TYPE_PRODUCT
			else -> VIEW_TYPE_PRODUCT
		}
	}

	interface BindImageButtons {
		fun setCartButton(productId: String, imgView: ImageView)
	}

	interface OnClickListener {
		fun onClick(productData: Product)
	}
	object ProductComparator: DiffUtil.ItemCallback<Product>() {
		override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean {
			// Id is unique.
			return oldItem._id == newItem._id
		}

		override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean {
			return oldItem.name == newItem.name
		}
	}
}
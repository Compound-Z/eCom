package vn.ztech.software.ecom.ui.cart

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import vn.ztech.software.ecom.R
import vn.ztech.software.ecom.databinding.CartListItemBinding
import vn.ztech.software.ecom.databinding.LayoutCircularLoaderBinding
import vn.ztech.software.ecom.model.Product


class CartItemAdapter(
	private val context: Context, products: List<Product>
) : RecyclerView.Adapter<CartItemAdapter.ViewHolder>() {

	lateinit var onClickListener: OnClickListener
	var products: List<Product> = products

	inner class ViewHolder(private val binding: CartListItemBinding) :
		RecyclerView.ViewHolder(binding.root) {
		fun bind(product: Product) {
			binding.loaderLayout.loaderFrameLayout.visibility = View.GONE
			binding.cartProductTitleTv.text = product.name
			binding.cartProductPriceTv.text =
				context.getString(R.string.price_text, product.price.toString())
			if (product.imageUrl.isNotEmpty()) {
				val imgUrl = product.imageUrl.toUri().buildUpon().scheme("https").build()
				Glide.with(context)
					.asBitmap()
					.load(imgUrl)
					.into(binding.productImageView)
				binding.productImageView.clipToOutline = true
			}
			binding.cartProductQuantityTextView.text = product.quantity.toString()

			binding.cartProductDeleteBtn.setOnClickListener {
				onClickListener.onDeleteClick(product._id, binding.loaderLayout)
			}
//			binding.cartProductPlusBtn.setOnClickListener {
//				binding.loaderLayout.loaderFrameLayout.visibility = View.VISIBLE
//				onClickListener.onPlusClick(product.itemId)
//			}
//			binding.cartProductMinusBtn.setOnClickListener {
//				binding.loaderLayout.loaderFrameLayout.visibility = View.VISIBLE
//				onClickListener.onMinusClick(product.itemId, product.quantity, binding.loaderLayout)
//			}

		}
	}

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
		return ViewHolder(
			CartListItemBinding.inflate(
				LayoutInflater.from(parent.context), parent, false
			)
		)
	}

	override fun onBindViewHolder(holder: ViewHolder, position: Int) {
		holder.bind(products[position])
	}

	override fun getItemCount() = products.size

	interface OnClickListener {
		fun onDeleteClick(itemId: String, itemBinding: LayoutCircularLoaderBinding)
		fun onPlusClick(itemId: String)
		fun onMinusClick(itemId: String, currQuantity: Int, itemBinding: LayoutCircularLoaderBinding)
	}
}
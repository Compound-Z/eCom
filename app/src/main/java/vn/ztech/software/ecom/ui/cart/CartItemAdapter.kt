package vn.ztech.software.ecom.ui.cart

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import vn.ztech.software.ecom.R
import vn.ztech.software.ecom.api.response.CartProductResponse
import vn.ztech.software.ecom.databinding.CartListItemBinding
import vn.ztech.software.ecom.databinding.ItemShopInCartBinding
import vn.ztech.software.ecom.databinding.LayoutCircularLoaderBinding
import vn.ztech.software.ecom.model.Product
import vn.ztech.software.ecom.model.Shop
import vn.ztech.software.ecom.util.extension.removeUnderline
import vn.ztech.software.ecom.util.extension.toCurrency


class CartItemAdapter(
	private val context: Context, products: List<Any>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

	lateinit var onClickListener: OnClickListener
	var products: List<Any> = products

	inner class ViewHolderProduct(private val binding: CartListItemBinding) :
		RecyclerView.ViewHolder(binding.root) {
		fun bind(product: CartProductResponse) {
			binding.loaderLayout.loaderFrameLayout.visibility = View.GONE
			binding.cartProductTitleTv.text = product.name
			binding.cartProductPriceTv.text = product.price.toCurrency()
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
				onClickListener.onDeleteClick(product.productId, binding.loaderLayout)
			}
			binding.cartProductPlusBtn.setOnClickListener {
				binding.loaderLayout.loaderFrameLayout.visibility = View.VISIBLE
				onClickListener.onPlusClick(product.productId)
			}
			binding.cartProductMinusBtn.setOnClickListener {
				binding.loaderLayout.loaderFrameLayout.visibility = View.VISIBLE
				onClickListener.onMinusClick(product.productId, product.quantity, binding.loaderLayout)

			}

			binding.cartProductTitleTv.setOnClickListener {
				onClickListener.onItemClick(product)
			}
			binding.productImageView.setOnClickListener {
				onClickListener.onItemClick(product)
			}


		}
	}
	inner class ViewHolderShop(private val binding: ItemShopInCartBinding) :
		RecyclerView.ViewHolder(binding.root) {
		fun bind(shop: Shop) {
			binding.tvShopName.text = shop.name.removeUnderline()

			binding.tvShopName.setOnClickListener {
				onClickListener.onShopClick(shop)
			}

		}
	}

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
		return when(viewType){
			VIEW_TYPE_PRODUCT-> ViewHolderProduct(
				CartListItemBinding.inflate(
					LayoutInflater.from(parent.context), parent, false
				)
			)
			VIEW_TYPE_SHOP ->  ViewHolderShop(
				ItemShopInCartBinding.inflate(
					LayoutInflater.from(parent.context), parent, false
				)
			)
			else -> ViewHolderProduct(
				CartListItemBinding.inflate(
					LayoutInflater.from(parent.context), parent, false
				)
			)
		}

	}

	override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
		when(val item = products[position]){
			is CartProductResponse -> (holder as ViewHolderProduct).bind(item)
			is Shop -> (holder as ViewHolderShop).bind(item)
		}
	}

	override fun getItemViewType(position: Int): Int {
		return when(products[position]){
			is CartProductResponse -> VIEW_TYPE_PRODUCT
			is Shop -> VIEW_TYPE_SHOP
			else -> VIEW_TYPE_PRODUCT
		}
	}

	override fun getItemCount() = products.size

	interface OnClickListener {
		fun onItemClick(product: CartProductResponse)
		fun onDeleteClick(itemId: String, itemBinding: LayoutCircularLoaderBinding)
		fun onPlusClick(itemId: String)
		fun onMinusClick(itemId: String, currQuantity: Int, itemBinding: LayoutCircularLoaderBinding)
		fun onShopClick(shop: Shop)
	}

	companion object {
		const val VIEW_TYPE_PRODUCT = 1
		const val VIEW_TYPE_SHOP = 2
	}
}
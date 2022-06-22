package vn.ztech.software.ecom.ui.order

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import vn.ztech.software.ecom.R
import vn.ztech.software.ecom.api.response.CartProductResponse
import vn.ztech.software.ecom.databinding.OrderListItemBinding

class OrderProductsAdapter(
    private val context: Context, val productsArg: List<CartProductResponse>,
) : RecyclerView.Adapter<OrderProductsAdapter.ViewHolder>() {
    var products: List<CartProductResponse> = productsArg

    inner class ViewHolder(private val binding: OrderListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(product: CartProductResponse) {
            binding.loaderLayout.loaderFrameLayout.visibility = View.GONE
            binding.cartProductTitleTv.text = product.name
            binding.cartProductQuantityTv.text = "Quantity: x${product.quantity}"
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
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            OrderListItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(products[position])
    }

    override fun getItemCount() = products.size
}
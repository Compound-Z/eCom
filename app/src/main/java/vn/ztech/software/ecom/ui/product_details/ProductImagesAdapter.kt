package vn.ztech.software.ecom.ui.product_details

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import vn.ztech.software.ecom.databinding.ItemImagesBinding

class ProductImagesAdapter(private val context: Context, private val images: List<String>) :
	RecyclerView.Adapter<ProductImagesAdapter.ViewHolder>() {

	class ViewHolder(binding: ItemImagesBinding) : RecyclerView.ViewHolder(binding.root) {
		val imageView = binding.rcImageView
	}

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
		return ViewHolder(
			ItemImagesBinding.inflate(
				LayoutInflater.from(parent.context),
				parent,
				false
			)
		)
	}

	override fun onBindViewHolder(holder: ViewHolder, position: Int) {
		val imageUrl = images[position]
		val imgUrl = imageUrl.toUri().buildUpon().scheme("https").build()

		Glide.with(context)
			.asBitmap()
			.load(imgUrl)
			.into(holder.imageView)
	}

	override fun getItemCount(): Int = images.size
}
package vn.ztech.software.ecom.ui.category

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import vn.ztech.software.ecom.R
import vn.ztech.software.ecom.databinding.ItemCategoryListBinding
import vn.ztech.software.ecom.databinding.LayoutHomeAdBinding
import vn.ztech.software.ecom.model.Category
import vn.ztech.software.ecom.ui.home.ListProductsAdapter
import vn.ztech.software.ecom.util.extension.removeUnderline

class ListCategoriesAdapter(categoryList: List<Any>, private val context: Context) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var data = categoryList

    lateinit var onClickListener: OnClickListener

    inner class ItemViewHolder(binding: ItemCategoryListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        private val categoryName = binding.categoryNameTv
        private val productCard = binding.categoryCard
        private val categoryImage = binding.categoryImageView

        fun bind(categoryData: Category) {
            productCard.setOnClickListener {
                onClickListener.onClick(categoryData)
            }
            categoryName.text = categoryData.name.removeUnderline()

            if (categoryData.imageUrl.isNotEmpty()) {
                val imgUrl = categoryData.imageUrl.toUri().buildUpon().scheme("https").build()
                Glide.with(context)
                    .asBitmap()
                    .load(imgUrl)
                    .into(categoryImage)
                categoryImage.clipToOutline = true
            }

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
                ItemCategoryListBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (val categoryData = data[position]) {
            is Int -> (holder as AdViewHolder).adImageView.setImageResource(categoryData)
            is Category -> (holder as ItemViewHolder).bind(categoryData)
        }
    }

    override fun getItemCount(): Int = data.size

    companion object {
        const val VIEW_TYPE_CATEGORY = 1
        const val VIEW_TYPE_AD = 2
    }

    override fun getItemViewType(position: Int): Int {
        return when (data[position]) {
            is Int -> VIEW_TYPE_AD
            is Category -> VIEW_TYPE_CATEGORY
            else -> VIEW_TYPE_CATEGORY
        }
    }
    

    interface OnClickListener {
        fun onClick(categoryData: Category)
    }
}
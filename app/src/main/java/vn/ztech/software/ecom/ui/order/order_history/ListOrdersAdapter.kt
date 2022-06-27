package vn.ztech.software.ecom.ui.order.order_history

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import vn.ztech.software.ecom.R
import vn.ztech.software.ecom.databinding.ItemOrderHistoryBinding
import vn.ztech.software.ecom.databinding.OrderListItemBinding
import vn.ztech.software.ecom.model.Order

class ListOrderAdapter( val context: Context, ordersArg: List<Order>,
                        val onClickListener: OnClickListener
) : RecyclerView.Adapter<ListOrderAdapter.ViewHolder>() {
    var orders: List<Order> = ordersArg

    inner class ViewHolder(private val binding: ItemOrderHistoryBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(order: Order) {

            binding.cartProductTitleTv.text = "${order.orderItems[0].name}..."
            binding.tvSubTotalAndShipping.text = "Subtotal: ${order.billing.subTotal} + ${order.billing.shippingFee}(ship)"
            binding.tvTotal.text = (order.billing.subTotal + order.billing.shippingFee).toString()
            if (order.orderItems[0].imageUrl.isNotEmpty()) {
                val imgUrl = order.orderItems[0].imageUrl.toUri().buildUpon().scheme("https").build()
                Glide.with(context)
                    .asBitmap()
                    .load(imgUrl)
                    .into(binding.productImageView)
                binding.productImageView.clipToOutline = true
            }
            setUIBaseOnOrderStatus(binding.tvOrderStatus, order.status)
            binding.btViewDetails.setOnClickListener {
                onClickListener.onClickButtonViewDetail(order)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemOrderHistoryBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(orders[position])
    }

    override fun getItemCount() = orders.size
    private fun setUIBaseOnOrderStatus(tv: TextView, status: String){
        tv.apply {
            text = status
            when(status){
                "PENDING"->{
                    setTextColor(Color.BLUE)
                    background = resources.getDrawable(R.drawable.rounded_bg_blue)
                }
                "CANCELED"->{
                    setTextColor(Color.RED)
                    background = resources.getDrawable(R.drawable.rounded_bg_red)
                }
                "PROCESSING"->{
                    setTextColor(Color.YELLOW)
                    background = resources.getDrawable(R.drawable.rounded_bg_yellow)
                }
                "CONFIRMED"->{
                    setTextColor(Color.GREEN)
                    background = resources.getDrawable(R.drawable.rounded_bg_green)
                }
            }
        }
    }
    interface OnClickListener{
        fun onClickButtonViewDetail(order: Order)
    }
}
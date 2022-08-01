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
import vn.ztech.software.ecom.util.extension.toCurrency
import vn.ztech.software.ecom.util.extension.toDateTimeString
import vn.ztech.software.ecom.util.extension.toYear

class ListOrderAdapter( val context: Context, ordersArg: List<Order>,
                        val onClickListener: OnClickListener
) : RecyclerView.Adapter<ListOrderAdapter.ViewHolder>() {
    var orders: List<Order> = ordersArg

    inner class ViewHolder(private val binding: ItemOrderHistoryBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(order: Order) {

            binding.cartProductTitleTv.text = "${order.orderItems[0].name}..."
            binding.tvSubTotalAndShipping.text = "Subtotal: ${order.billing.subTotal.toCurrency()} + ${order.billing.shippingFee.toCurrency()}(ship)"
            binding.tvTotal.text = (order.billing.subTotal + order.billing.shippingFee).toCurrency()
            if (order.orderItems[0].imageUrl.isNotEmpty()) {
                val imgUrl = order.orderItems[0].imageUrl.toUri().buildUpon().scheme("https").build()
                Glide.with(context)
                    .asBitmap()
                    .load(imgUrl)
                    .into(binding.productImageView)
                binding.productImageView.clipToOutline = true
            }
            setUIBaseOnOrderStatus(binding.tvOrderStatus, order.status)

            if (order.status == "CONFIRMED"){
                binding.btViewDetails.text = "Mark as Received!"
                binding.btViewDetails.setBackgroundColor(context.resources.getColor(R.color.button_bg_orange))
                /**only show expected delivery time if the order has been CONFIRMED*/
                binding.tvExpectedDeliveryTime.apply {
                    order.shippingDetails?.let {
                        if(!it.expectedDeliveryTime.isNullOrEmpty()){
                            visibility = View.VISIBLE
                            text = "Expected delivery time: ${it.expectedDeliveryTime?.toDateTimeString()}"
                        }
                     }
                }
            }else{
                binding.btViewDetails.text = "View details"
                binding.btViewDetails.setBackgroundColor(context.resources.getColor(R.color.blue_accent_300))

            }
            binding.btViewDetails.setOnClickListener {
                onClickListener.onClickButtonViewDetail(order)
            }
            binding.productCard.setOnClickListener {
                onClickListener.onClick(order)
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
                    setTextColor(Color.GRAY)
                    background = resources.getDrawable(R.drawable.rounded_bg_gray)
                }
                "PROCESSING"->{
                    setTextColor(resources.getColor(R.color.dark_yellow))
                    background = resources.getDrawable(R.drawable.rounded_bg_yellow)
                }
                "CONFIRMED"->{
                    setTextColor(Color.GREEN)
                    background = resources.getDrawable(R.drawable.rounded_bg_green)
                }
                "RECEIVED"->{
                    setTextColor(Color.GRAY)
                    background = resources.getDrawable(R.drawable.rounded_bg_gray)
                }
            }
        }
    }
    interface OnClickListener{
        fun onClickButtonViewDetail(order: Order)
        fun onClick(order: Order)
    }
}
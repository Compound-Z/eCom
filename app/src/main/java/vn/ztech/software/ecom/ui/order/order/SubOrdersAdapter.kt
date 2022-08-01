package vn.ztech.software.ecom.ui.order.order

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import vn.ztech.software.ecom.api.response.ShippingOption
import vn.ztech.software.ecom.databinding.LayoutSubOrderBinding
import vn.ztech.software.ecom.model.SubOrder
import vn.ztech.software.ecom.ui.order.OrderProductsAdapter
import vn.ztech.software.ecom.util.extension.toOrderItems

class SubOrdersAdapter(
    private val context: Context,
    subOrders: List<SubOrder>,
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    lateinit var onClickListener: OnClickListener
    var data: List<SubOrder> = subOrders

    inner class ViewHolderSubOrder(private var binding: LayoutSubOrderBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(subOrder: SubOrder, position: Int) {
            binding.layoutShop.tvShopName.text = subOrder.shop.name

            /***products*/
            val productsAdapter = OrderProductsAdapter(context, subOrder.items.toMutableList().toOrderItems())
            binding.orderDetailsProRecyclerView.adapter = productsAdapter

            /**shipping options*/
            val shippingOptionsAdapter = ShippingOptionsAdapter(context, subOrder.shippingOptions?: listOf())
            shippingOptionsAdapter.onClickListener = object : ShippingOptionsAdapter.OnClickListener {
                override fun onClick(shippingOption: ShippingOption) {
                    onClickListener.onSelectShippingOption(subOrder.shop._id, shippingOption.service_id)
//                    orderViewModel.currentSelectedShippingOption.value = shippingOption
//                    orderViewModel.calculateCost()
                }
            }
            binding.layoutListShippingOptions.recyclerShippingOptions.adapter = shippingOptionsAdapter
//            binding.layout.setOnClickListener {
//                onCardClick(position, shippingOption, binding.radioButton)
//                onClickListener.onClick(shippingOption)
//            }
//            binding.radioButton.setOnClickListener {
//                onCardClick(position, shippingOption, binding.radioButton)
//                onClickListener.onClick(shippingOption)
//            }
//
//            //choose the first shipping option as default
//            if (position==0){
//                binding.layout.performClick()
//            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderSubOrder {
        return ViewHolderSubOrder(
            LayoutSubOrderBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as ViewHolderSubOrder).bind(data[position], position)
    }

    override fun getItemCount(): Int = data.size

    interface OnClickListener {
        fun onSelectShippingOption(shopId: String, serviceId: Int)
    }

}
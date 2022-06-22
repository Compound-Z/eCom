package vn.ztech.software.ecom.ui.address

import android.content.Context
import android.util.Log
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.card.MaterialCardView
import vn.ztech.software.ecom.R
import vn.ztech.software.ecom.databinding.LayoutAddressCardBinding
import vn.ztech.software.ecom.model.AddressItem
import vn.ztech.software.ecom.util.extension.getFullAddress

private const val TAG = "AddressAdapter"

class AddressAdapter(
	private val context: Context,
	addressItems: List<AddressItem>,
	private val isSelect: Boolean
) :
	RecyclerView.Adapter<AddressAdapter.ViewHolder>() {

	lateinit var onClickListener: OnClickListener
	var data: List<AddressItem> = addressItems

	var lastCheckedAddress: String? = null
	private var lastCheckedCard: MaterialCardView? = null
	var selectedAddressPos = -1

	inner class ViewHolder(private var binding: LayoutAddressCardBinding) :
		RecyclerView.ViewHolder(binding.root) {
		fun bind(address: AddressItem, position: Int) {
			binding.addressCard.isChecked = position == selectedAddressPos
			binding.addressPersonNameTv.text =
				context.getString(R.string.person_name, address.receiverName)
			binding.addressCompleteAddressTv.text = address.getFullAddress()
			binding.addressMobileTv.text = address.receiverPhoneNumber
			if (isSelect) {
				binding.addressCard.setOnClickListener {
					onCardClick(position, address._id, it as MaterialCardView)
				}
			}
			binding.addressEditBtn.setOnClickListener {
				onClickListener.onEditClick(address._id)
			}
			binding.addressDeleteBtn.setOnClickListener {
				onClickListener.onDeleteClick(address._id)
				notifyDataSetChanged()
			}
		}
	}

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
		return ViewHolder(
			LayoutAddressCardBinding.inflate(
				LayoutInflater.from(parent.context),
				parent,
				false
			)
		)
	}

	override fun onBindViewHolder(holder: ViewHolder, position: Int) {
		holder.bind(data[position], position)
	}

	override fun getItemCount(): Int = data.size

	interface OnClickListener {
		fun onEditClick(addressId: String)
		fun onDeleteClick(addressId: String)
	}
	
	//todo: this may lead to some bugs when the list address update and change order of the data....
	private fun onCardClick(position: Int, addressTd: String, card: MaterialCardView) {
		if (addressTd != lastCheckedAddress) {
			card.apply {
				strokeColor = context.getColor(R.color.blue_accent_300)
				isChecked = true
				strokeWidth = TypedValue.applyDimension(
					TypedValue.COMPLEX_UNIT_DIP,
					2F,
					resources.displayMetrics
				).toInt()
			}
			lastCheckedCard?.apply {
				strokeColor = context.getColor(R.color.light_gray)
				isChecked = false
				strokeWidth = TypedValue.applyDimension(
					TypedValue.COMPLEX_UNIT_DIP,
					1F,
					resources.displayMetrics
				).toInt()
			}

			lastCheckedAddress = addressTd
			lastCheckedCard = card
			selectedAddressPos = position
			Log.d(TAG, "onCardClick: selected address = $addressTd")
		}
	}
}
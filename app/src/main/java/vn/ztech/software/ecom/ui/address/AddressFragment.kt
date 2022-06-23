package vn.ztech.software.ecom.ui.address

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import vn.ztech.software.ecom.R
import vn.ztech.software.ecom.databinding.FragmentAddressBinding
import vn.ztech.software.ecom.ui.BaseFragment
import org.koin.androidx.viewmodel.ext.android.viewModel
import vn.ztech.software.ecom.model.AddressItem
import vn.ztech.software.ecom.util.extension.showErrorDialog


private const val TAG = "AddressFragment"

class AddressFragment : BaseFragment<FragmentAddressBinding>() {
    private lateinit var addressAdapter: AddressAdapter
    private val viewModel: AddressViewModel by viewModel()
    override fun setViewBinding(): FragmentAddressBinding {
        return FragmentAddressBinding.inflate(layoutInflater)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getAddresses()
    }


    override fun setUpViews() {
        super.setUpViews()
        binding.addressAppBar.topAppBar.title = "Address"
        binding.addressAppBar.topAppBar.setNavigationOnClickListener { findNavController().navigateUp() }
        binding.loaderLayout.loaderFrameLayout.visibility = View.GONE
        binding.addressAddBtn.visibility = View.GONE
        binding.addressAddBtn.setOnClickListener {
            navigateToAddEditAddress(false)
        }
        binding.addressEmptyTextView.visibility = View.GONE

        if (context != null) {
            val addressItems = viewModel.addresses.value?.addresses ?: emptyList<AddressItem>()
            addressAdapter = AddressAdapter(requireContext(), addressItems, false)
            addressAdapter.onClickListener = object : AddressAdapter.OnClickListener {
                override fun onEditClick(addressId: String) {
                    Log.d(TAG, "onEditAddress: initiated")
                    navigateToAddEditAddress(true, addressId)
                }

                override fun onDeleteClick(addressId: String) {
                    Log.d(TAG, "onDeleteAddress: initiated")
                    showDeleteDialog(addressId)
                }
            }
            binding.addressAddressesRecyclerView.adapter = addressAdapter
        }
    }


    override fun observeView() {
        super.observeView()
        viewModel.loading.observe(viewLifecycleOwner) { status ->
            when (status) {
                true -> {
                    binding.addressEmptyTextView.visibility = View.GONE
                    binding.loaderLayout.loaderFrameLayout.visibility = View.VISIBLE
                    binding.loaderLayout.circularLoader.showAnimationBehavior
                }
                false -> {
                    binding.addressAddBtn.visibility = View.VISIBLE
                    binding.loaderLayout.circularLoader.hideAnimationBehavior
                    binding.loaderLayout.loaderFrameLayout.visibility = View.GONE
                }
            }
        }

        viewModel.addresses.observe(viewLifecycleOwner){
            it?.let {
                if(it.addresses.isNotEmpty()){
                    addressAdapter.data = it.addresses
                    binding.addressAddressesRecyclerView.adapter = addressAdapter
                    binding.addressAddressesRecyclerView.adapter?.notifyDataSetChanged()
                }else {
                    binding.addressAddressesRecyclerView.visibility = View.GONE
                    binding.loaderLayout.loaderFrameLayout.visibility = View.GONE
                    binding.loaderLayout.circularLoader.hideAnimationBehavior
                    binding.addressEmptyTextView.visibility = View.VISIBLE
                }
            }
            binding.addressAddBtn.visibility = View.VISIBLE
        }

        viewModel.error.observe(viewLifecycleOwner){
            it?.let {
                showErrorDialog(it)
            }
        }
    }

    private fun showDeleteDialog(addressId: String) {
//        context?.let {
//            MaterialAlertDialogBuilder(it)
//                .setTitle(getString(R.string.delete_dialog_title_text))
//                .setMessage(getString(R.string.delete_address_message_text))
//                .setNeutralButton(getString(R.string.pro_cat_dialog_cancel_btn)) { dialog, _ ->
//                    dialog.cancel()
//                }
//                .setPositiveButton(getString(R.string.delete_dialog_delete_btn_text)) { dialog, _ ->
//                    viewModel.deleteAddress(addressId)
//                    dialog.cancel()
//                }
//                .show()
//        }
    }

    private fun navigateToAddEditAddress(isEdit: Boolean, addressId: String? = null) {
        findNavController().navigate(
            R.id.action_addressFragment_to_addEditAddressFragment,
            bundleOf("isEdit" to isEdit, "addressId" to addressId)
        )
        android.util.Patterns.PHONE
    }


}
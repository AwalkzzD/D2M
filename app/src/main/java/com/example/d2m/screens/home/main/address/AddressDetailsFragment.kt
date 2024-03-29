package com.example.d2m.screens.home.main.address

import android.util.Log
import android.util.Patterns
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.d2m.R
import com.example.d2m.databinding.FragmentAddressDetailsBinding
import com.example.d2m.screens.utils.BaseActivity
import com.example.d2m.screens.utils.BaseFragment

private const val TAG = "AddressDetailsFragment"

class AddressDetailsFragment : BaseFragment<FragmentAddressDetailsBinding, AddressDetailsViewModel>(
    R.layout.fragment_address_details, AddressDetailsViewModel::class.java
) {

    override fun setUpView() {
        setUpToolBar()
        initViewModel()
        setUpListeners()
    }

    private fun setUpToolBar() {
        (activity as BaseActivity<*, *>).setupToolbar(
            fragmentBinding.appBar.toolbar, "Address Details", false
        )
    }

    private fun setUpListeners() {
        fragmentBinding.saveAddressButton.setOnClickListener {
            if (validateInputs()) {
                showToast("Saving Address Details", Toast.LENGTH_LONG)
            } else {
                showToast("Invalid Address Details", Toast.LENGTH_SHORT)
            }
        }

        fragmentBinding.addressArea.setOnClickListener {
            findNavController().navigate(R.id.action_addressDetails_to_selectAreaFragment)
        }
    }

    private fun validateInputs(): Boolean {
        fragmentBinding.customerFullName.apply {
            if (this.text.toString().isEmpty()) {
                this.error = "Cannot be empty"
                return false
            }
        }

        fragmentBinding.customerPhoneNum.apply {
            if (this.text.toString().isEmpty()) {
                this.error = "Cannot be empty"
                return false
            } else if (this.text.toString().length != 10) {
                this.error = "Not a Valid Phone Number"
                return false
            }
        }

        fragmentBinding.billingEmail.apply {
            if (this.text.toString().isEmpty()) {
                this.error = "Cannot be empty"
                return false
            } else if (!Patterns.EMAIL_ADDRESS.matcher(this.text.toString()).matches()) {
                this.error = "Not a Valid Email Address"
                return false
            }
        }

        fragmentBinding.addressHouseNumberName.apply {
            if (this.text.toString().isEmpty()) {
                this.error = "Cannot be empty"
                return false
            }
        }

        fragmentBinding.addressRoadColony.apply {
            if (this.text.toString().isEmpty()) {
                this.error = "Cannot be empty"
                return false
            }
        }

        fragmentBinding.addressArea.apply {
            if (this.text.toString().isEmpty()) {
                this.error = "Cannot be empty"
                return false
            }
        }

        return true
    }

    private fun initViewModel() {
        fragmentViewModel.area.observe(viewLifecycleOwner) {
            fragmentBinding.addressArea.setText("$it asd")
            Log.d(TAG, "initViewModel: Called")
        }
    }
}

package com.example.d2m.screens.home.main.address

import android.util.Patterns
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.d2m.R
import com.example.d2m.databinding.FragmentAddressDetailsBinding
import com.example.d2m.screens.utils.BaseActivity
import com.example.d2m.screens.utils.BaseFragment

private const val TAG = "AddressDetailsFragment"

class AddressDetailsFragment : BaseFragment<FragmentAddressDetailsBinding, AddressDetailsViewModel>(
    R.layout.fragment_address_details, AddressDetailsViewModel::class.java
) {

    private val selectAreaViewModel: SelectAreaViewModel by activityViewModels()

    private lateinit var address: StringBuilder

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
                showToast("Saving your address details", Toast.LENGTH_LONG)

                /**
                 * code snippet to store address into Shared Prefs
                 * */
                /*val sharedPreferencesLogin =
                    activity?.getSharedPreferences("userData", Context.MODE_PRIVATE)
                sharedPreferencesLogin?.edit()?.putString("address", address.toString())?.apply()*/

                findNavController().navigate(R.id.action_addressDetailsFragment_to_checkoutFragment)
            } else {
                showToast("Invalid address details", Toast.LENGTH_SHORT)
            }
        }

        fragmentBinding.addressArea.setOnClickListener {
            findNavController().navigate(R.id.action_addressDetails_to_selectAreaFragment)
        }
    }

    private fun validateInputs(): Boolean {

        address.clear()

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
            address.append(this.text.toString())
        }

        fragmentBinding.addressRoadColony.apply {
            if (this.text.toString().isEmpty()) {
                this.error = "Cannot be empty"
                return false
            }
            address.append(this.text.toString())
            address.append(", ")

        }

        fragmentBinding.addressArea.apply {
            if (this.text.toString().isEmpty()) {
                this.error = "Cannot be empty"
                return false
            }
            address.append(this.text.toString())
        }

        return true
    }

    private fun initViewModel() {
        selectAreaViewModel.area.observe(viewLifecycleOwner) {
            fragmentBinding.addressArea.setText(buildString {
                append(it.areaName)
                append(" - ")
                append(it.pincode)
            })
        }
    }
}

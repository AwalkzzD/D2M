package com.example.d2m.screens.home.main.checkout

import android.util.Log
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.d2m.R
import com.example.d2m.data.local.home.ServiceX
import com.example.d2m.databinding.FragmentCheckoutBinding
import com.example.d2m.screens.home.main.service.ServiceViewModel
import com.example.d2m.screens.utils.BaseActivity
import com.example.d2m.screens.utils.BaseFragment
import java.util.Calendar
import java.util.Locale

private const val TAG = "CheckoutFragment"

// FIXME: use "userData" shared prefs to check if default address is null, if null set visibility of address layout to GONE or setText with address 

class CheckoutFragment : BaseFragment<FragmentCheckoutBinding, CheckoutViewModel>(
    R.layout.fragment_checkout, CheckoutViewModel::class.java
) {
    private val serviceViewModel: ServiceViewModel by activityViewModels()

    override fun setUpView() {
        setUpToolBar()
        initViewModel()

        seUpListeners()
    }

    private fun setUpToolBar() {
        (activity as BaseActivity<*, *>).setupToolbar(
            fragmentBinding.appBar.toolbar, "Checkout", true
        )
    }

    private fun initViewModel() {

        fragmentViewModel.addedCartServices.postValue(serviceViewModel.addedServiceX)

        fragmentViewModel.addedCartServices.observe(viewLifecycleOwner) {
            if (it != null) {
                updateCartAmount(it)
            }
        }

    }

    private fun seUpListeners() {

        /**
         * code snippet to get address from Shared Prefs and display in CheckOut Fragment
         *
         * stored to Shared Prefs from AddressDetailsFragment
         * */
        /*val sharedPreferencesLogin =
            activity?.getSharedPreferences("userData", Context.MODE_PRIVATE)
        val address = sharedPreferencesLogin?.getString("address", "")

        if (!address.isNullOrEmpty()) {
            fragmentBinding.checkoutAddress.text = address.toString()
        } else {
            fragmentBinding.yourLocationLayout.visibility = View.GONE
        }*/

        fragmentBinding.datePicker.apply {
            minDate = System.currentTimeMillis()
        }

        fragmentBinding.selectedDate.text = buildString {
            append(
                Calendar.getInstance().getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.ENGLISH)
            )
            append(", ")
            append(Calendar.getInstance().get(Calendar.YEAR))
        }

        fragmentBinding.datePicker.setOnDateChangedListener { view, year, monthOfYear, dayOfMonth ->
            val cal = Calendar.getInstance()
            cal.set(year, monthOfYear, dayOfMonth)

            fragmentBinding.selectedDate.text = buildString {
                append(cal.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.ENGLISH))
                append(", ")
                append(year)
            }
        }

        fragmentBinding.checkoutOrderBottom.checkoutButton.setOnClickListener {
            // FIXME: add condition, if all details are valid only then navigate to CartFragment 
            findNavController().navigate(R.id.action_checkoutFragment_to_cartFragment)
        }
    }

    private fun updateCartAmount(addedCartServices: ArrayList<ServiceX>) {
        var cartTotal = 0.0
        for (serviceX in addedCartServices) {
            cartTotal += serviceX.price
        }

        fragmentBinding.checkoutOrderBottom.cartTotal.text = buildString {
            append("₹ ")
            append(cartTotal)
        }

        Log.d(TAG, "updateCartAmount: $cartTotal")
    }
}
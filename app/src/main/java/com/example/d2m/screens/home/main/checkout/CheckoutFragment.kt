package com.example.d2m.screens.home.main.checkout

import android.content.Context
import android.util.Log
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.d2m.R
import com.example.d2m.data.local.checkout.TimeSlots
import com.example.d2m.data.local.home.ServiceX
import com.example.d2m.data.remote.otp.verify.VerifyOtpResponseData
import com.example.d2m.databinding.FragmentCheckoutBinding
import com.example.d2m.screens.home.main.service.ServiceViewModel
import com.example.d2m.screens.utils.BaseActivity
import com.example.d2m.screens.utils.BaseFragment
import com.example.d2m.screens.utils.GenericDataAdapter
import com.google.gson.Gson
import java.util.Calendar
import java.util.Locale

private const val TAG = "CheckoutFragment"

class CheckoutFragment : BaseFragment<FragmentCheckoutBinding, CheckoutViewModel>(
    R.layout.fragment_checkout, CheckoutViewModel::class.java
) {

    private lateinit var timeSlotsAdapter: GenericDataAdapter<TimeSlots>

    private val serviceViewModel: ServiceViewModel by activityViewModels()
    private val calendar = Calendar.getInstance()
    private val timeSlotsList: MutableList<TimeSlots> = mutableListOf()

    override fun setUpView() {
        setUpToolBar()
        setUpData()
        initViewModel()
        initRecyclerView()
        initViews()
        seUpListeners()
    }

    private fun setUpToolBar() {
        (activity as BaseActivity<*, *>).setupToolbar(
            fragmentBinding.appBar.toolbar, "Checkout", true
        )
    }

    private fun setUpData() {
        timeSlotsList.clear()
        timeSlotsList.add(TimeSlots("01:00 PM - 02:00 PM"))
        timeSlotsList.add(TimeSlots("02:00 PM - 03:00 PM"))
        timeSlotsList.add(TimeSlots("03:00 PM - 04:00 PM"))
        timeSlotsList.add(TimeSlots("04:00 PM - 05:00 PM"))
        timeSlotsList.add(TimeSlots("05:00 PM - 06:00 PM"))
        timeSlotsList.add(TimeSlots("06:00 PM - 07:00 PM"))
    }

    private fun initViewModel() {

        fragmentViewModel.addedCartServices.postValue(serviceViewModel.addedServiceX)

        fragmentViewModel.addedCartServices.observe(viewLifecycleOwner) {
            if (it != null) {
                updateCartAmount(it)
            }
        }

    }

    private fun initViews() {
        val sharedPreferences = activity?.getSharedPreferences("userData", Context.MODE_PRIVATE)
        val userData = Gson().fromJson(
            sharedPreferences?.getString("verifiedUser", ""), VerifyOtpResponseData::class.java
        )

        userData.defaultAddress.also { defaultAddress ->
            if (defaultAddress != null) {
                fragmentBinding.checkoutAddress.text = buildString {
                    append(defaultAddress.addressLine1)
                    append(", ")
                    append(defaultAddress.addressLine2)
                    append(", ")
                    append(defaultAddress.getCityAreaDetail.areaName)
                    append(" - ")
                    append(defaultAddress.getCityAreaDetail.pincode)
                }
            } else {
                fragmentBinding.yourLocationLayout.visibility = View.GONE
            }
        }

        fragmentBinding.datePicker.apply {
            minDate = System.currentTimeMillis()
//            maxDate = System.currentTimeMillis() + (1000 * 60 * 60 * 24 * 20)
        }

        fragmentBinding.selectedDate.text = buildString {
            append(
                calendar.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.ENGLISH)
            )
            append(", ")
            append(calendar.get(Calendar.YEAR))
        }

    }

    private fun initRecyclerView() {

        timeSlotsAdapter =
            GenericDataAdapter(timeSlotsList, R.layout.time_slot_list_item) { timeSlot: TimeSlots ->

            }

        fragmentBinding.timeSlotsRv.apply {
            layoutManager = GridLayoutManager(requireActivity(), 2)
            adapter = timeSlotsAdapter
        }

    }

    private fun seUpListeners() {
        fragmentBinding.datePicker.setOnDateChangedListener { view, year, monthOfYear, dayOfMonth ->
            calendar.set(year, monthOfYear, dayOfMonth)

            fragmentBinding.selectedDate.text = buildString {
                append(calendar.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.ENGLISH))
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
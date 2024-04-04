package com.example.d2m.screens.home.main.checkout

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.d2m.R
import com.example.d2m.data.local.checkout.TimeSlots
import com.example.d2m.data.local.home.ServiceX
import com.example.d2m.data.remote.otp.verify.VerifyOtpResponseData
import com.example.d2m.databinding.FragmentCheckoutBinding
import com.example.d2m.screens.home.main.service.ServiceViewModel
import com.example.d2m.screens.utils.adapters.GenericDataAdapter
import com.example.d2m.screens.utils.base_classes.BaseActivity
import com.example.d2m.screens.utils.base_classes.BaseFragment
import com.example.d2m.screens.utils.calendar_utils.DateUtils
import com.example.d2m.screens.utils.calendar_utils.calendar.CalendarChangesObserver
import com.example.d2m.screens.utils.calendar_utils.calendar.CalendarViewManager
import com.example.d2m.screens.utils.calendar_utils.calendar.SingleRowCalendarAdapter
import com.example.d2m.screens.utils.calendar_utils.selection.CalendarSelectionManager
import com.google.gson.Gson
import java.util.Calendar
import java.util.Date
import java.util.Locale

private const val TAG = "CheckoutFragment"

class CheckoutFragment : BaseFragment<FragmentCheckoutBinding, CheckoutViewModel>(
    R.layout.fragment_checkout, CheckoutViewModel::class.java
) {

    private lateinit var timeSlotsAdapter: GenericDataAdapter<TimeSlots>

    private val serviceViewModel: ServiceViewModel by activityViewModels()
    private val calendar = Calendar.getInstance()
    private val timeSlotsList: MutableList<TimeSlots> = mutableListOf()

    private val datesList: MutableList<Date> = mutableListOf()

    override fun setUpView() {
        setUpToolBar()
        setUpData()
        setUpCalendar()
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

        datesList.clear()
        datesList.add(Calendar.getInstance().time)
        datesList.add(Calendar.getInstance().time)
        datesList.add(Calendar.getInstance().time)
        datesList.add(Calendar.getInstance().time)
        datesList.add(Calendar.getInstance().time)
        datesList.add(Calendar.getInstance().time)
        datesList.add(Calendar.getInstance().time)
        datesList.add(Calendar.getInstance().time)
        datesList.add(Calendar.getInstance().time)
        datesList.add(Calendar.getInstance().time)
    }

    private fun setUpCalendar() {
        val myCalendarViewManager = object : CalendarViewManager {
            override fun setCalendarViewResourceId(
                position: Int,
                date: Date,
                isSelected: Boolean,
            ): Int {
                return if (isSelected) {
                    R.layout.calendar_user
                } else R.layout.calendar_item
            }

            override fun bindDataToCalendarView(
                holder: SingleRowCalendarAdapter.CalendarViewHolder,
                date: Date,
                position: Int,
                isSelected: Boolean,
            ) {
                holder.itemView.findViewById<TextView>(R.id.tv_date_calendar_item).text =
                    DateUtils.getDayNumber(date)

                holder.itemView.findViewById<TextView>(R.id.tv_day_calendar_item).text =
                    DateUtils.getDay3LettersName(date)
            }
        }
        val mySelectionManager = object : CalendarSelectionManager {
            override fun canBeItemSelected(position: Int, date: Date): Boolean {
                return true
            }
        }
        val myCalendarChangesObserver = object : CalendarChangesObserver {
            @SuppressLint("SetTextI18n")
            override fun whenWeekMonthYearChanged(
                weekNumber: String,
                monthNumber: String,
                monthName: String,
                year: String,
                date: Date,
            ) {
                fragmentBinding.selectedDate.text =
                    "${DateUtils.getMonthName(date)}, ${DateUtils.getYear(date)} "

                super.whenWeekMonthYearChanged(weekNumber, monthNumber, monthName, year, date)
            }

            /***
             * This function is called when the user selects a date from the calendar view.
             * It updates the selected date and time in the view model and then checks
             * if the selected date is the same as the current booking date. If it is,
             * it sets up the time slots for today's date, otherwise it sets up the time slots for the selected date.
             */
            @SuppressLint("SetTextI18n")
            override fun whenSelectionChanged(isSelected: Boolean, position: Int, date: Date) {/*viewModel.selectedDate.value =
                    DateUtils.getYear(date) + "-" + DateUtils.getMonthNumber(date) + "-" + DateUtils.getDayNumber(
                        date
                    )*/

                val todayDate =
                    DateUtils.getDayNumber(date) + "-" + DateUtils.getMonthNumber(date) + "-" + DateUtils.getYear(
                        date
                    )

                /*viewModel.selectedTimeId.value = 0
                viewModel.selectedTime.value = ""

                if (todayDate == viewModel.bookingDate.value) {
                    getBinding().txtTitle2.text =
                        ("(" + slotDataToday.size.toString() + " Slot Available)")
                    setUpTimeSlotMultiViewRecyclerView(slotDataToday)
                } else {
                    getBinding().txtTitle2.text =
                        ("(" + slotDataList.size.toString() + " Slot Available)")
                    setUpTimeSlotMultiViewRecyclerView(slotDataList)
                }*/

                fragmentBinding.selectedDate.text =
                    "${DateUtils.getMonthName(date)}, ${DateUtils.getYear(date)} "

                super.whenSelectionChanged(isSelected, position, date)
            }
        }

        /**
         * creates a single row calendar by setting
         * the calendar view manager, calendar changes observer, and calendar selection manager.
         */
        val singleRowCalendar = fragmentBinding.rowCalendar.apply {
            calendarViewManager = myCalendarViewManager
            calendarChangesObserver = myCalendarChangesObserver
            calendarSelectionManager = mySelectionManager
            longPress = false
            multiSelection = false
            deselection = false
            futureDaysCount = 20
            includeCurrentDate = true
            init()
            select(0)
        }
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
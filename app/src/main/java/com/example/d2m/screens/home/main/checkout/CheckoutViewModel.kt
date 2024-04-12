package com.example.d2m.screens.home.main.checkout

import android.util.Log
import androidx.databinding.ObservableBoolean
import androidx.lifecycle.MutableLiveData
import com.example.d2m.data.local.home.ServiceX
import com.example.d2m.data.local.time_slots.GetTimeSlots
import com.example.d2m.data.local.time_slots.TimeSlots
import com.example.d2m.network_utils.ApiClient
import com.example.d2m.network_utils.api_services.GetTimeSlotsService
import com.example.d2m.screens.utils.base_classes.BaseViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CheckoutViewModel : BaseViewModel() {
    var addedCartServices: MutableLiveData<ArrayList<ServiceX>> =
        MutableLiveData<ArrayList<ServiceX>>()

    var timeSlotsList: MutableLiveData<GetTimeSlots> = MutableLiveData()

    var token = MutableLiveData<String>()

    fun checkTimeSlots(timeSlot: TimeSlots) {

        timeSlotsList.value?.timeSlots?.find { it.isSelected.get() }.apply {
            this?.isSelected?.set(false)
        }

        timeSlotsList.value?.timeSlots?.find { timeSlot == it }.also {
            if (it != null) {
                if (it.isSelected.get()) {
                    it.isSelected.set(false)
                } else {
                    it.isSelected.set(true)
                }
            }
        }

    }

    fun getTimeSlots() {
        setLoadingState(true)
        val retrofitInstance =
            ApiClient.createService(GetTimeSlotsService::class.java) as GetTimeSlotsService

        val retrofitData = retrofitInstance.getTimeSlots("Bearer " + token.value.toString())

        retrofitData.enqueue(object : Callback<GetTimeSlots?> {
            override fun onResponse(call: Call<GetTimeSlots?>, response: Response<GetTimeSlots?>) {
                timeSlotsList.postValue(response.body())
            }

            override fun onFailure(call: Call<GetTimeSlots?>, t: Throwable) {
                Log.d("TAG", "onFailure: Failed to get Time Slots --> $t")
            }
        })

        ApiClient.destroyInstance()
        setLoadingState(false)
    }

}
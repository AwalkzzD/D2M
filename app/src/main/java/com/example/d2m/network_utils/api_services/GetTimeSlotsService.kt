package com.example.d2m.network_utils.api_services

import com.example.d2m.data.local.time_slots.GetTimeSlots
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header

interface GetTimeSlotsService {

    @GET("api/v1/get-time-slots")
    fun getTimeSlots(
        @Header("Authorization") token: String
    ): Call<GetTimeSlots>

}
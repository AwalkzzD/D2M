package com.example.d2m.data.services.api

import com.example.d2m.data.models.car.CarAdded
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface AddVehicleService {
    @POST("api/v1/user/add-vehicle-information")
    fun addVehicle(
        @Body body: RequestBody,
        @Header("Authorization") token: String
    ): Call<CarAdded>
}
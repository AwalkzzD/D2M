package com.example.d2m.screens.home.addvehicle

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.d2m.data.models.car.CarAdded
import com.example.d2m.data.services.api.AddVehicleService
import com.example.d2m.data.services.api.ApiClient
import com.google.gson.GsonBuilder
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AddCarViewModel : ViewModel() {

    var vehicleNumber = MutableLiveData<String>()
    var modelName = MutableLiveData<String>()
    var fuelType = MutableLiveData<String>()
    var vehicleColor = MutableLiveData<String>()
    var brandName = MutableLiveData<String>()
    var isDefault = MutableLiveData<String>()
    var userID = MutableLiveData<String>()
    var token = MutableLiveData<String>()

    fun addCar() {

        Log.d("TAG", "addCar: user ID --> " + userID.value.toString())

        val reqBody: RequestBody = MultipartBody.Builder().setType(MultipartBody.FORM)
            .addFormDataPart("user_id", userID.value.toString())
            .addFormDataPart("vehicle_number", vehicleNumber.value.toString())
            .addFormDataPart("owner_name", "XYZ")
            .addFormDataPart("model_name", modelName.value.toString())
            .addFormDataPart("fuel_type", "1").addFormDataPart("color", "XYZ")
            .addFormDataPart("brand_name", brandName.value.toString())
            .addFormDataPart("is_default", isDefault.value.toString()).build()

        val retrofitInstance =
            ApiClient.createService(AddVehicleService::class.java) as AddVehicleService
        val retrofitData = retrofitInstance.addVehicle(reqBody, "Bearer " + token.value.toString())

        retrofitData.enqueue(object : Callback<CarAdded?> {
            override fun onResponse(call: Call<CarAdded?>, response: Response<CarAdded?>) {
                Log.d(
                    "TAG",
                    "onResponse: " + GsonBuilder().setPrettyPrinting().create()
                        .toJson(response.body())
                )
            }

            override fun onFailure(call: Call<CarAdded?>, t: Throwable) {
                Log.d("TAG", "onFailure: Failed to add Car --> $t")
            }
        })

        ApiClient.destroyInstance()
    }
}
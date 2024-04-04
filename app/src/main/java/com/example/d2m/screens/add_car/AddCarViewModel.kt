package com.example.d2m.screens.add_car

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.d2m.data.local.car.CarAdded
import com.example.d2m.data.local.car.CarBrand
import com.example.d2m.data.local.car.CarModel
import com.example.d2m.data.local.car.CarRegistration
import com.example.d2m.data.local.car.FuelType
import com.example.d2m.network_utils.ApiClient
import com.example.d2m.network_utils.api_services.AddVehicleService
import com.example.d2m.screens.utils.base_classes.BaseViewModel
import com.google.gson.GsonBuilder
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AddCarViewModel : BaseViewModel() {

    var carAdded: MutableLiveData<CarAdded> = MutableLiveData<CarAdded>()
    var carBrand: MutableLiveData<CarBrand> = MutableLiveData()
    var carModel: MutableLiveData<CarModel> = MutableLiveData()
    var fuelType: MutableLiveData<FuelType> = MutableLiveData()
    var carRegistration: MutableLiveData<CarRegistration> = MutableLiveData()
    var isDefault = MutableLiveData<String>()
    var userID = MutableLiveData<String>()
    var token = MutableLiveData<String>()
    var vehicleColor = MutableLiveData<String>()

    fun addCar() {

        /**
         * requestbody using multipartbody to pass car details as form-data type in request
         **/
        val reqBody: RequestBody = MultipartBody.Builder().setType(MultipartBody.FORM)
            .addFormDataPart("user_id", userID.value.toString())
            .addFormDataPart(
                "vehicle_number",
                carRegistration.value?.carRegistrationNumber ?: "null"
            )
            .addFormDataPart("owner_name", "XYZ")
            .addFormDataPart("model_name", carModel.value?.carModelName ?: "null")
            .addFormDataPart("fuel_type", fuelType.value?.fuelName ?: "null")
            .addFormDataPart("color", vehicleColor.value.toString())
            .addFormDataPart("brand_name", carBrand.value?.carBrandName ?: "null")
            .addFormDataPart("is_default", isDefault.value.toString())
            .build()

        val retrofitInstance =
            ApiClient.createService(AddVehicleService::class.java) as AddVehicleService

        val retrofitData = retrofitInstance.addVehicle(reqBody, "Bearer " + token.value.toString())

        retrofitData.enqueue(object : Callback<CarAdded?> {
            override fun onResponse(call: Call<CarAdded?>, response: Response<CarAdded?>) {
                carAdded.postValue(response.body())
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
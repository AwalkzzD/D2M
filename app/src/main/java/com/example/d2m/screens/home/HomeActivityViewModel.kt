package com.example.d2m.screens.home

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.d2m.data.local.home.UserHome
import com.example.d2m.network_utils.ApiClient
import com.example.d2m.network_utils.api_services.RequestUserData
import com.example.d2m.screens.utils.base_classes.BaseViewModel
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.net.SocketTimeoutException

class HomeActivityViewModel : BaseViewModel() {

    var userLiveData: MutableLiveData<UserHome> = MutableLiveData<UserHome>()

    fun requestUserData(userId: String) {

        val reqBody: RequestBody =
            MultipartBody.Builder().setType(MultipartBody.FORM).addFormDataPart("user_id", userId)
                .build()

        val retrofitInstance =
            ApiClient.createService(RequestUserData::class.java) as RequestUserData
        val retrofitData = retrofitInstance.requestUserData(reqBody)

        retrofitData.enqueue(object : Callback<UserHome?> {
            override fun onResponse(call: Call<UserHome?>, response: Response<UserHome?>) {
                userLiveData.postValue(response.body())
                /*Log.d(
                    "TAG",
                    "onResponse: " + GsonBuilder().setPrettyPrinting().create()
                        .toJson(response.body())
                )*/
            }

            override fun onFailure(call: Call<UserHome?>, t: Throwable) {
                when (t) {
                    is SocketTimeoutException -> {
                        Log.d("TAG", "Server Timeout --> $t")
                    }

                    else -> {
                        Log.d("TAG", "onFailure: Failed to get User Data --> $t")
                    }
                }
            }
        })
        ApiClient.destroyInstance()

    }
}
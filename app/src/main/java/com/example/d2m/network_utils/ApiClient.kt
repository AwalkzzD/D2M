package com.example.d2m.network_utils

import com.example.d2m.common_utils.AppConstants
import com.example.d2m.common_utils.AppConstants.BASE_URL
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class ApiClient {

    companion object {

        private var retrofitService: Any? = null

        private val httpClient = OkHttpClient.Builder()
            .connectTimeout(AppConstants.CONNECTION_TIME_OUT.toLong(), TimeUnit.MILLISECONDS)
            .readTimeout(AppConstants.READ_TIME_OUT.toLong(), TimeUnit.MILLISECONDS)
            .writeTimeout(AppConstants.WRITE_TIME_OUT.toLong(), TimeUnit.MILLISECONDS).build()

        fun <S> createService(serviceClass: Class<S>): Any? {
            if (retrofitService == null) {
                val retrofit: Retrofit =
                    Retrofit.Builder().baseUrl(BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .client(httpClient)
                        .build()
                retrofitService = retrofit.create(serviceClass)
            }
            return retrofitService
        }

        fun destroyInstance() {
            retrofitService = null
        }
    }

}
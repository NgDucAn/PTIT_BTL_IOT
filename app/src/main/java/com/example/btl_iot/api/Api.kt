package com.example.btl_iot.api

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object Api {
    private const val BASE_URL = "http://192.168.190.194:3000/"
    private fun getRetrofit(): Retrofit {
        // Cấu hình OkHttpClient với thời gian chờ tùy chỉnh
        val okHttpClient = OkHttpClient.Builder()
            .connectTimeout(15, TimeUnit.SECONDS) // Tăng thời gian chờ kết nối
            .readTimeout(15, TimeUnit.SECONDS)    // Tăng thời gian chờ đọc dữ liệu
            .writeTimeout(15, TimeUnit.SECONDS)   // Tăng thời gian chờ ghi dữ liệu
            .build()

        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .client(okHttpClient) // Gắn OkHttpClient tùy chỉnh
            .build()
    }

    fun getApi(): UserApi {
        return getRetrofit().create(UserApi::class.java)
    }
}
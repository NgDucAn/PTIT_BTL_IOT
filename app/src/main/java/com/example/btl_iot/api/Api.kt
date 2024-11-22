package com.example.btl_iot.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object Api {
    private const val BASE_URL = "http://192.168.190.194:3000/"
    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
    }

    fun getApi(): UserApi {
        return getRetrofit().create(UserApi::class.java)
    }
}
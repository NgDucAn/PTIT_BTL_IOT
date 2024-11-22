package com.example.btl_iot.api

import com.example.btl_iot.model.GetWeatherResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface UserApi {

    @GET("weather?city=Hanoi")
    suspend fun getWeather(
        @Query("temperature") temperature: Double,
        @Query("humidity") humidity: Double,
        @Query("precipitation") precipitation: Boolean
    ): Call<GetWeatherResponse>
}
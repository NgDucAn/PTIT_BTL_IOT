package com.example.btl_iot.api

import com.example.btl_iot.model.GetWeatherResponse
import com.example.btl_iot.model.LoginRequest
import com.example.btl_iot.model.LoginResponse
import com.example.btl_iot.model.UserRequest
import com.example.btl_iot.model.RegisterResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface UserApi {
    @GET("/weather")
    suspend fun getWeather(@Query("city") city: String): Response<GetWeatherResponse>

    @POST("addUser")
    suspend fun registerUser(
        @Body userRequest: UserRequest
    ): Response<RegisterResponse>

    @POST("checkLogin")
    suspend fun loginUser(@Body loginRequest: LoginRequest): Response<LoginResponse>
}
package com.example.btl_iot.model

import com.google.gson.annotations.SerializedName

data class GetWeatherResponse (
    @SerializedName("status" ) var status : Boolean? = null,
    @SerializedName("data"   ) var data   : DataWeather?    = DataWeather()
)

data class DataWeather(
    @SerializedName("temperature") var temperature: Double? = null,
    @SerializedName("humidity") var humidity: Double? = null,
    @SerializedName("precipitation") var precipitation: Boolean? = null
)
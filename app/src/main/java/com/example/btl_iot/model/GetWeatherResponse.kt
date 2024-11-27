package com.example.btl_iot.model

import com.google.gson.annotations.SerializedName

data class GetWeatherResponse(
    @SerializedName("temperature") var temperature: Double? = null,
    @SerializedName("humidity") var humidity: Int? = null,
    @SerializedName("precipitation") var precipitation: Double? = null
)

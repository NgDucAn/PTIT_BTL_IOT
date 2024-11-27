package com.example.btl_iot.ui.activity

import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.example.btl_iot.R
import com.example.btl_iot.api.Api
import com.example.btl_iot.base.BaseActivity
import com.example.btl_iot.databinding.ActivityHomeBinding
import com.example.btl_iot.model.GetWeatherResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class HomeActivity : BaseActivity<ActivityHomeBinding>() {
    override fun createBinding(): ActivityHomeBinding {
        return ActivityHomeBinding.inflate(layoutInflater)
    }

    override fun initMain() {
        initEvent()
        getWeather()
    }

    private fun initEvent() {
        with(binding) {
            icSetting.setOnClickListener {
                startActivity(Intent(this@HomeActivity, SettingActivity::class.java))
            }
            swWatering.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    Toast.makeText(this@HomeActivity, "The pump is on.", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this@HomeActivity, "The pump is off.", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun getWeather() {
        lifecycleScope.launch(Dispatchers.IO) {
            try {
                // Gửi request với tham số city
                val response = Api.getApi().getWeather(city = "Hanoi")

                if (response.isSuccessful) {
                    val getWeatherResponse = response.body()
                    if (getWeatherResponse != null) {
                        withContext(Dispatchers.Main) {
                            initData(getWeatherResponse)
                        }
                    } else {
                        Log.e("Weather Error", "Response body is null")
                    }
                } else {
                    val errorBody = response.errorBody()?.string()
                    Log.e("Weather API Error", "Response Code: ${response.code()}, Error: $errorBody")
                    withContext(Dispatchers.Main) {
                        Toast.makeText(
                            this@HomeActivity,
                            "Failed to fetch weather: $errorBody",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(
                        this@HomeActivity,
                        "API Failure: ${e.localizedMessage}",
                        Toast.LENGTH_LONG
                    ).show()
                }
                Log.e("API Failure", e.stackTraceToString())
            }
        }
    }

    private fun initData(getWeatherResponse: GetWeatherResponse) {
        with(binding) {
            Log.d("Weather Data", getWeatherResponse.toString())
            if (getWeatherResponse.humidity!! >= 70) {
                icWeather.setImageResource(R.drawable.ic_rain)
            } else {
                icWeather.setImageResource(R.drawable.ic_sun)
            }
            tvTemperatureText.text = "${getWeatherResponse.temperature}°C"
            tvValueTemperature.text = "${getWeatherResponse.temperature}"
            tvWeatherStatus.text = "${getWeatherResponse.humidity}%"
            tvValueHumidity.text = "${getWeatherResponse.humidity}%"
        }
    }
}
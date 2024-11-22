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
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class HomeActivity : BaseActivity<ActivityHomeBinding>() {
    private var temperature: Double = 0.0
    private var humidity: Double = 0.0
    private var precipitation: Boolean = false

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
        }
    }

    private fun getWeather() {
        CoroutineScope(Dispatchers.IO).launch {
            val getWeatherApi: Call<GetWeatherResponse> =
                Api.getApi().getWeather(temperature, humidity, precipitation)
            getWeatherApi.enqueue(object : Callback<GetWeatherResponse> {
                override fun onResponse(
                    call: Call<GetWeatherResponse>,
                    response: Response<GetWeatherResponse>
                ) {
                    if (response.isSuccessful) {
                        val getWeatherResponse: GetWeatherResponse? = response.body()
                        if (getWeatherResponse?.status == true) {
                            initData(getWeatherResponse)
                        } else {
                            Log.d("Weather null", "Weather data is null or status is false")
                        }
                    } else {
                        Log.e("Weather API Error", "Response Code: ${response.code()}")
                    }
                }

                override fun onFailure(call: Call<GetWeatherResponse>, t: Throwable) {
                    Toast.makeText(this@HomeActivity, "API Failure: ${t.localizedMessage}", Toast.LENGTH_LONG).show()
                    Log.e("API Failure", t.stackTraceToString())
                }
            })
        }
    }

    private fun initData(getWeatherResponse: GetWeatherResponse) {
        with(binding) {
            Log.d("Data Weather", getWeatherResponse.data.toString())
            if (getWeatherResponse.data?.precipitation == true) {
                icWeather.setImageResource(R.drawable.ic_rain)
            } else {
                icWeather.setImageResource(R.drawable.ic_sun)
            }
            tvTemperatureText.text = getWeatherResponse.data?.temperature.toString()
            tvWeatherStatus.text = getWeatherResponse.data?.humidity.toString()
        }
    }
}
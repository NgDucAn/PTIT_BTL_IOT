package com.example.btl_iot.ui.activity

import android.content.Intent
import com.example.btl_iot.base.BaseActivity
import com.example.btl_iot.databinding.ActivitySettingBinding

class SettingActivity : BaseActivity<ActivitySettingBinding>() {
    override fun createBinding(): ActivitySettingBinding {
        return ActivitySettingBinding.inflate(layoutInflater)
    }

    override fun initMain() {
        initView()
    }

    private fun initView() {
        with(binding) {
            tb.setNavigationOnClickListener {
                finish()
            }

            clPump.setOnClickListener {
                val intent = Intent(this@SettingActivity, PumpControlTimes::class.java)
                startActivity(intent)
            }
        }
    }
}
package com.example.btl_iot.ui.activity

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

        }
    }
}
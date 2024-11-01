package com.example.btl_iot.ui.activity

import com.example.btl_iot.base.BaseActivity
import com.example.btl_iot.databinding.ActivityHomeBinding

class HomeActivity : BaseActivity<ActivityHomeBinding>() {
    override fun createBinding(): ActivityHomeBinding {
        return ActivityHomeBinding.inflate(layoutInflater)
    }

    override fun initMain() {

    }
}
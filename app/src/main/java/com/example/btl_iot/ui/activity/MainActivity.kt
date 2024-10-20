package com.example.btl_iot.ui.activity

import com.example.btl_iot.base.BaseActivity
import com.example.btl_iot.databinding.ActivityMainBinding

class MainActivity : BaseActivity<ActivityMainBinding>() {
    override fun createBinding(): ActivityMainBinding {
        return ActivityMainBinding.inflate(layoutInflater)
    }

    override fun initMain() {

    }
}
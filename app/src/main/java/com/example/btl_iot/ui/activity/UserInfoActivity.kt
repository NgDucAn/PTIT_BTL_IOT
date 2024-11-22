package com.example.btl_iot.ui.activity

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.btl_iot.R
import com.example.btl_iot.base.BaseActivity
import com.example.btl_iot.databinding.ActivityUserInfoBinding

class UserInfoActivity : BaseActivity<ActivityUserInfoBinding>() {
    override fun createBinding(): ActivityUserInfoBinding {
        return ActivityUserInfoBinding.inflate(layoutInflater)
    }

    override fun initMain() {

    }
}
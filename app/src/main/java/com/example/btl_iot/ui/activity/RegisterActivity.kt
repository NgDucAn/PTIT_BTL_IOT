package com.example.btl_iot.ui.activity

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.btl_iot.R
import com.example.btl_iot.base.BaseActivity
import com.example.btl_iot.databinding.ActivityRegisterBinding

class RegisterActivity : BaseActivity<ActivityRegisterBinding>() {
    override fun createBinding(): ActivityRegisterBinding {
        return ActivityRegisterBinding.inflate(layoutInflater)
    }

    override fun initMain() {
        initView()
    }

    private fun initView() {
        with(binding) {
            btRegister.setOnClickListener {
                startActivity(Intent(this@RegisterActivity, HomeActivity::class.java))
            }
            tvLoginNow.setOnClickListener {
                startActivity(Intent(this@RegisterActivity, LoginActivity::class.java))
            }
        }
    }
}
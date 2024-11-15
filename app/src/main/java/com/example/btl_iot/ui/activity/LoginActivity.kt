package com.example.btl_iot.ui.activity

import android.content.Intent
import com.example.btl_iot.base.BaseActivity
import com.example.btl_iot.databinding.ActivityLoginBinding

class LoginActivity : BaseActivity<ActivityLoginBinding>() {
    override fun createBinding(): ActivityLoginBinding {
        return ActivityLoginBinding.inflate(layoutInflater)
    }

    override fun initMain() {
        initView()
    }

    private fun initView() {
        with(binding) {
            btLogin.setOnClickListener {
                startActivity(Intent(this@LoginActivity, HomeActivity::class.java))
            }
            tvRegisterNow.setOnClickListener {
                startActivity(Intent(this@LoginActivity, RegisterActivity::class.java))
            }
        }
    }
}
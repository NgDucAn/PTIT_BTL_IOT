package com.example.btl_iot.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.viewbinding.ViewBinding
import com.example.btl_iot.R
import com.example.btl_iot.utils.AndroidUtils.hideSystemNavigationBar

abstract class BaseActivity<VB : ViewBinding> : AppCompatActivity() {
    protected lateinit var binding: VB

    abstract fun createBinding(): VB

    abstract fun initMain()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = createBinding()
        setContentView(binding.root)

        val window = this.window
        window.statusBarColor = ContextCompat.getColor(this, R.color.white)

        hideSystemNavigationBar(this)
        initMain()
    }

    override fun onResume() {
        super.onResume()
        hideSystemNavigationBar(this)
    }
}
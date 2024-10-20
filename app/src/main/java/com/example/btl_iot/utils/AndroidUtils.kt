package com.example.btl_iot.utils

import android.app.Activity
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat

object AndroidUtils {
    fun hideSystemNavigationBar(activity: Activity) {
        val window = activity.window
        // Set layout to not extend into the status bar
        WindowInsetsControllerCompat(window, window.decorView).let { controller ->
            // Hide the navigation bar
            controller.hide(WindowInsetsCompat.Type.navigationBars())
            // Auto-hide navigation bar
            controller.systemBarsBehavior = WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        }
    }
}
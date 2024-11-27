package com.example.btl_iot.ui.activity

import android.content.Intent
import android.text.InputType
import android.view.MotionEvent
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import com.example.btl_iot.R
import com.example.btl_iot.api.Api
import com.example.btl_iot.base.BaseActivity
import com.example.btl_iot.databinding.ActivityLoginBinding
import com.example.btl_iot.model.LoginRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException

class LoginActivity : BaseActivity<ActivityLoginBinding>() {
    private var isPasswordVisible = false

    override fun createBinding(): ActivityLoginBinding {
        return ActivityLoginBinding.inflate(layoutInflater)
    }

    override fun initMain() {
        initView()
        setupPasswordToggle()
    }

    private fun setupPasswordToggle() {
        // Lắng nghe sự kiện chạm vào icon mắt
        binding.etPassword.setOnTouchListener { _, event ->
            if (event.action == MotionEvent.ACTION_UP) {
                val drawableEnd = binding.etPassword.compoundDrawables[2]
                if (drawableEnd != null && event.rawX >= (binding.etPassword.right - drawableEnd.bounds.width())) {
                    togglePasswordVisibility()
                    true
                } else {
                    false
                }
            } else {
                false
            }
        }
    }

    private fun togglePasswordVisibility() {
        if (isPasswordVisible) {
            // Ẩn mật khẩu
            binding.etPassword.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
            binding.etPassword.setCompoundDrawablesWithIntrinsicBounds(
                null, null, ContextCompat.getDrawable(this, R.drawable.ic_eye_closed), null
            )
        } else {
            // Hiển thị mật khẩu
            binding.etPassword.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
            binding.etPassword.setCompoundDrawablesWithIntrinsicBounds(
                null, null, ContextCompat.getDrawable(this, R.drawable.ic_eye_open), null
            )
        }
        // Đặt lại trạng thái cursor
        binding.etPassword.setSelection(binding.etPassword.text?.length ?: 0)

        // Đổi trạng thái hiển thị
        isPasswordVisible = !isPasswordVisible
    }

    private fun initView() {
        with(binding) {
            tvRegisterNow.setOnClickListener {
                startActivity(Intent(this@LoginActivity, RegisterActivity::class.java))
            }

            binding.btLogin.setOnClickListener {
                val username = binding.etUserName.text.toString().trim()
                val password = binding.etPassword.text.toString().trim()

                if (username.isNotEmpty() && password.isNotEmpty()) {
                    loginUser(username, password)
                } else if (username.isEmpty()) {
                    tvError.visibility = View.VISIBLE
                    tvError.text = "Please enter your username"
                } else if(password.isEmpty()) {
                    tvError.visibility = View.VISIBLE
                    tvError.text = "Please enter your password"
                } else if(username.isEmpty() && password.isEmpty()) {
                    tvError.visibility = View.VISIBLE
                    tvError.text = "All fields are required"
                } else {
                    tvError.visibility = View.VISIBLE
                }
            }
        }
    }

    private fun loginUser(username: String, password: String) {
        lifecycleScope.launch(Dispatchers.IO) {
            try {
                val loginRequest = LoginRequest(username, password)
                val response = Api.getApi().loginUser(loginRequest)

                withContext(Dispatchers.Main) {
                    if (response.isSuccessful) {
                        val loginResponse = response.body()
                        loginResponse?.let {
                            if (it.status == 200) {
                                Toast.makeText(
                                    this@LoginActivity,
                                    it.message,
                                    Toast.LENGTH_LONG
                                ).show()

                                // Lưu token vào SharedPreferences
                                saveTokens(it.accessToken, it.refreshToken)

                                // Chuyển đến màn hình chính
                                val intent = Intent(this@LoginActivity, HomeActivity::class.java)
                                intent.putExtra("username", it.user.username)
                                startActivity(intent)
                                finish()
                            } else {
                                binding.tvError.visibility = View.VISIBLE
                                binding.tvError.text = "Incorrect username or password"
//                                Toast.makeText(
//                                    this@LoginActivity,
//                                    "Failed: ${it.message}",
//                                    Toast.LENGTH_LONG
//                                ).show()
                            }
                        }
                    } else {
                        val errorBody = response.errorBody()?.string()
                        binding.tvError.visibility = View.VISIBLE
                        binding.tvError.text = "Incorrect username or password"
                        Toast.makeText(
                            this@LoginActivity,
                            "Error: $errorBody",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
            } catch (e: HttpException) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(this@LoginActivity, "Http Error: ${e.message}", Toast.LENGTH_LONG).show()
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(this@LoginActivity, "Error: ${e.message}", Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    private fun saveTokens(accessToken: String, refreshToken: String) {
        val sharedPreferences = getSharedPreferences("auth_prefs", MODE_PRIVATE)
        with(sharedPreferences.edit()) {
            putString("access_token", accessToken)
            putString("refresh_token", refreshToken)
            apply()
        }
    }
}
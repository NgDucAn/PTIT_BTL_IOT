package com.example.btl_iot.ui.activity

import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.example.btl_iot.R
import com.example.btl_iot.api.Api
import com.example.btl_iot.base.BaseActivity
import com.example.btl_iot.databinding.ActivityRegisterBinding
import com.example.btl_iot.model.UserRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException

class RegisterActivity : BaseActivity<ActivityRegisterBinding>() {
    private var isPasswordVisible = false

    override fun createBinding(): ActivityRegisterBinding {
        return ActivityRegisterBinding.inflate(layoutInflater)
    }

    override fun initMain() {
        initView()
    }

    private fun initView() {
        with(binding) {
            setupPasswordToggle()

            tvLoginNow.setOnClickListener {
                startActivity(Intent(this@RegisterActivity, LoginActivity::class.java))
            }

            btRegister.setOnClickListener {
                val username = etUserName.text.toString().trim()
                val password = etPassword.text.toString().trim()
                val email = etEmail.text.toString().trim()

                if (username.isNotEmpty() && password.isNotEmpty() && email.isNotEmpty()) {
                    registerUser(username, password, email)
                } else if (username.isEmpty() || password.isEmpty() || email.isEmpty()) {
                    tvError.visibility = View.VISIBLE
                    tvError.text = "All fields are required"
                    return@setOnClickListener
                } else if (!isEmailValid(email)) {
                    tvError.visibility = View.VISIBLE
                    tvError.text = "Invalid email format"
                    return@setOnClickListener
                } else if (password.length < 8) {
                    tvError.visibility = View.VISIBLE
                    tvError.text = "Password must be at least 8 characters"
                    return@setOnClickListener
                }
            }
        }
    }

    private fun setupPasswordToggle() {
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

    private fun isEmailValid(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    private fun registerUser(username: String, password: String, email: String) {
        lifecycleScope.launch(Dispatchers.IO) {
            try {
                val userRequest = UserRequest(username, password, email)
                Log.d("Register Request", "Sending: $userRequest")

                val response = Api.getApi().registerUser(userRequest)

                withContext(Dispatchers.Main) {
                    if (response.isSuccessful) {
                        val registerResponse = response.body()
                        registerResponse?.let {
                            if (it.status == 200) {
                                Toast.makeText(
                                    this@RegisterActivity,
                                    it.message,
                                    Toast.LENGTH_LONG
                                ).show()
                                Log.d("Register Success", "User ID: ${it.data.id}, Email: ${it.data.email}")

                                val intent = Intent(this@RegisterActivity, LoginActivity::class.java)
                                startActivity(intent)
                                finish()
                            } else {
                                Toast.makeText(
                                    this@RegisterActivity,
                                    "Failed: ${it.message}",
                                    Toast.LENGTH_LONG
                                ).show()
                            }
                        }
                    } else {
                        val errorBody = response.errorBody()?.string()
                        Log.e("Register Error", "Response Code: ${response.code()}, Error: $errorBody")
                        Toast.makeText(
                            this@RegisterActivity,
                            "Failed to register: $errorBody",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
            } catch (e: HttpException) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(this@RegisterActivity, "Http Error: ${e.message}", Toast.LENGTH_LONG).show()
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(this@RegisterActivity, "Error: ${e.message}", Toast.LENGTH_LONG).show()
                }
            }
        }
    }
}
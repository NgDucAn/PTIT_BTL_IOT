package com.example.btl_iot.model

data class RegisterResponse(
    val status: Int,
    val title: String,
    val message: String,
    val data: UserData
)

data class UserData(
    val id: Int,
    val username: String,
    val password: String, // Lưu ý: Mã hóa, không nên hiển thị
    val email: String,
    val updatedAt: String,
    val createdAt: String
)


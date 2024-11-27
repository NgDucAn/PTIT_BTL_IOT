package com.example.btl_iot.model

data class LoginResponse(
    val status: Int,
    val title: String,
    val message: String,
    val user: User,
    val accessToken: String,
    val refreshToken: String
)

data class User(
    val username: String,
    val password: String
)



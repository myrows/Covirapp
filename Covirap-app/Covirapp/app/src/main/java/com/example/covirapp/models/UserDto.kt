package com.example.covirapp.models

data class UserDto(
    val fullName: String,
    val password: String,
    val password2: String,
    val province: String,
    val status: String,
    val username: String
)
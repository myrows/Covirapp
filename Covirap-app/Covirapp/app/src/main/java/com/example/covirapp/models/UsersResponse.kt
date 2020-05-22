package com.example.covirapp.models

class UsersResponse : ArrayList<UsersResponseItem>()

data class UsersResponseItem(
    val fullName: String,
    val id: Int,
    val password: String,
    val province: String,
    val avatar: String,
    val status: String,
    val username: String
)
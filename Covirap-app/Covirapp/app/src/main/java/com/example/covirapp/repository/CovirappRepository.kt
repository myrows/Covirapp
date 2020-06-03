package com.example.covirapp.repository

import com.example.covirapp.api.CovirappService
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CovirappRepository @Inject constructor( var covirappService: CovirappService) {

    suspend fun getUsers() = covirappService.getAllUsers()
    suspend fun getUsersProvince() = covirappService.getUsersByOwnProvince()

}
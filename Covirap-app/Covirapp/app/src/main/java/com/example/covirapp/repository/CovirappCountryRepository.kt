package com.example.covirapp.repository

import com.example.covirapp.api.CovirappCountryService
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CovirappCountryRepository @Inject constructor(var covirappService: CovirappCountryService) {

    suspend fun getCountries() = covirappService.getAllCountries()

}
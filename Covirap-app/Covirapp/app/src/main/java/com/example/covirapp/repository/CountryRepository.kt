package com.example.covirapp.repository

import com.example.covirapp.api.CountryService
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CountryRepository @Inject constructor( var covidService : CountryService) {

    suspend fun getCountry() = covidService.getCountriesWithStats()
}
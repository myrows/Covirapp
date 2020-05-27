package com.example.covirapp.repository

import com.example.covirapp.api.CovirappCountryService
import com.example.covirapp.common.SharedPreferencesManager
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CovirappCountryRepository @Inject constructor(var covirappService: CovirappCountryService) {

    var pattern = "yyyy-MM-dd"
    var simpleDateFormat: SimpleDateFormat = SimpleDateFormat(pattern)
    var date: String = simpleDateFormat.format(Date())
    var countryName : String = SharedPreferencesManager.SharedPreferencesManager.getSomeStringValue("nameCountry").toString()

        suspend fun getRegionsOfCounty() = covirappService.getRegionsOfCovid( date, countryName )

}
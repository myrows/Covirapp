package com.example.covirapp.repository

import com.example.covirapp.api.CovirappCountryService
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CovirappCountryRepository @Inject constructor(var covirappService: CovirappCountryService) {

    var pattern = "yyyy-MM-dd"
    var simpleDateFormat: SimpleDateFormat = SimpleDateFormat(pattern)
    var date: String = simpleDateFormat.format(Date())

        suspend fun getRegionsOfCounty() = covirappService.getRegionsOfCovid( date, "spain" )

}
package com.example.covirapp.models

class PaisesResponse : ArrayList<PaisesResponseItem>()

data class PaisesResponseItem(
    val active: Int,
    val activePerOneMillion: Double,
    val cases: Int,
    val casesPerOneMillion: Int,
    val continent: String,
    val country: String,
    val countryInfo: CountryInfo,
    val critical: Int,
    val criticalPerOneMillion: Int,
    val deaths: Int,
    val deathsPerOneMillion: Double,
    val population: Int,
    val recovered: Int,
    val recoveredPerOneMillion: Double,
    val tests: Int,
    val testsPerOneMillion: Int,
    val todayCases: Int,
    val todayDeaths: Int,
    val updated: Long
)

data class CountryInfo(
    val _id: Int,
    val flag: String,
    val iso2: String,
    val iso3: String,
    val lat: Int,
    val long: Int
)
package com.example.covirapp.models

import com.google.gson.annotations.SerializedName

data class Pais (

    @SerializedName("updated") val updated : Int,
    @SerializedName("country") val country : String,
    @SerializedName("countryInfo") val countryInfo : CountryInfo,
    @SerializedName("cases") val cases : Int,
    @SerializedName("todayCases") val todayCases : Int,
    @SerializedName("deaths") val deaths : Int,
    @SerializedName("todayDeaths") val todayDeaths : Int,
    @SerializedName("recovered") val recovered : Int,
    @SerializedName("active") val active : Int,
    @SerializedName("critical") val critical : Int,
    @SerializedName("casesPerOneMillion") val casesPerOneMillion : Int,
    @SerializedName("deathsPerOneMillion") val deathsPerOneMillion : Int,
    @SerializedName("tests") val tests : Int,
    @SerializedName("testsPerOneMillion") val testsPerOneMillion : Int,
    @SerializedName("population") val population : Int,
    @SerializedName("continent") val continent : String,
    @SerializedName("activePerOneMillion") val activePerOneMillion : Double,
    @SerializedName("recoveredPerOneMillion") val recoveredPerOneMillion : Double,
    @SerializedName("criticalPerOneMillion") val criticalPerOneMillion : Double
)
package com.example.covirapp.models

class GraphicCountryResponse : ArrayList<GraphicCountryResponseItem>()

data class GraphicCountryResponseItem(
    val Active: Int,
    val City: String,
    val CityCode: String,
    val Confirmed: Int,
    val Country: String,
    val CountryCode: String,
    val Date: String,
    val Deaths: Int,
    val Lat: String,
    val Lon: String,
    val Province: String,
    val Recovered: Int
)
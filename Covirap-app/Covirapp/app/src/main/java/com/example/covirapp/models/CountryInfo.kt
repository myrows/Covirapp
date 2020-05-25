package com.example.covirapp.models

import com.google.gson.annotations.SerializedName

data class CountryInfo (

    @SerializedName("_id") val _id : Int,
    @SerializedName("iso2") val iso2 : String,
    @SerializedName("iso3") val iso3 : String,
    @SerializedName("lat") val lat : Int,
    @SerializedName("long") val long : Int,
    @SerializedName("flag") val flag : String
)
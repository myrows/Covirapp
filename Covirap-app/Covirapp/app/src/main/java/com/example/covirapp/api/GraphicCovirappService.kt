package com.example.covirapp.api

import com.example.covirapp.models.GraphicCountryResponse
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface GraphicCovirappService {

    @GET("/country/{country}")
    fun getDataOfCountry( @Path("country") country : String ) : Call<GraphicCountryResponse>

}
package com.example.covirapp.api

import com.example.covirapp.models.NewRegionsResponse
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*


interface CovirappCountryService {

    @GET("/api/{date}/country/{country}")
    fun getRegionsOfCovid( @Path("date") date : String , @Path("country") country : String ) : Call<NewRegionsResponse>



}
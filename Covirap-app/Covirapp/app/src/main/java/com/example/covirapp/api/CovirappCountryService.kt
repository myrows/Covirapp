package com.example.covirapp.api

import com.example.covirapp.models.PaisesResponse
import retrofit2.Response
import retrofit2.http.*


interface CovirappCountryService {

    @GET("/v2/countries")
    suspend fun getAllCountries() : Response<PaisesResponse>


}
package com.example.covirapp.api

import com.example.covirapp.models.CountryResponse
import retrofit2.Response
import retrofit2.http.*

interface CountryService {

    @GET("countries")
    suspend fun getCountriesWithStats( ) : Response<CountryResponse>
}
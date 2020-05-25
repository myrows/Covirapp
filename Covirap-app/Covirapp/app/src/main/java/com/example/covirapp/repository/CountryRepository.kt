package com.example.covirapp.repository

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.covirapp.api.CovirappCountryService
import com.example.covirapp.api.CovirappService
import com.example.covirapp.api.generator.ServiceGenerator
import com.example.covirapp.di.MyApplication
import com.example.covirapp.models.Pais
import com.example.covirapp.models.PaisResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CountryRepository {

    var covidService : CovirappService
    var countries : LiveData<List<Pais>>
    var serviceGenerator : ServiceGenerator = ServiceGenerator()

    init {
        covidService = serviceGenerator.createServiceUser(CovirappService::class.java)
        countries = getAllCountriesCovid()
    }

    fun getAllCountriesCovid(): LiveData<List<Pais>> {
        var dataCountries : MutableLiveData<List<Pais>> = MutableLiveData()
        val call : Call<PaisResponse> = covidService.getCountriesOfCovid()
        call.enqueue( object : Callback<PaisResponse> {
            override fun onResponse(
                call: Call<PaisResponse>,
                response: Response<PaisResponse>
            ) {
                if ( response.isSuccessful ) {
                    dataCountries.value = response.body()
                    Log.d("CountryRepository","${response.body()}")
                }
            }

            override fun onFailure(call: Call<PaisResponse>, t: Throwable) {
                Toast.makeText(MyApplication.instance, "Error de conexión", Toast.LENGTH_LONG).show()
            }
        })
        return dataCountries
    }
}
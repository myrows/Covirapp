package com.example.covirapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.covirapp.models.Pais
import com.example.covirapp.models.PaisResponse
import com.example.covirapp.repository.CountryRepository

class CountryViewModel : ViewModel() {

    var countryRepository : CountryRepository
    var countries : LiveData<List<Pais>>

    init {
        countryRepository = CountryRepository()
        countries = countryRepository.getAllCountriesCovid()
    }

    fun getAllCountries() : LiveData<List<Pais>> {
        return countries
    }
}
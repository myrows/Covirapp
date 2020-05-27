package com.example.covirapp.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.covirapp.common.Resource
import com.example.covirapp.models.CountryResponse
import com.example.covirapp.repository.CountryRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

class CountryViewModel @Inject constructor( private val countryRepository: CountryRepository ) : ViewModel() {

    var countryApi : MutableLiveData<Resource<CountryResponse>> = MutableLiveData()

    init {
        getCountries()
    }

    fun getCountries() = viewModelScope.launch {
        countryApi.value = Resource.Loading()
        delay(2000)
        val response = countryRepository.getCountry()
        countryApi.value = handleCountriesCovirapp( response )
    }

    private fun handleCountriesCovirapp ( response: Response<CountryResponse>) : Resource<CountryResponse> {
        if ( response.isSuccessful ) {
            response.body()?.let { resultResponse ->
                return Resource.Success(resultResponse)
            }
        }
        return Resource.Error(response.message())
    }
}
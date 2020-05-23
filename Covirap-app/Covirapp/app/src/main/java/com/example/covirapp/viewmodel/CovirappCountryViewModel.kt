package com.example.covirapp.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.covirapp.api.CovirappCountryService
import com.example.covirapp.common.Resource
import com.example.covirapp.models.PaisesResponse
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

class CovirappCountryViewModel @Inject constructor(
    private val covirappRepository: CovirappCountryService
) : ViewModel() {

    var countries : MutableLiveData<Resource<PaisesResponse>> = MutableLiveData()

    init {
        getCountries()
        Log.i("MOVIES", "theMovieDBRepository en MovieViewModel: $covirappRepository")
    }

    fun getCountries() = viewModelScope.launch {
        countries.value = Resource.Loading()
        delay(3000)
        val response = covirappRepository.getAllCountries()
        countries.value = handleCountriesCovirapp( response )
    }

    private fun handleCountriesCovirapp ( response: Response<PaisesResponse> ) : Resource<PaisesResponse> {
        if ( response.isSuccessful ) {
            response.body()?.let { resultResponse ->
                return Resource.Success(resultResponse)
            }
        }
        return Resource.Error(response.message())
    }
}
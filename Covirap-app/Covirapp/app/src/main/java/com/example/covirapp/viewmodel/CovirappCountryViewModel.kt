package com.example.covirapp.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.covirapp.api.CovirappCountryService
import com.example.covirapp.common.Resource
import com.example.covirapp.models.NewRegionsResponse
import com.example.covirapp.models.UsersResponse
import com.example.covirapp.repository.CovirappCountryRepository
import com.example.covirapp.repository.CovirappRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

class CovirappCountryViewModel @Inject constructor(
    private val covirappRepository: CovirappCountryRepository
) : ViewModel() {

    var countriesApi : MutableLiveData<Resource<NewRegionsResponse>> = MutableLiveData()

    init {
        getCountriesApi()
        Log.i("MOVIES", "theMovieDBRepository en MovieViewModel: $covirappRepository")
    }

    fun getCountriesApi() = viewModelScope.launch {
        countriesApi.value = Resource.Loading()
        delay(2000)
        val response = covirappRepository.getRegionsOfCounty()
        countriesApi.value = handleCountriesCovirapp( response )
    }

    private fun handleCountriesCovirapp ( response: Response<NewRegionsResponse> ) : Resource<NewRegionsResponse> {
        if ( response.isSuccessful ) {
            response.body()?.let { resultResponse ->
                return Resource.Success(resultResponse)
            }
        }
        return Resource.Error(response.message())
    }
}
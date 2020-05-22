package com.example.covirapp.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.covirapp.common.Resource
import com.example.covirapp.models.UsersResponse
import com.example.covirapp.repository.CovirappRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

class UsersViewModel @Inject constructor(
    private val covirappRepository: CovirappRepository
) : ViewModel() {

    var users : MutableLiveData<Resource<UsersResponse>> = MutableLiveData()

    init {
        getUsers()
        Log.i("MOVIES", "theMovieDBRepository en MovieViewModel: $covirappRepository")
    }

    fun getUsers() = viewModelScope.launch {
        users.value = Resource.Loading()
        delay(3000)
        val response = covirappRepository.getUsers()
        users.value = handleCovirapp( response)
    }

    private fun handleCovirapp ( response: Response<UsersResponse> ) : Resource<UsersResponse> {
        if ( response.isSuccessful ) {
            response.body()?.let { resultResponse ->
                return Resource.Success(resultResponse)
            }
        }
        return Resource.Error(response.message())
    }
}
package com.example.covirapp.api

import com.example.covirapp.models.Login
import com.example.covirapp.models.UsersResponse
import com.example.covirapp.response.LoginResponse
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*
import java.util.*

interface CovirappService {

    @POST("/oauth/token")
    @FormUrlEncoded
    fun login ( @Field("grant_type") grant_type : String, @Field("username") username : String,
                @Field("password") password : String) : Call<LoginResponse>

    @GET("/covirapp/user")
    suspend fun getAllUsers(): Response<UsersResponse>

    @GET("/covirapp/files/{fileName}")
    fun getAvatar( @Path("fileName") fileName : String) : Call<ResponseBody>


}
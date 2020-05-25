package com.example.covirapp.api

import com.example.covirapp.models.Login
import com.example.covirapp.models.PaisResponse
import com.example.covirapp.models.UsersResponse
import com.example.covirapp.response.LoginResponse
import okhttp3.ResponseBody
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.http.*
import java.util.*

interface CovirappService {

    @POST("/oauth/token")
    @FormUrlEncoded
    fun login ( @Field("grant_type") grant_type : String, @Field("username") username : String,
                @Field("password") password : String) : Call<LoginResponse>

    @Multipart
    @POST("/covirapp/user")
    fun register(@Part("username") username : RequestBody, @Part("password") password : RequestBody,
                 @Part("fullName") fullName : RequestBody,
                 @Part("province") province : RequestBody,
                 @Part uploadfile : MultipartBody.Part) : Call<ResponseBody>

    @GET("/covirapp/user")
    suspend fun getAllUsers(): Response<UsersResponse>

    @GET("/covirapp/files/{fileName}")
    fun getAvatar( @Path("fileName") fileName : String) : Call<ResponseBody>

    @GET("/covirapp/user/me/province")
    suspend fun getUsersByOwnProvince() : Response<UsersResponse>

    @GET("/v2/countries")
    fun getCountriesOfCovid() : Call<PaisResponse>
}
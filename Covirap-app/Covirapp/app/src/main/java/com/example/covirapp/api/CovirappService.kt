package com.example.covirapp.api

import com.example.covirapp.models.*
import com.example.covirapp.response.LoginResponse
import okhttp3.ResponseBody
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*

interface CovirappService {

    @GET("/covirapp/user")
    suspend fun getAllUsers(): Response<UsersResponse>

    @GET("/covirapp/files/{fileName}")
    fun getAvatar( @Path("fileName") fileName : String) : Call<ResponseBody>

    @GET("/covirapp/user/me/province")
    suspend fun getUsersByOwnProvince() : Response<UsersResponse>

    @GET("/covirapp/user/me")
    fun getUserDataAuthenticated() : Call<UsersResponseItem>

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

    @POST("/covirapp/quiz")
    fun createQuiz ( @Body quiz : QuizDto ) : Call<QuizResponse>

    @PUT("/covirapp/user/{id}")
    fun editUser( @Path ("id") id : Long, @Body newUser : UserDto ) : Call<ResponseBody>

    @PUT("/covirapp/user/me/status")
    fun updateStatus( @Body user : UserDto ) : Call<UsersResponseItem>

    @PUT("/covirapp/quiz/status/")
    fun statusTest( @Body quiz : QuizDto ) : Call<ResponseBody>

    @GET("/covirapp/province/{name}")
    fun getProvinceByName( @Path("name") name : String ) : Call<ProvinceDto>
}
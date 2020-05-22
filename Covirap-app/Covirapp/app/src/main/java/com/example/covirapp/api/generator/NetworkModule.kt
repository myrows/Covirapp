package com.example.covirapp.api.generator

import android.content.Context
import android.content.SharedPreferences
import android.provider.Settings.Global.getString
import com.example.covirapp.api.CovirappService
import com.example.covirapp.common.Constantes
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton
@Module
class NetworkModule {

    @Singleton
    @Provides
    @Named("url")
    fun provideBaseUrl(): String = Constantes.API_BASE_URL

    @Singleton
    @Provides
    fun provideOkHttpClient(theMovieDBInterceptor: TheMovieDBInterceptor): OkHttpClient {

        return with(OkHttpClient.Builder()) {
            addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.HEADERS))
            addInterceptor(OauthInterceptor())
            addInterceptor(theMovieDBInterceptor)
            connectTimeout(Constantes.TIMEOUT_INMILIS, TimeUnit.MILLISECONDS)
            build()
        }
    }

    @Singleton
    @Provides
    fun provideTheMovieDBRetrofitService(@Named("url") baseUrl: String, okHttpClient: OkHttpClient): CovirappService {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
            .create(CovirappService::class.java)
    }
}
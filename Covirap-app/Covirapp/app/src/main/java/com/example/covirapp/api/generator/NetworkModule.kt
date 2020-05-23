package com.example.covirapp.api.generator

import com.example.covirapp.api.CovirappCountryService
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
    @Named("urlCovid")
    fun provideCovidBaseUrl(): String = Constantes.API_BASE_URL_COVID

    @Singleton
    @Provides
    fun provideOkHttpClient(theMovieDBInterceptor: TheMovieDBInterceptor): OkHttpClient {

        return with(OkHttpClient.Builder()) {
            addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.HEADERS))
            addInterceptor(OauthInterceptor())
            addInterceptor(CovidInterceptor())
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

    @Singleton
    @Provides
    fun provideCovidRetrofitService(@Named("urlCovid") baseUrl: String, okHttpClient: OkHttpClient): CovirappCountryService {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
            .create(CovirappCountryService::class.java)
    }
}
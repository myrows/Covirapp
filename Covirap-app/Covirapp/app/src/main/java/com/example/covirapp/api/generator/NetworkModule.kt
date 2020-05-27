package com.example.covirapp.api.generator

import com.example.covirapp.api.CountryService
import com.example.covirapp.api.CovirappCountryService
import com.example.covirapp.api.CovirappService
import com.example.covirapp.api.GraphicCovirappService
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
    @Named("urlCovidRegions")
    fun provideCovidBaseUrl(): String = Constantes.API_BASE_URL_REGIONS

    @Singleton
    @Provides
    @Named("urlCovidCountry")
    fun provideCovidCountryBaseUrl(): String = Constantes.API_BASE_URL_COVID_COUNTRY

    @Singleton
    @Provides
    @Named("okHttpClientApiCovid")
    fun provideOkHttpClientCredentials(covirappInterceptor: CovirappInterceptor): OkHttpClient {

        return with(OkHttpClient.Builder()) {
            addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.HEADERS))
            addInterceptor(OauthInterceptor())
            addInterceptor(covirappInterceptor)
            connectTimeout(Constantes.TIMEOUT_INMILIS, TimeUnit.MILLISECONDS)
            build()
        }
    }

    @Singleton
    @Provides
    @Named("okHttpClientRegions")
    fun provideOkHttpClientRegions(apiRegionsInterceptor: CovidInterceptor): OkHttpClient {

        return with(OkHttpClient.Builder()) {
            addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.HEADERS))
            addInterceptor(apiRegionsInterceptor)
            connectTimeout(Constantes.TIMEOUT_INMILIS, TimeUnit.MILLISECONDS)
            build()
        }
    }

    @Singleton
    @Provides
    @Named("okHttpClientCountries")
    fun provideOkHttpClientCountries(apiCountriesInterceptor: CovidInterceptor): OkHttpClient {

        return with(OkHttpClient.Builder()) {
            addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.HEADERS))
            addInterceptor(apiCountriesInterceptor)
            connectTimeout(Constantes.TIMEOUT_INMILIS, TimeUnit.MILLISECONDS)
            build()
        }
    }

    @Singleton
    @Provides
    fun provideOwnApiRetrofitService(@Named("url") baseUrl: String, @Named("okHttpClientApiCovid") okHttpClient: OkHttpClient): CovirappService {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
            .create(CovirappService::class.java)
    }

    @Singleton
    @Provides
    fun provideCovidRegionsRetrofitService(@Named("urlCovidRegions") baseUrl: String, @Named("okHttpClientRegions") okHttpClient: OkHttpClient): CovirappCountryService {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
            .create(CovirappCountryService::class.java)
    }

    @Singleton
    @Provides
    fun provideCovidCountryRetrofitService(@Named("urlCovidCountry") baseUrl: String, @Named("okHttpClientCountries") okHttpClient: OkHttpClient): CountryService {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
            .create(CountryService::class.java)
    }
}
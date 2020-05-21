package com.example.covirapp.api.generator

import com.example.covirapp.common.Constantes
import com.example.covirapp.common.SharedPreferencesManager
import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import org.jetbrains.annotations.NotNull
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import java.util.concurrent.TimeUnit


class ServiceGenerator {
    private val logging =
        HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.HEADERS)

    private val httpClientBuilder = OkHttpClient.Builder()

    private val httpClient = OkHttpClient.Builder()
        .connectTimeout(1, TimeUnit.MINUTES)
        .readTimeout(30, TimeUnit.SECONDS)
        .writeTimeout(15, TimeUnit.SECONDS)
        .build()

    private val builder = Retrofit.Builder()
        .baseUrl(Constantes.API_BASE_URL)
        .client(httpClient)
        .addConverterFactory(GsonConverterFactory.create())

    var retrofit: Retrofit? = null

    fun <S> createServiceLogin(serviceClass: Class<S>?): S {
        if (!httpClient.interceptors.contains(logging)) {
            httpClientBuilder.addInterceptor(object : Interceptor {
                @Throws(IOException::class)
                override fun intercept(chain: Interceptor.Chain): Response {
                    val original: Request = chain.request()
                    // Request customization: add request headers
                    val requestBuilder: Request.Builder = original.newBuilder()
                    val request: Request = requestBuilder.build()
                    return chain.proceed(request)
                }
            })
            httpClientBuilder.addInterceptor(logging)
            httpClientBuilder.addInterceptor(BasicAuthInterceptor("my-client", "secret"))
            builder.client(httpClientBuilder.build())
            retrofit = builder.build()
        }
        return retrofit!!.create(serviceClass)
    }

    fun <S> createServiceRegister(serviceClass: Class<S>?): S {
        val httpClientBuilder = OkHttpClient.Builder()
        httpClientBuilder.addInterceptor(object : Interceptor {
            @NotNull
            @Throws(IOException::class)
            override fun intercept(@NotNull chain: Interceptor.Chain): Response {
                val original: Request = chain.request()
                val originalHttpUrl: HttpUrl = original.url
                val url = originalHttpUrl.newBuilder()
                    .build()
                val requestBuilder: Request.Builder = original.newBuilder()
                    .url(url)
                val request: Request = requestBuilder.build()
                return chain.proceed(request)
            }
        })
        httpClientBuilder.addInterceptor(logging)
        builder.client(httpClientBuilder.build())
        retrofit = builder.build()
        return retrofit!!.create(serviceClass)
    }

    fun <S> createServiceNota(serviceClass: Class<S>?): S {
        val tokenUserLogged = SharedPreferencesManager.SharedPreferencesManager.getSomeStringValue("tokenId")
        val httpClientBuilder = OkHttpClient.Builder()
        httpClientBuilder.addInterceptor(object : Interceptor {
            @Throws(IOException::class)
            override fun intercept(chain: Interceptor.Chain): Response {
                val original: Request = chain.request()
                val originalHttpUrl = original.url
                val url = originalHttpUrl.newBuilder()
                    .build()
                val requestBuilder = original.newBuilder()
                    .header("Authorization","Bearer "+ tokenUserLogged)
                    .url(url)
                val request = requestBuilder.build()
                return chain.proceed(request)
            }
        })
        httpClientBuilder.addInterceptor(logging)
        builder.client(httpClientBuilder.build())
        retrofit = builder.build()
        return retrofit!!.create(serviceClass)
    }
}
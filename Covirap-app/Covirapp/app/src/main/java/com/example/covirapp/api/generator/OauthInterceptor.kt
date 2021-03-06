package com.example.covirapp.api.generator

import com.example.covirapp.common.SharedPreferencesManager
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class OauthInterceptor @Inject constructor(): Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {

        // Añadimos parámetros a la URL de la cadena que recibimos (chain)
        val urlWithParams = chain.request()
            .url
            .newBuilder()
            .build()

        var request = chain.request()

        var token = SharedPreferencesManager.SharedPreferencesManager.getSomeStringValue("tokenId").toString()

        request = request.newBuilder()
            .url(urlWithParams)
            .header("Authorization","Bearer "+ token)
            .build()

        return chain.proceed(request)

    }
}
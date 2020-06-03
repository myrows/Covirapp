package com.example.covirapp.api.generator

import java.io.IOException;
import okhttp3.Credentials;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BasicAuthInterceptor @Inject constructor(user: String, password: String) :
    Interceptor {

    private val credentials: String = Credentials.basic(user, password)

    override fun intercept(chain: Interceptor.Chain): Response {
        val request: Request = chain.request()
        val authenticatedRequest: Request = request.newBuilder()
            .header("Authorization", credentials).build()
        return chain.proceed(authenticatedRequest)
    }

}
package com.example.damkeep.api.generator

import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import org.jetbrains.annotations.NotNull
import java.io.IOException

class AuthenticationInterceptor(private val authToken: String) : Interceptor {

    @NotNull
    @Throws(IOException::class)
    override fun intercept(@NotNull chain: Interceptor.Chain): Response {
        val original: Request = chain.request()
        val builder: Request.Builder = original.newBuilder()
            .header("Authorization", authToken)
        val request: Request = builder.build()
        return chain.proceed(request)
    }

}
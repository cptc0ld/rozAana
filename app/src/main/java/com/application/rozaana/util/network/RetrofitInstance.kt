package com.application.rozaana.util.network

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitInstance {
    private lateinit var basicInstance: Retrofit

    private fun getBodyLevelLoggingInterceptor(): Interceptor {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        return httpLoggingInterceptor
    }

    @Synchronized
    private fun createBasicRetrofitInstance(baseUrl: String) {
        basicInstance = Retrofit.Builder()
            .baseUrl("$baseUrl/")
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(
                getOkHttpBuilder()
                    .connectTimeout(60, TimeUnit.SECONDS)
                    .readTimeout(60, TimeUnit.SECONDS)
                    .writeTimeout(60, TimeUnit.SECONDS)
                    .addInterceptor(getBodyLevelLoggingInterceptor())
                    .build()
            )
            .build()
    }
    private fun getOkHttpBuilder() = OkHttpClient.Builder()


    @Synchronized
    fun getBasicRetroFitInstance(baseUrl: String): Retrofit {
        if (!RetrofitInstance::basicInstance.isInitialized) createBasicRetrofitInstance(baseUrl)
        return basicInstance
    }
}

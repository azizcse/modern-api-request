package com.example.expert.util

import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object ApiService {

    private val CHANNEL_BASE_URL by lazy { "https://gist.githubusercontent.com/" }
    private val YOUTUBE_BASE_URL by lazy { "https://www.googleapis.com/" }

    fun getYoutubeClient(): ApiClient = Retrofit.Builder()
        .baseUrl(YOUTUBE_BASE_URL)
        .client(buildHttpClient())
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(CoroutineCallAdapterFactory.invoke())
        .build()
        .create(ApiClient::class.java)

    fun getChannelClient(): ApiClient = Retrofit.Builder()
        .baseUrl(CHANNEL_BASE_URL)
        .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
        .build().create(ApiClient::class.java)

    private fun buildHttpClient(): OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(buildLoggingInterceptor())
        .connectTimeout(120, TimeUnit.SECONDS)
        .readTimeout(120, TimeUnit.SECONDS)
        .writeTimeout(90, TimeUnit.SECONDS)
        .build()


    private fun buildLoggingInterceptor(): HttpLoggingInterceptor {
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY
        return logging
    }


    private const val BASE_URL = "https://5e510330f2c0d300147c034c.mockapi.io/"

    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build() //Doesn't require the adapter
    }

    val apiService: ApiClient = getRetrofit().create(ApiClient::class.java)
}
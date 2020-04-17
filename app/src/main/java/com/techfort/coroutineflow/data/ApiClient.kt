package com.example.expert.util

import com.techfort.coroutineflow.data.NetworkContent
import com.techfort.coroutineflow.data.model.User
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiClient {
    @GET("/azizcse/9c162fd13e7a2fbae8b8cfcb726b5bf4/raw/e61bad84588613bed8b2a8849d57ce94e88ec0b1")
    suspend fun getContent(): Response<NetworkContent>

    @GET("/users")
    suspend fun getUsers(): Response<List<User>>
}
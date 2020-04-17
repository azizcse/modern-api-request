package com.techfort.coroutineflow.data

import com.example.expert.util.ApiService
import com.techfort.coroutineflow.data.model.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import okhttp3.ResponseBody
import retrofit2.Response

object DataProvider {

    ///azizcse/9c162fd13e7a2fbae8b8cfcb726b5bf4/raw/e61bad84588613bed8b2a8849d57ce94e88ec0b1"
    fun getContent(): Flow<Response<NetworkContent>> {
        return flow {
            val apiService = ApiService.getChannelClient()
            val contentResponse = apiService.getContent()
            emit(contentResponse)
        }.flowOn(Dispatchers.IO)
    }


    fun getContentResponse(): Flow<Response<NetworkContent>> {
        return flow {
            val apiService = ApiService.getChannelClient()
            val contentResponse = apiService.getContent()
            emit(contentResponse)
        }.flowOn(Dispatchers.IO)
    }


    val userApi by lazy { ApiService.apiService }
    suspend fun getUsers() = userApi.getUsers()
}
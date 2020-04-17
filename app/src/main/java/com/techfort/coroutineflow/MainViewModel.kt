package com.techfort.coroutineflow

import android.util.Log
import androidx.lifecycle.*
import com.techfort.coroutineflow.data.DataProvider
import com.techfort.coroutineflow.data.NetworkContent
import com.techfort.coroutineflow.util.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import okhttp3.ResponseBody
import retrofit2.Response

class MainViewModel : ViewModel() {
    private val _content = MutableLiveData<Response<NetworkContent>>()
    val content: LiveData<Response<NetworkContent>> get() = _content

    /**
     * Approach 1
     */
    fun getContent() {
        viewModelScope.launch {
            DataProvider.getContent()
                .onStart {
                    // TODO when start work show progress
                    Log.e("MainActivityLog", "start search")
                }.catch { exception ->
                    // TODO if error occurred show message
                    Log.e("MainActivityLog", "start error ${exception.message} ")

                }.onCompletion {
                    // TODO after complete task what you want to do
                    Log.e("MainActivityLog", "start complete")
                }.collect {
                    Log.e("MainActivityLog", "start data")
                    _content.value = it
                }
        }
    }

    /**
     * Approach 2
     */
    val contentLiveData: LiveData<Response<NetworkContent>> = liveData {
        DataProvider.getContent()
            .onStart {

            }.catch { exception ->
                Log.e("MainActivityLog", "start error ${exception.message} ")
            }.collect {
                emit(it)
            }

    }

    /**
     * Approach 3
     */
    val contentLiveData2: LiveData<Response<NetworkContent>> =
        DataProvider.getContent()
            .onStart {

            }.catch { exception ->
                Log.e("MainActivityLog", "start error ${exception.message} ")
            }.asLiveData()


    val contentResponse = MutableLiveData<Resource<Response<NetworkContent>>>()

    fun getNetworkContentResponse() {
        viewModelScope.launch {
            DataProvider.getContentResponse()
                .onStart {
                    contentResponse.value = Resource.loading(data = null)
                }.catch { error ->
                    contentResponse.value =
                        Resource.error(data = null, message = error.message ?: "Exception occurred")
                }.collect { resource ->
                    contentResponse.value = Resource.success(data = resource)
                }
        }
    }


    /**
     * Approach 4
     */
    fun getUsers() = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = DataProvider.getUsers()))
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }
}



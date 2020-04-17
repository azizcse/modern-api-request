package com.techfort.coroutineflow

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.techfort.coroutineflow.data.NetworkContent
import com.techfort.coroutineflow.data.model.User
import com.techfort.coroutineflow.util.Status
import kotlinx.coroutines.InternalCoroutinesApi
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    val TAG = "MainActivityLog"
    val viewModel by lazy { ViewModelProvider(this).get(MainViewModel::class.java) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        subscribeViewMode()
        loadData()
    }

    private fun subscribeViewMode() {
        viewModel.content.observe(this, Observer {
            runOnUiThread {
                val content = it.body()
                Log.e(TAG, "Content - 1 : ${content?.content?.size}")
            }

        })

        viewModel.contentLiveData.observe(this, Observer {
            runOnUiThread {
                val content = it.body()
                Log.e(TAG, "Content - 2 : ${content?.content?.size}")
            }

        })

        viewModel.contentLiveData2.observe(this, Observer {
            runOnUiThread({
                val content = it.body()
                Log.e(TAG, "Content - 3 : ${content?.content?.size}")
            })
        })


        viewModel.getUsers().observe(this, Observer {
            it?.let { resource ->
                when (resource.status) {
                    Status.LOADING -> {
                        Log.e(TAG, "User loading..................")
                    }
                    Status.SUCCESS -> {
                        val response = resource.data as Response<List<User>>
                        if (response!!.isSuccessful) {
                            Log.e(TAG, "User list size ${response?.body()?.size}")
                        } else if (response!!.code() == 401) {

                        } else {

                        }

                    }
                    Status.ERROR -> {
                        Log.e(TAG, "User loading error...............")
                    }
                }
            }
        })

        viewModel.contentResponse.observe(this, Observer {
            it?.let { resource ->
                when (resource.status) {
                    Status.LOADING -> Log.e(TAG, "Show progress")
                    Status.SUCCESS -> {
                        val response = resource.data as Response<NetworkContent>
                        if (response.isSuccessful) {
                            Log.e(TAG, "Network content size  ${response?.body()?.content?.size}")
                        } else if (response.code() == 401) {

                        } else {

                        }
                    }
                    Status.ERROR -> Log.e(TAG, resource.message)
                }
            }
        })
    }

    fun loadData() {
        viewModel.getContent()
        viewModel.getNetworkContentResponse()
    }
}

package com.techfort.coroutineflow.data

import com.google.gson.annotations.SerializedName

data class NetworkContent(@SerializedName("data")val content : List<Content>)
package com.techfort.coroutineflow.data

import com.google.gson.annotations.SerializedName

data class Content(
    @SerializedName("title")
    val name: String,
    @SerializedName("id")
    val channelId: String,
    @SerializedName("type")
    val type : Int
)
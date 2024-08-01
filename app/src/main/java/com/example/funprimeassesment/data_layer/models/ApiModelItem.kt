package com.example.funprimeassesment.data_layer.models

import java.io.Serializable

data class ApiModelItem(
    val albumId: Int,
    val id: Int,
    val thumbnailUrl: String,
    val title: String,
    val url: String
):Serializable
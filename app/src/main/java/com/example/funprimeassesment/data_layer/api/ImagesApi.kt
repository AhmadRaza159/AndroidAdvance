package com.example.funprimeassesment.data_layer.api

import androidx.annotation.Keep
import com.example.funprimeassesment.data_layer.models.ApiModelItem
import retrofit2.http.GET

@Keep
interface ImagesApi {
    @GET("photos")
    suspend fun getData(): ArrayList<ApiModelItem>
}
package com.example.funprimeassesment.data_layer.repositories

import com.example.funprimeassesment.data_layer.api.ImagesApi
import com.example.funprimeassesment.data_layer.models.ApiModelItem
import com.example.funprimeassesment.utils.Resource
import javax.inject.Inject

class ImagesRepository@Inject constructor(
    private val imagesApi: ImagesApi) {
    suspend fun getImages(): Resource<ArrayList<ApiModelItem>> {
        return try {
            Resource.Success(imagesApi.getData())
        } catch (exc: Exception) {
            Resource.Error(exc.message ?: exc.toString())
        }
    }
}
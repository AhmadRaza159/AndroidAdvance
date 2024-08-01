package com.example.funprimeassesment.ui.fragments.recycler_screen

import androidx.lifecycle.ViewModel
import com.example.funprimeassesment.data_layer.repositories.ImagesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RecyclerViewModel @Inject constructor(private val repo:ImagesRepository):ViewModel() {
    suspend fun getImages()=repo.getImages()
}
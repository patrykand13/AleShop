package com.momlok.aleshop.categories

import androidx.lifecycle.ViewModel
import com.momlok.aleshop.repository.FirebaseRepository

class HomeViewModel: ViewModel() {

    private val repository = FirebaseRepository()

    val categories = repository.getHomeData()
}
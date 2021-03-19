package com.momlok.aleshop.categories

import androidx.lifecycle.ViewModel
import com.momlok.aleshop.repository.FirebaseRepository

class ItemViewModel: ViewModel() {

    val repository = FirebaseRepository()

    fun addItemsToCart(id: String, number: Int){
        repository.addItemsToCart(id, number)
    }
}
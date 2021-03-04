package com.momlok.aleshop.categories

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.momlok.aleshop.data.Items
import com.momlok.aleshop.repository.FirebaseRepository

class ShearViewModel: ViewModel() {

    private val repository = FirebaseRepository()

    //val items = repository.getItemsData()
    fun getItemsData(categories: String): LiveData<List<Items>> {
        val items = repository.getItemsData(categories)
        return items
    }

    fun addItemsToCart(items: Items){
        repository.addItemsToCart(items)
    }
}
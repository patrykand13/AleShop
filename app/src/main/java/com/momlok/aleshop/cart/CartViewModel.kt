package com.momlok.aleshop.cart

import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import com.momlok.aleshop.repository.FirebaseRepository

class CartViewModel: ViewModel() {
    private val repository = FirebaseRepository()

    val user = repository.getUserData()

    val cart = user.switchMap {
        repository.getItemsCart(it.cart)
    }
}
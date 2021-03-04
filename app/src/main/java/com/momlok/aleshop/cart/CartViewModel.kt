package com.momlok.aleshop.cart

import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import com.momlok.aleshop.data.Items
import com.momlok.aleshop.data.Order
import com.momlok.aleshop.repository.FirebaseRepository

class CartViewModel: ViewModel() {
    private val repository = FirebaseRepository()

    val user = repository.getUserData()

    val cart = user.switchMap {
        repository.getItemsCart(it.cart)
    }

    fun removeCartItem(items: Items){
        repository.removeItemsFromCart(items)
    }
    fun removeCart(){
        repository.removeCart()
    }
    fun createOrder(order: Order){
        repository.createNewOrder(order)
    }
    fun updateOrder(items: Items, order: Order){
        repository.updateOrder(items, order)
    }
}
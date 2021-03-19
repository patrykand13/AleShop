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
        repository.getItemsCart(it.cart!!.keys.toList())
    }
    fun removeCartItem(items: String){
        repository.removeItemsFromCart(items)
    }
    fun createOrder(order: Order){
        repository.createNewOrder(order)
    }
    fun updateOrder(items: Items, number: Int, order: Order){
        repository.updateOrder(items, number, order)
    }
    fun updateCart(id: String, updateNumber: Int){
        repository.updateCart(id,updateNumber)
    }
}
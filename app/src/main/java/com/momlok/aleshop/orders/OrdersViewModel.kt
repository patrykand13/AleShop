package com.momlok.aleshop.orders

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import com.momlok.aleshop.data.Order
import com.momlok.aleshop.data.User
import com.momlok.aleshop.repository.FirebaseRepository

class OrdersViewModel: ViewModel() {

    private val repository = FirebaseRepository()

    val user = repository.getUserData()
    val orders = user.switchMap {
        repository.getOrdersData(it.uid)
    }

}
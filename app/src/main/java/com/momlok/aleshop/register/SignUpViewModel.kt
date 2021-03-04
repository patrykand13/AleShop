package com.momlok.aleshop.register

import androidx.lifecycle.ViewModel
import com.momlok.aleshop.data.User
import com.momlok.aleshop.repository.FirebaseRepository

class SignUpViewModel: ViewModel() {
    private val repository = FirebaseRepository()

    fun createNewUs(user: User){
        repository.createNewUser(user)
    }
}
package com.momlok.aleshop.data

data class User(
    val uid:String? = null,
    val email:String? = null,
    val name:String? = null,
    val cart: List<String>? = null
)
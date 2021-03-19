package com.momlok.aleshop.data

data class User(
        val uid:String? = null,
        val email:String? = null,
        val name:String? = null,
        val surname:String? = null,
        val cart: Map<String, Any>? = null
)
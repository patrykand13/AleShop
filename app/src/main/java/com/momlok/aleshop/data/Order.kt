package com.momlok.aleshop.data

data class Order (
        val id:String? = null,
        val status:String? = null,
        val userID:String? = null,
        val cart: List<String>? = null
        )
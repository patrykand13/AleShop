package com.momlok.aleshop.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.momlok.aleshop.data.Items
import com.momlok.aleshop.data.Order
import com.momlok.aleshop.data.User

class FirebaseRepository {

    private val storage = FirebaseStorage.getInstance()
    private val auth = FirebaseAuth.getInstance()
    private val cloud = FirebaseFirestore.getInstance()


    fun createNewUser(user: User){
        cloud.collection("users")
                .document(user.uid!!)
                .set(user)
    }
    fun createNewOrder(order: Order){
        cloud.collection("orders")
                .document(order.id!!)
                .set(order)
    }
    fun getUserData(): LiveData<User>{
        val cloudResult = MutableLiveData<User>()
        val uid = auth.currentUser?.uid

        cloud.collection("users")
            .document(uid!!)
            .get()
            .addOnSuccessListener {
                val user = it.toObject(User::class.java)
                cloudResult.postValue(user)
            }
            .addOnFailureListener {
                Log.d("error","get user faild")
            }
        return cloudResult
    }
    fun getItemsData(categories: String): LiveData<List<Items>>{
        val cloudResult = MutableLiveData<List<Items>>()

        cloud.collection("items")
                .whereEqualTo("categories", categories)
            .get()
            .addOnSuccessListener {
                val items = it.toObjects(Items::class.java)
                cloudResult.postValue(items)
            }
            .addOnFailureListener {
                Log.d("error","get items faild")
            }
        return cloudResult
    }
    fun getOrdersData(user: String?): LiveData<List<Order>>{
        val cloudResult = MutableLiveData<List<Order>>()

        cloud.collection("orders")
                .whereEqualTo("userID",user)
                .get()
                .addOnSuccessListener {
                    val order = it.toObjects(Order::class.java)
                    cloudResult.postValue(order)
                }
                .addOnFailureListener {
                    Log.d("error","get items faild")
                }
        return cloudResult
    }
    fun addItemsToCart(items: Items){
        cloud.collection("users")
                .document(auth.currentUser?.uid!!)
                .update("cart",FieldValue.arrayUnion(items.id))
                .addOnSuccessListener {
                    Log.d("repository","add to cart")
                }
                .addOnFailureListener {
                    Log.d("repository fail","faild add to cart")
                }
    }
    fun getItemsCart(list: List<String>?): LiveData<List<Items>>{
        val cloudResult = MutableLiveData<List<Items>>()
        if (!list.isNullOrEmpty()){
            cloud.collection("items")
                    .whereIn("id",list)
                    .get()
                    .addOnSuccessListener {
                        val resultList = it.toObjects(Items::class.java)
                        cloudResult.postValue(resultList)
                    }
                    .addOnFailureListener {
                        Log.d("repository fail","faild get to cart")
                    }
        }
        return cloudResult

    }
    fun removeItemsFromCart(items: Items){
        cloud.collection("users")
                .document(auth.currentUser?.uid!!)
                .update("cart",FieldValue.arrayRemove(items.id))
                .addOnSuccessListener {
                    Log.d("repository","remove item from cart")
                }
                .addOnFailureListener {
                    Log.d("repository fail","faild remove item from cart")
                }
    }
    fun updateOrder(items: Items, order: Order){
        cloud.collection("orders")
                .document(order.id!!)
                .update("cart",FieldValue.arrayUnion(items.id))
                .addOnSuccessListener {
                    Log.d("repository","update order")
                }
                .addOnFailureListener {
                    Log.d("repository fail","faild update order")
                }
    }
    fun removeCart(){
        cloud.collection("users")
                .document(auth.currentUser?.uid!!)
                .update("cart",FieldValue.delete())
                .addOnSuccessListener {
                    Log.d("repository","remove cart")
                }
                .addOnFailureListener {
                    Log.d("repository fail","faild remove cart")
                }
    }
}
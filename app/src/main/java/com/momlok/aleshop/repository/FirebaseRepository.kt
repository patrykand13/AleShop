package com.momlok.aleshop.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.momlok.aleshop.data.Categories
import com.momlok.aleshop.data.Items
import com.momlok.aleshop.data.Order
import com.momlok.aleshop.data.User

class FirebaseRepository {

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
                Log.d("repository failed","get user failed")
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
                Log.d("repository failed","get items failed")
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
                    Log.d("repository failed","get orders failed")
                }
        return cloudResult
    }
    fun getHomeData(): MutableLiveData<List<Categories>> {
        val cloudResult = MutableLiveData<List<Categories>>()

        cloud.collection("categories")
                .get()
                .addOnSuccessListener {
                    val categories = it.toObjects(Categories::class.java)
                    cloudResult.postValue(categories)
                }
                .addOnFailureListener {
                    Log.d("repository failed","get categories data failed")
                }
        return cloudResult
    }
    fun getItemsCart(list: List<String>?): LiveData<List<Items>>{
        val cloudResult = MutableLiveData<List<Items>>()
        try {
            if (!list.isNullOrEmpty()){
                cloud.collection("items")
                        .whereIn("id",list)
                        .get()
                        .addOnSuccessListener {
                            val resultList = it.toObjects(Items::class.java)
                            cloudResult.postValue(resultList)
                        }
                        .addOnFailureListener {
                            Log.d("repository failed","failed get item to cart")
                        }
            }
        }catch (e:Exception){}

        return cloudResult

    }

    fun addItemsToCart(id: String, number: Int){
        val updates = hashMapOf<String, Any>(
                "cart.$id" to number
        )
        cloud.collection("users")
                .document(auth.currentUser?.uid!!)
                .update(updates)
                .addOnSuccessListener {
                    Log.d("repository","add item to cart")
                }
                .addOnFailureListener {
                    Log.d("repository failed","failed item add to cart")
                }
    }
    fun updateOrder(items: Items, number: Int, order: Order){
        var id = items.id
        val updates = hashMapOf<String, Any>(
                "cart.$id" to number
        )
        cloud.collection("orders")
                .document(order.id!!)
                .update(updates)
                .addOnSuccessListener {
                    Log.d("repository","update order")
                }
                .addOnFailureListener {
                    Log.d("repository failed","failed update order")
                }
    }
    fun updateCart(id: String,number:Int){
        val updates = hashMapOf<String, Any>(
                "cart.$id" to number
        )
        cloud.collection("users")
                .document(auth.currentUser?.uid!!)
                .update(updates)
                .addOnSuccessListener {
                    Log.d("repository","update cart")
                }
                .addOnFailureListener {
                    Log.d("repository failed","failed update cart")
                }
    }
    fun removeItemsFromCart(id: String){
        val updates = hashMapOf<String, Any>(
                "cart.$id" to FieldValue.delete()
        )
        cloud.collection("users")
                .document(auth.currentUser?.uid!!)
                .update(updates)
                .addOnSuccessListener {
                    Log.d("repository","remove item from cart")
                }
                .addOnFailureListener {
                    Log.d("repository failed","failed remove item from cart")
                }
    }
}
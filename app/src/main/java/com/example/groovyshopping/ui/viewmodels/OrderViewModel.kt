package com.example.groovyshopping.ui.viewmodels

import androidx.lifecycle.viewModelScope
import com.example.groovyshopping.base.BaseViewModel
import com.example.groovyshopping.data.order.Order
import com.example.groovyshopping.user.data.remote.networkHandling.Resource
import com.example.groovyshopping.user.data.reporsitory.MainRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.homecookapp.user.utils.AppManger
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class OrderViewModel(
    override var mainRepository: MainRepository,
    override val appManger: AppManger,
    private val firestore: FirebaseFirestore,
    private val auth: FirebaseAuth
) : BaseViewModel(mainRepository, appManger) {

    private val _order = MutableStateFlow<Resource<Order>>(Resource.unspecified())
    val order = _order.asStateFlow()

    // ✅ Add this for all orders
    private val _allOrders = MutableStateFlow<Resource<List<Order>>>(Resource.unspecified())
    val allOrders = _allOrders.asStateFlow()

    fun placeOrder(order: Order) {
        viewModelScope.launch {
            _order.emit(Resource.loading())

            try {
                val cartItems = firestore.collection("user")
                    .document(auth.uid!!)
                    .collection("cart")
                    .get()
                    .await()

                firestore.runBatch { batch ->
                    val userOrderRef = firestore.collection("user")
                        .document(auth.uid!!)
                        .collection("orders")
                        .document()

                    val globalOrderRef = firestore.collection("orders").document()

                    batch.set(userOrderRef, order)
                    batch.set(globalOrderRef, order)

                    cartItems.documents.forEach {
                        batch.delete(it.reference)
                    }

                }.addOnSuccessListener {
                    viewModelScope.launch {
                        _order.emit(Resource.success(order))
                    }
                }.addOnFailureListener {
                    viewModelScope.launch {
                        _order.emit(Resource.error(it.message.toString()))
                    }
                }

            } catch (e: Exception) {
                _order.emit(Resource.error(e.message.toString()))
            }
        }
    }

    // ✅ Get all orders for current user
    fun getAllOrders() {
        viewModelScope.launch {
            _allOrders.emit(Resource.loading())

            try {
                firestore.collection("user")
                    .document(auth.uid!!)
                    .collection("orders")
                    .addSnapshotListener { value, error ->
                        if (error != null) {
                            viewModelScope.launch {
                                _allOrders.emit(Resource.error(error.message.toString()))
                            }
                            return@addSnapshotListener
                        }

                        val ordersList = value?.toObjects(Order::class.java)
                        viewModelScope.launch {
                            _allOrders.emit(Resource.success(ordersList ?: emptyList()))
                        }
                    }
            } catch (e: Exception) {
                _allOrders.emit(Resource.error(e.message.toString()))
            }
        }
    }
}
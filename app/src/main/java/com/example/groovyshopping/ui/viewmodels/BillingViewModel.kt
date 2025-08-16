package com.example.groovyshopping.ui.viewmodels

import androidx.lifecycle.viewModelScope
import com.example.groovyshopping.base.BaseViewModel
import com.example.groovyshopping.data.Address
import com.example.groovyshopping.data.order.Order
import com.example.groovyshopping.user.data.remote.networkHandling.Resource
import com.example.groovyshopping.user.data.reporsitory.MainRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.homecookapp.user.utils.AppManger
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class BillingViewModel(
    override var mainRepository: MainRepository,
    override val appManger: AppManger,
    private val firestore: FirebaseFirestore,
    private val auth: FirebaseAuth
) : BaseViewModel(mainRepository, appManger) {

    private val _address = MutableStateFlow<Resource<List<Address>>>(Resource.unspecified())
    val address = _address.asStateFlow()

    private val _order = MutableStateFlow<Resource<Order>>(Resource.unspecified())
    val order = _order.asStateFlow()

    init {
        getUserAddresses()
    }

    fun getUserAddresses() {
        viewModelScope.launch { _address.emit(Resource.loading()) }
        firestore.collection("user").document(auth.uid!!).collection("address")
            .addSnapshotListener { value, error ->
                if (error != null) {
                    viewModelScope.launch {
                        _address.emit(Resource.error(error.message.toString()))
                    }
                    return@addSnapshotListener
                }

                val addresses = value?.toObjects(Address::class.java)
                viewModelScope.launch {
                    _address.emit(Resource.success(addresses ?: emptyList()))
                }
            }
    }

    fun placeOrder(order: Order) {
        viewModelScope.launch {
            _order.emit(Resource.loading())
            try {
                val cartItems = firestore.collection("user")
                    .document(auth.uid!!)
                    .collection("cart")
                    .get()
                    .await()

                val orderId = System.currentTimeMillis()

                // هنا بتعرف الـ orderMap
                val orderMap = hashMapOf(
                    "orderId" to orderId,
                    "orderStatus" to order.orderStatus,
                    "date" to FieldValue.serverTimestamp(), // Timestamp من السيرفر
                    "userId" to auth.uid,
                    "totalPrice" to order.totalPrice,
                    "products" to order.products,
                    "address" to order.address
                )

                // هنا بقى تقدر تستخدمه
                firestore.runBatch { batch ->
                    val userOrderRef = firestore.collection("user")
                        .document(auth.uid!!)
                        .collection("orders")
                        .document(orderId.toString())

                    val globalOrderRef = firestore.collection("orders")
                        .document(orderId.toString())

                    batch.set(userOrderRef, orderMap)
                    batch.set(globalOrderRef, orderMap)

                    cartItems.documents.forEach {
                        batch.delete(it.reference)
                    }
                }.await()

                _order.emit(Resource.success(order))
            } catch (e: Exception) {
                _order.emit(Resource.error(e.message.toString()))
            }
        }
    }

    fun getCurrentDate(): String {
        val sdf = java.text.SimpleDateFormat("yyyy-MM-dd", java.util.Locale.getDefault())
        return sdf.format(java.util.Date())
    }

}
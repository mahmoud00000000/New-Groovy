package com.example.groovyshopping.ui.viewmodels

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.example.groovyshopping.base.BaseViewModel
import com.example.groovyshopping.data.User
import com.example.groovyshopping.data.order.Order
import com.example.groovyshopping.user.data.remote.networkHandling.Resource
import com.example.groovyshopping.user.data.reporsitory.MainRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.homecookapp.user.utils.AppManger
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import com.google.firebase.firestore.Query

class ProfileViewModel(
    override var mainRepository: MainRepository,
    override val appManger: AppManger,
    private val firestore: FirebaseFirestore,
    private val auth: FirebaseAuth
) : BaseViewModel(mainRepository, appManger) {

    private val _user = MutableStateFlow<Resource<User>>(Resource.unspecified())
    val user = _user.asStateFlow()

    private val _logoutSuccess = MutableStateFlow<Boolean?>(null)
    val logoutSuccess = _logoutSuccess.asStateFlow()

    init {
        getUser()
    }

    fun getUser() {
        viewModelScope.launch {
            Log.d("UserAccountViewModel", "Start getUser()")
            _user.emit(Resource.loading())
        }

        firestore.collection("user").document(auth.uid!!).get()
            .addOnSuccessListener {
                Log.d("UserAccountViewModel", "DocumentSnapshot: ${it.exists()}")
                if (it.exists()) {
                    val user = it.toObject(User::class.java)
                    Log.d("UserAccountViewModel", "User data: $user")
                    user?.let {
                        viewModelScope.launch {
                            _user.emit(Resource.success(it))
                            Log.d("UserAccountViewModel", "Emitted success")
                        }
                    }
                } else {
                    Log.d("UserAccountViewModel", "Document does not exist")
                    viewModelScope.launch {
                        _user.emit(Resource.error("User not found"))
                    }
                }
            }.addOnFailureListener {
                Log.d("UserAccountViewModel", "Failed to get user: ${it.message}")
                viewModelScope.launch {
                    _user.emit(Resource.error(it.message.toString()))
                }
            }
    }

    fun logout() {
        auth.signOut()
        viewModelScope.launch {
            _logoutSuccess.emit(true)
        }
    }

    fun getLastOrder(callback: (Order?) -> Unit) {
        val currentUserId = FirebaseAuth.getInstance().currentUser?.uid ?: return callback(null)

        FirebaseFirestore.getInstance()
            .collection("user")
            .document(currentUserId)
            .collection("orders")
            .orderBy("date", Query.Direction.DESCENDING)
            .limit(1)
            .get()
            .addOnSuccessListener { snapshot ->
                val order = snapshot.documents.firstOrNull()?.toObject(Order::class.java)
                callback(order)
            }
            .addOnFailureListener {
                callback(null)
            }
    }

    fun setLogoutSuccess(value: Boolean?) {
        viewModelScope.launch {
            _logoutSuccess.emit(value)
        }
    }
}
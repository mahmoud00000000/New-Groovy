package com.example.groovyshopping.ui

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.example.groovyshopping.user.data.models.Product
import com.example.groovyshopping.user.data.models.UserDataModel
import com.example.groovyshopping.user.data.remote.networkHandling.Resource
import com.homecookapp.user.utils.AppManger
import com.example.groovyshopping.base.BaseViewModel
import com.example.groovyshopping.user.data.reporsitory.MainRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class AuthViewModel constructor(
    override var mainRepository: MainRepository,
    override val appManger: AppManger
) : BaseViewModel(mainRepository, appManger) {

    val loginResponse = MutableLiveData<FirebaseUser?>()
    val logoutSuccess = MutableLiveData<Boolean>()
    val registrationResponse = MutableLiveData<UserDataModel?>()
    val errorMessage = MutableLiveData<String?>()
    val emailLiveData = MutableLiveData<String>()
    val passwordLiveData = MutableLiveData<String>()
    var firstName = MutableLiveData<String>()
    var viewEmailLiveData = MutableLiveData<String>()
    val lastName = MutableLiveData<String>()
    val email = MutableLiveData<String>()
    val password = MutableLiveData<String>()
    val _specialProduct = MutableStateFlow<Resource<List<Product>>>(
        Resource.success(emptyList())  // أو Resource.error("Initial state") لكن دي مش مناسبة هنا
    )

    val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()


    fun fetchProducts() {
        viewModelScope.launch {
            _specialProduct.value = Resource.loading() // ممكن تكتب null أو تسيبها فاضية

            try {
                val snapshot = firestore.collection("products").get().await()
                val products = snapshot.toObjects(Product::class.java)

                Log.d("FirestoreTest", "Products: $products")

                _specialProduct.value = Resource.success(products)
            } catch (e: Exception) {
                _specialProduct.value = Resource.error(e.message ?: "Unknown error")
            }
        }
    }


    fun loginUser() {
        val email = emailLiveData.value
        val password = passwordLiveData.value

        if (email.isNullOrBlank() || password.isNullOrBlank()) {
            errorMessage.value = "Missing Required Fields!"
        } else if (password.length < 6) {
            errorMessage.value = "Password must be at least 6 characters!"
        } else {
            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        if (auth.currentUser!!.isEmailVerified) {

                            loginResponse.value = auth.currentUser

                        } else {
                            errorMessage.value = "Check your email"
                        }
                    } else {
                        errorMessage.value = task.exception?.localizedMessage
                    }

                }
        }
    }

    fun logoutUser() {
        auth.signOut()
        // امسح بيانات الجلسة لو انت مخزن حاجة في appManger أو SharedPreferences
        appManger.logout() // لو عندك دالة كده مثلاً

        logoutSuccess.value = true
    }
}







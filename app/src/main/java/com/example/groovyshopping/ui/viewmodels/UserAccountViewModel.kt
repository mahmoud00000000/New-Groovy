package com.example.groovyshopping.ui.viewmodels

import android.content.ContentResolver
import android.graphics.Bitmap
import android.net.Uri
import android.provider.MediaStore
import android.util.Log
import androidx.lifecycle.viewModelScope
import com.example.groovyshopping.base.BaseViewModel
import com.example.groovyshopping.user.data.remote.networkHandling.Resource
import com.example.groovyshopping.user.data.reporsitory.MainRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.StorageReference
import com.homecookapp.user.utils.AppManger
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import com.example.groovyshopping.utils.validateEmail
import kotlinx.coroutines.tasks.await
import java.io.ByteArrayOutputStream
import java.util.UUID
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import com.example.groovyshopping.utils.validateEmail
import com.example.groovyshopping.utils.RegisterValidation
import com.example.groovyshopping.data.User

class UserAccountViewModel(
    override var mainRepository: MainRepository,
    override val appManger: AppManger,
    private val firestore: FirebaseFirestore,
    private val auth: FirebaseAuth,
    private val storage: StorageReference,
    private val contentResolver: ContentResolver
) : BaseViewModel(mainRepository, appManger) {

    private val _user = MutableStateFlow<Resource<User>>(Resource.unspecified())
    val user = _user.asStateFlow()

    private val _updateInfo = MutableStateFlow<Resource<User>>(Resource.unspecified())
    val updateInfo = _updateInfo.asStateFlow()

    init {
        getUser()
    }

    fun getUser() {
        viewModelScope.launch {
            _user.emit(Resource.loading())
        }

        firestore.collection("user").document(auth.uid!!).get()
            .addOnSuccessListener {
                Log.d("getUser", "Document: ${it.exists()} - Data: ${it.data}")
                val user = it.toObject(User::class.java)
                user?.let {
                    viewModelScope.launch {
                        _user.emit(Resource.success(it))
                    }
                }
            }.addOnFailureListener {
                viewModelScope.launch {
                    _user.emit(Resource.error(it.message.toString()))
                }
            }
    }

    fun updateUser(user: User, imageUri: Uri?) {
        val areInputsValid = validateEmail(user.email) is RegisterValidation.Success &&
                user.firstName.trim().isNotEmpty() &&
                user.lastName.trim().isNotEmpty()

        if (!areInputsValid) {
            viewModelScope.launch {
                _user.emit(Resource.error("Check your inputs"))
            }
            return
        }

        viewModelScope.launch {
            _updateInfo.emit(Resource.loading())
        }

        if (imageUri == null) {
            saveUserInformation(user, true)
        } else {
            saveUserInformationWithNewImage(user, imageUri)
        }
    }

    private fun saveUserInformationWithNewImage(user: User, imageUri: Uri) {
        viewModelScope.launch {
            try {
                val imageBitmap = MediaStore.Images.Media.getBitmap(contentResolver, imageUri)
                val byteArrayOutputStream = ByteArrayOutputStream()
                imageBitmap.compress(Bitmap.CompressFormat.JPEG, 96, byteArrayOutputStream)
                val imageByteArray = byteArrayOutputStream.toByteArray()
                val imageDirectory = storage.child("profileImages/${auth.uid}/${UUID.randomUUID()}")
                val result = imageDirectory.putBytes(imageByteArray).await()
                val imageUrl = result.storage.downloadUrl.await().toString()
                saveUserInformation(user.copy(imagePath = imageUrl), false)
            } catch (e: Exception) {
                viewModelScope.launch {
                    _user.emit(Resource.error(e.message.toString()))
                }
            }
        }
    }

    private fun saveUserInformation(user: User, shouldRetrievedOldImage: Boolean) {
        firestore.runTransaction { transaction ->
            val documentRef = firestore.collection("user").document(auth.uid!!)
            val snapshot = transaction.get(documentRef)
            val currentUser = if (snapshot.exists()) {
                snapshot.toObject(User::class.java)
            } else {
                null
            }
            val newUser = if (shouldRetrievedOldImage) {
                user.copy(imagePath = currentUser?.imagePath ?: "")
            } else {
                user
            }
            transaction.set(documentRef, newUser)
        }.addOnSuccessListener {
            viewModelScope.launch {
                _updateInfo.emit(Resource.success(user))
            }
        }.addOnFailureListener {
            viewModelScope.launch {
                _updateInfo.emit(Resource.error(it.message.toString()))
            }
        }
    }

    fun updatePassword(newPassword: String) {
        val currentUser = auth.currentUser
        currentUser?.updatePassword(newPassword)
            ?.addOnSuccessListener {
                viewModelScope.launch {
                    _updateInfo.emit(Resource.success(user.value.data ?: return@launch))
                }
            }
            ?.addOnFailureListener {
                viewModelScope.launch {
                    _updateInfo.emit(Resource.error(it.message.toString()))
                }
            }
    }
}
package com.example.groovyshopping.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.groovyshopping.base.BaseViewModel
import com.example.groovyshopping.data.CartProduct
import com.example.groovyshopping.data.FirebaseCommon
import com.example.groovyshopping.user.data.remote.networkHandling.Resource
import com.example.groovyshopping.user.data.reporsitory.MainRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.homecookapp.user.utils.AppManger
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


class DetailsViewModel  constructor(
    override var mainRepository: MainRepository,
    override val appManger: AppManger,
    private val firestore: FirebaseFirestore,
    private val auth: FirebaseAuth,
    private val firebaseCommon: FirebaseCommon
) : BaseViewModel(mainRepository, appManger) {

    private val _addToCart = MutableStateFlow<Resource<CartProduct>>(Resource.unspecified())
    val addToCart = _addToCart.asStateFlow()

    fun addUpdateProductInCart(cartProduct: CartProduct) {
        val userId = auth.uid ?: return

        viewModelScope.launch {
            _addToCart.emit(Resource.loading())
        }

        firestore.collection("user").document(userId).collection("cart")
            .whereEqualTo("product.id", cartProduct.product.id)
            .get()
            .addOnSuccessListener { documents ->

                val matchedDocument = documents.firstOrNull { doc ->
                    val product = doc.toObject(CartProduct::class.java)
                    product.product.id == cartProduct.product.id &&
                            product.selectedColor == cartProduct.selectedColor &&
                            product.selectedSize == cartProduct.selectedSize
                }

                if (matchedDocument == null) {
                    addNewProduct(cartProduct)
                } else {
                    val documentId = matchedDocument.id
                    increaseQuantity(documentId, cartProduct)
                }
            }
            .addOnFailureListener {
                viewModelScope.launch {
                    _addToCart.emit(Resource.error(it.message.toString()))
                }
            }
    }

    private fun addNewProduct(cartProduct: CartProduct) {
        firebaseCommon.addProductToCart(cartProduct) { addedProduct, e ->
            viewModelScope.launch {
                if (e == null)
                    _addToCart.emit(Resource.success(addedProduct!!))
                else
                    _addToCart.emit(Resource.error(e.message.toString()))
            }
        }
    }

    private fun increaseQuantity(documentId: String, cartProduct: CartProduct) {
        firebaseCommon.increaseQuantity(documentId) { _, e ->
            viewModelScope.launch {
                if (e == null)
                    _addToCart.emit(Resource.success(cartProduct))
                else
                    _addToCart.emit(Resource.error(e.message.toString()))
            }
        }
    }
}
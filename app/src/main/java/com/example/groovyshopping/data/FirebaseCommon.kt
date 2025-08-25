package com.example.groovyshopping.data

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore

class FirebaseCommon(
    private val firestore: FirebaseFirestore,
    private val auth: FirebaseAuth
) {

    private fun getCartCollection(): CollectionReference {
        val uid = auth.uid ?: throw NullPointerException("User is not logged in")
        return firestore.collection("user").document(uid).collection("cart")
    }

    fun addProductToCart(cartProduct: CartProduct, onResult: (CartProduct?, Exception?) -> Unit) {
        val productId = cartProduct.product.id
        val color = cartProduct.selectedColor ?: "noColor"
        val size = cartProduct.selectedSize ?: "noSize"

        val documentId = "${productId}_${color}_${size}"

        getCartCollection().document(documentId).get().addOnSuccessListener { snapshot ->
            if (snapshot.exists()) {
                // لو المنتج بنفس المواصفات موجود، نزود الكمية
                val existingProduct = snapshot.toObject(CartProduct::class.java)
                existingProduct?.let {
                    val newQuantity = it.quantity + cartProduct.quantity
                    val updatedProduct = it.copy(quantity = newQuantity)
                    getCartCollection().document(documentId).set(updatedProduct)
                        .addOnSuccessListener { onResult(updatedProduct, null) }
                        .addOnFailureListener { e -> onResult(null, e) }
                }
            } else {
                // لو مش موجود، نضيفه جديد
                getCartCollection().document(documentId).set(cartProduct)
                    .addOnSuccessListener { onResult(cartProduct, null) }
                    .addOnFailureListener { e -> onResult(null, e) }
            }
        }.addOnFailureListener { e ->
            onResult(null, e)
        }
    }

    fun increaseQuantity(documentId: String, onResult: (String?, Exception?) -> Unit) {
        firestore.runTransaction { transition ->
            val documentRef = getCartCollection().document(documentId)
            val document = transition.get(documentRef)
            val productObject = document.toObject(CartProduct::class.java)
            productObject?.let { cartProduct ->
                val newQuantity = cartProduct.quantity + 1
                val newProductObject = cartProduct.copy(quantity = newQuantity)
                transition.set(documentRef, newProductObject)
            }
        }.addOnSuccessListener {
            onResult(documentId, null)
        }.addOnFailureListener {
            onResult(null, it)
        }
    }

    fun decreaseQuantity(documentId: String, onResult: (String?, Exception?) -> Unit) {
        firestore.runTransaction { transition ->
            val documentRef = getCartCollection().document(documentId)
            val document = transition.get(documentRef)
            val productObject = document.toObject(CartProduct::class.java)
            productObject?.let { cartProduct ->
                val newQuantity = cartProduct.quantity - 1
                val newProductObject = cartProduct.copy(quantity = newQuantity)
                transition.set(documentRef, newProductObject)
            }
        }.addOnSuccessListener {
            onResult(documentId, null)
        }.addOnFailureListener {
            onResult(null, it)
        }
    }

    enum class QuantityChanging {
        INCREASE,DECREASE
    }


}
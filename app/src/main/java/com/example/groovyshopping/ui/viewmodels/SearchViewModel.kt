package com.example.groovyshopping.ui.viewmodels

import androidx.lifecycle.viewModelScope
import com.example.groovyshopping.base.BaseViewModel
import com.example.groovyshopping.data.Product
import com.example.groovyshopping.user.data.reporsitory.MainRepository
import com.google.firebase.firestore.FirebaseFirestore
import com.homecookapp.user.utils.AppManger
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SearchViewModel(
    override var mainRepository: MainRepository,
    override val appManger: AppManger,
    private val firestore: FirebaseFirestore
) : BaseViewModel(mainRepository, appManger) {

    private val _productsInCategory = MutableStateFlow<List<Product>>(emptyList())
    val productsInCategory = _productsInCategory.asStateFlow()

    fun searchByCategory(categoryName: String) {
        val collections = listOf("Market", "cupboard", "table", "accessory", "furniture") // كل الكوليكشنات اللي عندك
        val allProducts = mutableListOf<Product>()

        viewModelScope.launch {
            try {
                collections.forEach { collection ->
                    firestore.collection(collection)
                        .whereEqualTo("category", categoryName)
                        .get()
                        .addOnSuccessListener { snapshot ->
                            val products = snapshot.toObjects(Product::class.java)
                            allProducts.addAll(products)

                            // لما تخلص آخر كوليكشن ابعت الليست
                            if (collection == collections.last()) {
                                viewModelScope.launch {
                                    _productsInCategory.emit(allProducts)
                                }
                            }
                        }
                }
            } catch (e: Exception) {
                // خطأ لو حصل
            }
        }
    }
}
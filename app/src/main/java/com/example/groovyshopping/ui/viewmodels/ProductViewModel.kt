package com.example.groovyshopping.ui.viewmodels

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.example.groovyshopping.base.BaseViewModel
import com.example.groovyshopping.data.Product
import com.example.groovyshopping.user.data.remote.networkHandling.Resource
import com.example.groovyshopping.user.data.reporsitory.MainRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.homecookapp.user.utils.AppManger
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class ProductViewModel constructor(
    override var mainRepository: MainRepository,
    override val appManger: AppManger
) : BaseViewModel(mainRepository, appManger) {

    val _specialProduct = MutableStateFlow<Resource<List<Product>>>(
        Resource.success(emptyList())  // أو Resource.error("Initial state") لكن دي مش مناسبة هنا
    )
    val _bestDealsProduct = MutableStateFlow<Resource<List<Product>>>(
        Resource.success(emptyList())  // أو Resource.error("Initial state") لكن دي مش مناسبة هنا
    )

    val _bestProduct = MutableStateFlow<Resource<List<Product>>>(
        Resource.success(emptyList())  // أو Resource.error("Initial state") لكن دي مش مناسبة هنا
    )

    private val _chairProducts = MutableStateFlow<Resource<List<Product>>>(
        Resource.success(emptyList()) // أو Resource.loading() حسب ما تحب تبدأ بيها
    )

    private val _offerProducts = MutableStateFlow<Resource<List<Product>>>(Resource.unspecified())
    val offerProducts = _offerProducts.asStateFlow()

    private val _bestProducts = MutableStateFlow<Resource<List<Product>>>(Resource.unspecified())
    val bestProducts = _bestProducts.asStateFlow()

    val chairProducts = _chairProducts.asStateFlow()

    private val _cupboardProducts = MutableStateFlow<Resource<List<Product>>>(Resource.loading())
    val cupboardProducts = _cupboardProducts.asStateFlow()

    private val _tableProducts = MutableStateFlow<Resource<List<Product>>>(Resource.loading())
    val tableProducts = _tableProducts.asStateFlow()

    private val _accessoryProducts = MutableStateFlow<Resource<List<Product>>>(Resource.unspecified())
    val accessoryProducts = _accessoryProducts.asStateFlow()


    val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()


    fun fetchProducts() {
        viewModelScope.launch {
            _specialProduct.value = Resource.loading()
            _bestDealsProduct.value = Resource.loading()
            _bestProduct.value = Resource.loading()

            try {
                val snapshot = firestore.collection("products").get().await()
                val products = snapshot.documents.map { document ->
                    val colorList = document.get("color") as? List<Long> ?: emptyList()
                    val sizeList = document.get("size") as? List<String> ?: emptyList()

                    Product(
                        id = document.getString("id") ?: "",
                        name = document.getString("name") ?: "",
                        category = document.getString("category") ?: "",
                        price = document.getDouble("price")?.toFloat() ?: 0f,
                        offerPercentage = document.getDouble("offerPercentage")?.toFloat(),
                        description = document.getString("description"),
                        colors = colorList.map { it.toInt() },
                        sizes = sizeList,
                        images = document.get("images") as? List<String> ?: emptyList()
                    )
                }

                Log.d("FirestoreTest", "Products: $products")

                _specialProduct.value = Resource.success(products)
                _bestDealsProduct.value = Resource.success(products)
                _bestProduct.value = Resource.success(products)

            } catch (e: Exception) {
                _specialProduct.value = Resource.error(e.message ?: "Unknown error")
                _bestDealsProduct.value = Resource.error(e.message ?: "Unknown error")
                _bestProduct.value = Resource.error(e.message ?: "Unknown error")
            }
        }
    }

    fun fetchCupboardProducts() {
        viewModelScope.launch {
            _cupboardProducts.value = Resource.loading()

            try {
                val snapshot = firestore.collection("cupboard").get().await()
                val products = snapshot.toObjects(Product::class.java)

                Log.d("FirestoreChair", "Fetched ${products.size} chairs")

                _cupboardProducts.value = Resource.success(products)
            } catch (e: Exception) {
                Log.e("FirestoreChair", "Error: ${e.message}")
                _cupboardProducts.value = Resource.error(e.message ?: "Unknown error")
            }
        }
    }

    fun fetchChairProducts() {
        viewModelScope.launch {
            _chairProducts.value = Resource.loading()

            try {
                val snapshot = firestore.collection("Market").get().await()
                val products = snapshot.toObjects(Product::class.java)

                Log.d("FirestoreChair", "Fetched ${products.size} chairs")

                _chairProducts.value = Resource.success(products)
            } catch (e: Exception) {
                Log.e("FirestoreChair", "Error: ${e.message}")
                _chairProducts.value = Resource.error(e.message ?: "Unknown error")
            }
        }
    }

    fun fetchTable() {
        viewModelScope.launch {
            _tableProducts.value = Resource.loading()
            try {
                val snapshot = firestore.collection("table").get().await()
                val products = snapshot.toObjects(Product::class.java)
                _tableProducts.value = Resource.success(products)
            } catch (e: Exception) {
                _tableProducts.value = Resource.error(e.message ?: "Unknown error")
            }
        }
    }

    fun fetchFurniture() {
        viewModelScope.launch {
            _bestProducts.value = Resource.loading()
            _offerProducts.value = Resource.loading()
            try {
                val snapshot = firestore.collection("furniture").get().await()
                val products = snapshot.toObjects(Product::class.java)
                _bestProducts.value = Resource.success(products)
                _offerProducts.value = Resource.success(products)
            } catch (e: Exception) {
                _bestProducts.value = Resource.error(e.message ?: "Unknown error")
                _offerProducts.value = Resource.error(e.message ?: "Unknown error")
            }
        }
    }

    fun fetchAccessory() {
        viewModelScope.launch {
            _bestProducts.value = Resource.loading()
            _offerProducts.value = Resource.loading()
            try {
                val snapshot = firestore.collection("accessory").get().await()
                val products = snapshot.toObjects(Product::class.java)
                _bestProducts.value = Resource.success(products)
                _offerProducts.value = Resource.success(products)
            } catch (e: Exception) {
                _bestProducts.value = Resource.error(e.message ?: "Unknown error")
                _offerProducts.value = Resource.error(e.message ?: "Unknown error")
            }
        }
    }



}
package com.example.groovyshopping.ui.categories

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.groovyshopping.R
import com.example.groovyshopping.databinding.FragmentBaseCategoryBinding
import com.example.groovyshopping.ui.AuthViewModel
import com.example.groovyshopping.ui.adapter.BestProductsAdapter
import com.example.groovyshopping.user.base.BaseFragment
import com.example.groovyshopping.user.data.remote.networkHandling.Resource
import kotlinx.coroutines.flow.collectLatest
import kotlin.reflect.KClass

//open class BaseCategoryFragment( val category: String) : BaseFragment<FragmentBaseCategoryBinding, AuthViewModel>(){
//    private lateinit var offerAdapter: BestProductsAdapter
//    private lateinit var bestProductsAdapter: BestProductsAdapter
//
//    override fun layoutResource(): Int = R.layout.fragment_base_category
//
//
//
//    override fun viewModelClass(): KClass<AuthViewModel> = AuthViewModel::class
//
//
//
//    override fun setUI(savedInstanceState: Bundle?) {
//        dataBinding.viewModel = viewModel
//    }
//
//    override fun observer() {
//
//        setupOfferRv()
//        setupBestProductsRv()
//
//        lifecycleScope.launchWhenStarted {
//            viewModel.bestProducts.collectLatest { result ->
//                when (result.status) {
//                    Resource.Status.LOADING -> {
//                        Log.d("ProductState", "Loading products...")
//                    }
//                    Resource.Status.SUCCESS -> {
//                        val allProducts = result.data ?: emptyList()
//
//                        // المنتجات اللي فيها خصم
//                        val offers = allProducts.filter { (it.offerPercentage ?: 0f) > 0 }
//
//                        // تقدر تسيب bestProducts = كل المنتجات أو تفلترها حسب اللي يعجبك
//                        val best = allProducts
//
//                        offerAdapter.differ.submitList(offers)
//                        bestProductsAdapter.differ.submitList(best)
//
//                        Log.d("ProductState", "عرض ${offers.size} منتجات خصومات و ${best.size} كأفضل منتجات")
//                    }
//                    Resource.Status.ERROR -> {
//                        Toast.makeText(requireContext(), result.message ?: "حصل خطأ", Toast.LENGTH_SHORT).show()
//                        Log.e("ProductState", "Error: ${result.message}")
//                    }
//                    Resource.Status.UNSPECIFIED -> {
//                        // عادي نسيبه فاضي
//                    }
//                }
//            }
//        }
//
//    }
//
//    private fun setupBestProductsRv() {
//        bestProductsAdapter = BestProductsAdapter()
//        dataBinding.rvBestProductsBase.apply {
//            layoutManager = GridLayoutManager(requireContext(), 2)
//            adapter = bestProductsAdapter
//        }
//    }
//
//    private fun setupOfferRv() {
//        offerAdapter = BestProductsAdapter()
//        dataBinding.rvOffer.apply {
//            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
//            adapter = offerAdapter
//        }
//    }
//
//    override fun clicks() {
//
//    }
//
//    override fun callApis() {
//        viewModel.fetchProductsByCategory(category)
//    }
//
//}

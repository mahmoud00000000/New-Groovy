package com.example.groovyshopping.ui.categories

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.groovyshopping.R
import com.example.groovyshopping.databinding.FragmentMainCategoryBinding
import com.example.groovyshopping.ui.AuthViewModel
import com.example.groovyshopping.ui.adapter.BestDealsAdapter
import com.example.groovyshopping.ui.adapter.BestProductsAdapter
import com.example.groovyshopping.ui.adapter.SpecialProductsAdapter
import com.example.groovyshopping.user.base.BaseFragment
import com.example.groovyshopping.user.data.remote.networkHandling.Resource
import kotlinx.coroutines.flow.collectLatest
import kotlin.reflect.KClass

class MainCategory : BaseFragment<FragmentMainCategoryBinding, AuthViewModel>(){
    private lateinit var specialProductsAdapter: SpecialProductsAdapter
    private lateinit var bestDealsProductsAdapter: BestDealsAdapter
    private lateinit var bestProductsAdapter: BestProductsAdapter
    override fun layoutResource(): Int = R.layout.fragment_main_category



    override fun viewModelClass(): KClass<AuthViewModel> = AuthViewModel::class



    override fun setUI(savedInstanceState: Bundle?) {
        dataBinding.viewModel = viewModel
    }

    override fun observer() {
        setupSpecialProductsRv()
        lifecycleScope.launchWhenStarted {
            viewModel._specialProduct.collectLatest {
                when (it.status) {
                    Resource.Status.LOADING -> {
                        showLoading()
                    }
                    Resource.Status.SUCCESS -> {
                        specialProductsAdapter.differ.submitList(it.data)
                        hideLoading()
                        Log.d("CheckData", "List size: ${it.data?.size}")
                        it.data?.forEachIndexed { index, product ->
                            Log.d("CheckData", "Item $index => ${product.name} | ${product.images}")
                        }
                    }
                    Resource.Status.ERROR -> {
                        hideLoading()
                        Toast.makeText(requireContext(), it.message ?: "حدث خطأ", Toast.LENGTH_SHORT).show()
                    }
                    Resource.Status.UNSPECIFIED -> {
                        hideLoading()
                        Log.w("Status", "UNSPECIFIED")
                    }
                }
            }
        }

        setupBestDealsProductsRv()
        lifecycleScope.launchWhenStarted {
            viewModel._bestDealsProduct.collectLatest {
                when (it.status) {
                    Resource.Status.LOADING -> {
                        showLoading()
                    }
                    Resource.Status.SUCCESS -> {
                        bestDealsProductsAdapter.differ.submitList(it.data)
                        hideLoading()
                        Log.d("CheckData", "List size: ${it.data?.size}")
                        it.data?.forEachIndexed { index, product ->
                            Log.d("CheckData", "Item $index => ${product.name} | ${product.images}")
                        }
                    }
                    Resource.Status.ERROR -> {
                        hideLoading()
                        Toast.makeText(requireContext(), it.message ?: "حدث خطأ", Toast.LENGTH_SHORT).show()
                    }
                    Resource.Status.UNSPECIFIED -> {
                        hideLoading()
                        Log.w("Status", "UNSPECIFIED")
                    }
                }
            }
        }

        setupBestProductsRv()
        lifecycleScope.launchWhenStarted {
            viewModel._bestProduct.collectLatest {
                when (it.status) {
                    Resource.Status.LOADING -> {
                        showLoading()
                    }
                    Resource.Status.SUCCESS -> {
                        bestProductsAdapter.differ.submitList(it.data)
                        hideLoading()
                        Log.d("CheckData", "List size: ${it.data?.size}")
                        it.data?.forEachIndexed { index, product ->
                            Log.d("CheckData", "Item $index => ${product.name} | ${product.images}")
                        }
                    }
                    Resource.Status.ERROR -> {
                        hideLoading()
                        Toast.makeText(requireContext(), it.message ?: "حدث خطأ", Toast.LENGTH_SHORT).show()
                    }
                    Resource.Status.UNSPECIFIED -> {
                        hideLoading()
                        Log.w("Status", "UNSPECIFIED")
                    }
                }
            }
        }

    }

    private fun hideLoading() {
        dataBinding.mainCategoryProgressbar.visibility = View.GONE
    }

    private fun showLoading() {
        dataBinding.mainCategoryProgressbar.visibility = View.VISIBLE
    }

    private fun setupSpecialProductsRv() {
        specialProductsAdapter = SpecialProductsAdapter()
        dataBinding.rvSpecialProducts.apply {
            layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL,false)
            adapter = specialProductsAdapter
        }
    }

    private fun setupBestDealsProductsRv() {
        bestDealsProductsAdapter = BestDealsAdapter()
        dataBinding.rvBestDealsProducts.apply {
            layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL,false)
            adapter = bestDealsProductsAdapter
        }
    }

    private fun setupBestProductsRv() {
        bestProductsAdapter = BestProductsAdapter()
        dataBinding.rvBestProducts.apply {
            layoutManager = GridLayoutManager(requireContext(), 2)
            adapter = bestProductsAdapter
        }
    }

    override fun clicks() {

    }

    override fun callApis() {
        viewModel.fetchProducts()
    }

}

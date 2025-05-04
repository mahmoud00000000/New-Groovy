package com.example.groovyshopping.ui.categories

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.groovyshopping.R
import com.example.groovyshopping.databinding.FragmentMainCategoryBinding
import com.example.groovyshopping.ui.AuthViewModel
import com.example.groovyshopping.ui.adapter.SpecialProductsAdapter
import com.example.groovyshopping.user.base.BaseFragment
import com.example.groovyshopping.user.data.remote.networkHandling.Resource
import kotlinx.coroutines.flow.collectLatest
import kotlin.reflect.KClass

class MainCategory : BaseFragment<FragmentMainCategoryBinding, AuthViewModel>(){
    private lateinit var specialProductsAdapter: SpecialProductsAdapter
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
                    }
                    Resource.Status.ERROR -> {
                        hideLoading()
                        Toast.makeText(requireContext(), it.message ?: "حدث خطأ", Toast.LENGTH_SHORT).show()
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

    override fun clicks() {

    }

    override fun callApis() {
        viewModel.fetchProducts()
    }

}

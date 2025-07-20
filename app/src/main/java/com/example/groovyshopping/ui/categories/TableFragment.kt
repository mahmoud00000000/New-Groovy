package com.example.groovyshopping.ui.categories

import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.groovyshopping.R
import com.example.groovyshopping.databinding.FragmentTableBinding
import com.example.groovyshopping.ui.AuthViewModel
import com.example.groovyshopping.ui.adapter.BestProductsAdapter
import com.example.groovyshopping.user.base.BaseFragment
import com.example.groovyshopping.user.data.remote.networkHandling.Resource
import kotlin.reflect.KClass

class TableFragment : BaseFragment<FragmentTableBinding, AuthViewModel>(){
    private lateinit var offerAdapter: BestProductsAdapter
    private lateinit var bestProductsAdapter: BestProductsAdapter
    override fun layoutResource(): Int = R.layout.fragment_table



    override fun viewModelClass(): KClass<AuthViewModel> = AuthViewModel::class



    override fun setUI(savedInstanceState: Bundle?) {
        dataBinding.viewModel = viewModel
    }

    override fun observer() {
        setupBestProductsRv()
        setupOfferRv()

        lifecycleScope.launchWhenStarted {
            viewModel.bestProducts.collect { resource ->
                when (resource.status) {
                    Resource.Status.SUCCESS -> {
                        resource.data?.let {
                            bestProductsAdapter.differ.submitList(it)
                        }
                    }
                    Resource.Status.ERROR -> {
                        Toast.makeText(requireContext(), resource.message ?: "Error", Toast.LENGTH_SHORT).show()
                    }
                    else -> Unit
                }
            }
        }

        lifecycleScope.launchWhenStarted {
            viewModel.offerProducts.collect { resource ->
                when (resource.status) {
                    Resource.Status.SUCCESS -> {
                        resource.data?.let {
                            offerAdapter.differ.submitList(it)
                        }
                    }
                    Resource.Status.ERROR -> {
                        Toast.makeText(requireContext(), resource.message ?: "Error", Toast.LENGTH_SHORT).show()
                    }
                    else -> Unit
                }
            }
        }
    }

    override fun clicks() {

    }

    private fun setupBestProductsRv() {
        bestProductsAdapter = BestProductsAdapter()
        dataBinding.rvBestProductsBase.apply {
            layoutManager = GridLayoutManager(requireContext(), 2)
            adapter = bestProductsAdapter
        }
    }

    private fun setupOfferRv() {
        offerAdapter = BestProductsAdapter()
        dataBinding.rvOffer.apply {
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            adapter = offerAdapter
        }
    }

    override fun callApis() {
        viewModel.fetchTable()
    }

}

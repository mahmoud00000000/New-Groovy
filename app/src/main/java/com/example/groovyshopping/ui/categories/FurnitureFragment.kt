package com.example.groovyshopping.ui.categories

import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.groovyshopping.R
import com.example.groovyshopping.databinding.FragmentFurnitureBinding
import com.example.groovyshopping.ui.viewmodels.AuthViewModel
import com.example.groovyshopping.ui.adapter.BestProductsAdapter
import com.example.groovyshopping.ui.viewmodels.ProductViewModel
import com.example.groovyshopping.user.base.BaseFragment
import com.example.groovyshopping.user.data.remote.networkHandling.Resource
import kotlin.reflect.KClass

class FurnitureFragment : BaseFragment<FragmentFurnitureBinding, ProductViewModel>(){
    private lateinit var offerAdapter: BestProductsAdapter
    private lateinit var bestProductsAdapter: BestProductsAdapter
    override fun layoutResource(): Int = R.layout.fragment_furniture



    override fun viewModelClass(): KClass<ProductViewModel> = ProductViewModel::class



    override fun setUI(savedInstanceState: Bundle?) {
        dataBinding.viewModel = viewModel
        setupOfferRv()
        setupBestProductsRv()
    }

    override fun observer() {
        setupBestProductsRv()
        setupOfferRv()
        clicks() // ← ضيفها هنا بعد ما الـ adapters يكونوا متجهزين

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

        offerAdapter.onClick = { product ->
            val bundle = Bundle().apply { putParcelable("product", product) }
            findNavController().navigate(R.id.action_global_productDetailsFragment, bundle)
        }

        bestProductsAdapter.onClick = { product ->
            val bundle = Bundle().apply { putParcelable("product", product) }
            findNavController().navigate(R.id.action_global_productDetailsFragment, bundle)
        }

    }

    override fun callApis() {
        viewModel.fetchFurniture()
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

}
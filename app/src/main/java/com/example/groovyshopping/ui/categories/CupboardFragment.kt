package com.example.groovyshopping.ui.categories

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.groovyshopping.R
import com.example.groovyshopping.data.Product
import com.example.groovyshopping.databinding.FragmentCupboardBinding
import com.example.groovyshopping.ui.viewmodels.AuthViewModel
import com.example.groovyshopping.ui.adapter.BestProductsAdapter
import com.example.groovyshopping.ui.viewmodels.ProductViewModel
import com.example.groovyshopping.user.base.BaseFragment
import com.example.groovyshopping.user.data.remote.networkHandling.Resource
import kotlinx.coroutines.flow.collectLatest
import kotlin.reflect.KClass

class CupboardFragment : BaseFragment<FragmentCupboardBinding, ProductViewModel>() {

    private lateinit var offerAdapter: BestProductsAdapter
    private lateinit var bestProductsAdapter: BestProductsAdapter

    override fun layoutResource(): Int = R.layout.fragment_cupboard

    override fun viewModelClass(): KClass<ProductViewModel> = ProductViewModel::class

    override fun setUI(savedInstanceState: Bundle?) {
        setupOfferRv()
        setupBestProductsRv()
    }

    override fun observer() {
        lifecycleScope.launchWhenStarted {
            viewModel.cupboardProducts.collectLatest { result ->
                when (result.status) {
                    Resource.Status.LOADING -> {
                        dataBinding.progressBar.visibility = View.VISIBLE
                    }

                    Resource.Status.SUCCESS -> {
                        dataBinding.progressBar.visibility = View.GONE
                        val allProducts = result.data.orEmpty()

                        val offers = allProducts.filter { (it.offerPercentage ?: 0f) > 0 }
                        val best = allProducts

                        offerAdapter.differ.submitList(offers)
                        bestProductsAdapter.differ.submitList(best)
                    }

                    Resource.Status.ERROR -> {
                        dataBinding.progressBar.visibility = View.GONE
                        Toast.makeText(
                            requireContext(),
                            result.message ?: "حصل خطأ",
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                    else -> Unit
                }
            }
        }
    }

    override fun clicks() {
        offerAdapter.onClick = { product ->
            navigateToProductDetails(product)
        }

        bestProductsAdapter.onClick = { product ->
            navigateToProductDetails(product)
        }
    }

    override fun callApis() {
        viewModel.fetchCupboardProducts()
    }

    private fun setupOfferRv() {
        offerAdapter = BestProductsAdapter()
        dataBinding.rvOffer.apply {
            layoutManager = LinearLayoutManager(
                requireContext(),
                LinearLayoutManager.HORIZONTAL,
                false
            )
            adapter = offerAdapter
        }
    }

    private fun setupBestProductsRv() {
        bestProductsAdapter = BestProductsAdapter()
        dataBinding.rvBestProductsBase.apply {
            layoutManager = GridLayoutManager(requireContext(), 2)
            adapter = bestProductsAdapter
        }
    }

    private fun navigateToProductDetails(product: Product) {
        val bundle = Bundle().apply { putParcelable("product", product) }
        findNavController().navigate(
            R.id.action_homeFragment_to_productDetailsFragment,
            bundle
        )
    }
}
package com.example.groovyshopping.ui.categories

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.groovyshopping.R
import com.example.groovyshopping.databinding.FragmentAccessoryBinding
import com.example.groovyshopping.ui.viewmodels.AuthViewModel
import com.example.groovyshopping.ui.adapter.BestProductsAdapter
import com.example.groovyshopping.ui.viewmodels.ProductViewModel
import com.example.groovyshopping.user.base.BaseFragment
import com.example.groovyshopping.user.data.remote.networkHandling.Resource
import kotlinx.coroutines.flow.collectLatest
import kotlin.reflect.KClass

class AccessoryFragment : BaseFragment<FragmentAccessoryBinding, ProductViewModel>() {

    private lateinit var offerAdapter: BestProductsAdapter
    private lateinit var bestProductsAdapter: BestProductsAdapter

    override fun layoutResource(): Int = R.layout.fragment_accessory

    override fun viewModelClass(): KClass<ProductViewModel> = ProductViewModel::class

    override fun setUI(savedInstanceState: Bundle?) {
        dataBinding.viewModel = viewModel
        setupOfferRv()
        setupBestProductsRv()
    }

    override fun observer() {

        // الضغط على العناصر
        offerAdapter.onClick = { product ->
            val bundle = Bundle().apply { putParcelable("product", product) }
            findNavController().navigate(R.id.action_global_productDetailsFragment, bundle)
        }

        bestProductsAdapter.onClick = { product ->
            val bundle = Bundle().apply { putParcelable("product", product) }
            findNavController().navigate(R.id.action_global_productDetailsFragment, bundle)
        }

        // أفضل المنتجات
        lifecycleScope.launchWhenStarted {
            viewModel.bestProducts.collectLatest { result ->
                when (result.status) {
                    Resource.Status.LOADING -> showLoading()
                    Resource.Status.SUCCESS -> {
                        hideLoading()
                        val products = result.data ?: emptyList()
                        bestProductsAdapter.differ.submitList(products)
                    }
                    Resource.Status.ERROR -> {
                        hideLoading()
                        Toast.makeText(requireContext(), result.message ?: "حصل خطأ", Toast.LENGTH_SHORT).show()
                    }
                    else -> Unit
                }
            }
        }

        // عروض
        lifecycleScope.launchWhenStarted {
            viewModel.offerProducts.collectLatest { result ->
                when (result.status) {
                    Resource.Status.LOADING -> showLoading()
                    Resource.Status.SUCCESS -> {
                        hideLoading()
                        val offers = result.data?.filter { (it.offerPercentage ?: 0f) > 0 } ?: emptyList()
                        offerAdapter.differ.submitList(offers)
                    }
                    Resource.Status.ERROR -> {
                        hideLoading()
                        Toast.makeText(requireContext(), result.message ?: "حصل خطأ", Toast.LENGTH_SHORT).show()
                    }
                    else -> Unit
                }
            }
        }
    }

    override fun clicks() {
        // فاضي دلوقتي
    }

    override fun callApis() {
        viewModel.fetchAccessory()
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

    private fun showLoading() {
        dataBinding.progressBar.visibility = View.VISIBLE
    }

    private fun hideLoading() {
        dataBinding.progressBar.visibility = View.GONE
    }
}
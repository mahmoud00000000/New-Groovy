package com.example.groovyshopping.ui.categories

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.groovyshopping.R
import com.example.groovyshopping.databinding.FragmentTableBinding
import com.example.groovyshopping.ui.viewmodels.AuthViewModel
import com.example.groovyshopping.ui.adapter.BestProductsAdapter
import com.example.groovyshopping.ui.viewmodels.ProductViewModel
import com.example.groovyshopping.user.base.BaseFragment
import com.example.groovyshopping.user.data.remote.networkHandling.Resource
import kotlinx.coroutines.flow.collectLatest
import kotlin.reflect.KClass

class TableFragment : BaseFragment<FragmentTableBinding, ProductViewModel>() {

    private lateinit var offerAdapter: BestProductsAdapter
    private lateinit var bestProductsAdapter: BestProductsAdapter

    override fun layoutResource(): Int = R.layout.fragment_table

    override fun viewModelClass(): KClass<ProductViewModel> = ProductViewModel::class

    override fun setUI(savedInstanceState: Bundle?) {
        dataBinding.viewModel = viewModel
        setupOfferRv()
        setupBestProductsRv()
    }

    override fun observer() {

        // عروض المنتجات
        offerAdapter.onClick = { product ->
            val bundle = Bundle().apply { putParcelable("product", product) }
            findNavController().navigate(R.id.action_global_productDetailsFragment, bundle)
        }

        // أفضل المنتجات
        bestProductsAdapter.onClick = { product ->
            val bundle = Bundle().apply { putParcelable("product", product) }
            findNavController().navigate(R.id.action_global_productDetailsFragment, bundle)
        }

        lifecycleScope.launchWhenStarted {
            viewModel.tableProducts.collectLatest { result ->
                when (result.status) {
                    Resource.Status.LOADING -> showLoading()
                    Resource.Status.SUCCESS -> {
                        hideLoading()
                        val allProducts = result.data ?: emptyList()
                        val offers = allProducts.filter { (it.offerPercentage ?: 0f) > 0 }
                        val best = allProducts

                        offerAdapter.differ.submitList(offers)
                        bestProductsAdapter.differ.submitList(best)
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
        // أي أكشن إضافي لو محتاج
    }

    override fun callApis() {
        viewModel.fetchTable()
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

package com.example.groovyshopping.ui.fragments

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.SearchView
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.groovyshopping.R
import com.example.groovyshopping.databinding.FragmentSearchBinding
import com.example.groovyshopping.ui.adapter.SearchAdapter
import com.example.groovyshopping.ui.viewmodels.AuthViewModel
import com.example.groovyshopping.ui.viewmodels.SearchViewModel
import com.example.groovyshopping.user.base.BaseFragment
import kotlin.reflect.KClass

class SearchFragment : BaseFragment<FragmentSearchBinding, SearchViewModel>() {

    private lateinit var adapter: SearchAdapter

    override fun layoutResource(): Int = R.layout.fragment_search

    override fun viewModelClass(): KClass<SearchViewModel> = SearchViewModel::class

    override fun setUI(savedInstanceState: Bundle?) {
        dataBinding.viewModel = viewModel
        setupRecyclerView()
    }

    override fun observer() {
        lifecycleScope.launchWhenStarted {
            viewModel.productsInCategory.collect { products ->
                adapter.differ.submitList(products)
            }
        }
    }

    override fun clicks() {
        dataBinding.etSearch.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                val query = s?.toString()?.trim()
                if (!query.isNullOrEmpty()) {
                    viewModel.searchByCategory(query)
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })
    }

    override fun callApis() {

    }

    private fun setupRecyclerView() {
        adapter = SearchAdapter()
        dataBinding.rvSearchResults.adapter = adapter
        dataBinding.rvSearchResults.layoutManager = LinearLayoutManager(requireContext())
    }
}

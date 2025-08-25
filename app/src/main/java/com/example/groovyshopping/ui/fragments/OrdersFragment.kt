package com.example.groovyshopping.ui.fragments

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.groovyshopping.R
import com.example.groovyshopping.databinding.FragmentOrdersBinding
import com.example.groovyshopping.databinding.FragmentSearchBinding
import com.example.groovyshopping.ui.adapter.AllOrdersAdapter
import com.example.groovyshopping.ui.viewmodels.AuthViewModel
import com.example.groovyshopping.ui.viewmodels.OrderViewModel
import com.example.groovyshopping.user.base.BaseFragment
import com.example.groovyshopping.user.data.remote.networkHandling.Resource
import kotlinx.coroutines.flow.collectLatest
import kotlin.reflect.KClass

class OrdersFragment : BaseFragment<FragmentOrdersBinding, OrderViewModel>() {

    private val ordersAdapter by lazy { AllOrdersAdapter() }

    override fun layoutResource(): Int = R.layout.fragment_orders

    override fun viewModelClass(): KClass<OrderViewModel> = OrderViewModel::class

    override fun setUI(savedInstanceState: Bundle?) {
        dataBinding.viewModel = viewModel
        setupOrdersRv()
    }

    override fun observer() {
        lifecycleScope.launchWhenStarted {
            viewModel.allOrders.collectLatest {
                when (it.status) {
                    Resource.Status.LOADING -> {
                        dataBinding.progressbarAllOrders.visibility = View.VISIBLE
                    }

                    Resource.Status.SUCCESS -> {
                        dataBinding.progressbarAllOrders.visibility = View.GONE
                        val orders = it.data ?: emptyList()
                        if (orders.isEmpty()) {
                            dataBinding.tvEmptyOrders.visibility = View.VISIBLE
                        } else {
                            dataBinding.tvEmptyOrders.visibility = View.GONE
                            ordersAdapter.differ.submitList(orders)
                        }
                    }

                    Resource.Status.ERROR -> {
                        dataBinding.progressbarAllOrders.visibility = View.GONE
                        Toast.makeText(requireContext(), it.message ?: "Unexpected error", Toast.LENGTH_SHORT).show()
                    }

                    else -> Unit
                }
            }
        }

        ordersAdapter.onClick = {
            val action =
                OrdersFragmentDirections.actionOrdersFragmentToOrderDetailFragment(it)
            findNavController().navigate(action)
        }
    }

    override fun clicks() {

        dataBinding.imageCloseOrders.setOnClickListener {
            findNavController().navigateUp()
        }
        // لو فيه حاجة ممكن تضاف هنا
    }

    override fun callApis() {
        viewModel.getAllOrders()
        // لو بتحب تبدأ بجلب الداتا هنا بدل viewModel.init جوه الـ ViewModel
    }

    private fun setupOrdersRv() {
        dataBinding.rvAllOrders.apply {
            adapter = ordersAdapter
            layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
        }
    }
}
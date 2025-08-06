package com.example.groovyshopping.ui.fragments

import android.os.Bundle
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.groovyshopping.R
import com.example.groovyshopping.data.order.OrderStatus
import com.example.groovyshopping.data.order.getOrderStatus
import com.example.groovyshopping.databinding.FragmentOrderDetailBinding
import com.example.groovyshopping.databinding.FragmentSearchBinding
import com.example.groovyshopping.ui.adapter.BillingProductsAdapter
import com.example.groovyshopping.ui.viewmodels.AuthViewModel
import com.example.groovyshopping.ui.viewmodels.OrderViewModel
import com.example.groovyshopping.user.base.BaseFragment
import com.example.groovyshopping.utils.VerticalItemDecoration
import kotlin.reflect.KClass

class OrderDetailFragment : BaseFragment<FragmentOrderDetailBinding, OrderViewModel>() {

    override fun layoutResource(): Int = R.layout.fragment_order_detail

    override fun viewModelClass(): KClass<OrderViewModel> = OrderViewModel::class

    private val billingProductsAdapter by lazy { BillingProductsAdapter() }
    private val args by navArgs<OrderDetailFragmentArgs>()

    override fun setUI(savedInstanceState: Bundle?) {
        dataBinding.viewModel = viewModel

        val order = args.order

        if (order != null) {

            setupOrderRv()

            dataBinding.apply {
                tvOrderId.text = "Order #${order.orderId}"

                stepView.setSteps(
                    mutableListOf(
                        OrderStatus.Ordered.status,
                        OrderStatus.Confirmed.status,
                        OrderStatus.Shipped.status,
                        OrderStatus.Delivered.status,
                    )
                )

                val currentOrderState = when (getOrderStatus(order.orderStatus)) {
                    is OrderStatus.Ordered -> 0
                    is OrderStatus.Confirmed -> 1
                    is OrderStatus.Shipped -> 2
                    is OrderStatus.Delivered -> 3
                    else -> 0
                }

                stepView.go(currentOrderState, false)
                if (currentOrderState == 3) {
                    stepView.done(true)
                }

                tvFullName.text = order.address.fullName
                tvAddress.text = "${order.address.street} ${order.address.city}"
                tvPhoneNumber.text = order.address.phone

                tvTotalPrice.text = "$ ${order.totalPrice}"
            }

            billingProductsAdapter.differ.submitList(order.products)
        } else {
            // هنا ممكن تعرض رسالة أو تخلي الفراجمنت ترجع للخلف
            Toast.makeText(requireContext(), "Order is missing!", Toast.LENGTH_SHORT).show()
            findNavController().popBackStack()
        }
    }

    private fun setupOrderRv() {
        dataBinding.rvProducts.apply {
            adapter = billingProductsAdapter
            layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
            addItemDecoration(VerticalItemDecoration())
        }
    }

    override fun observer() {
        // لو حبيت تراقب LiveData من الـ ViewModel
    }

    override fun clicks() {

    }

    override fun callApis() {
        // لو عايز تنادي API في بداية الفراجمنت
    }
}
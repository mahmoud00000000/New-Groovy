package com.example.groovyshopping.ui.fragments

import android.app.AlertDialog
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.groovyshopping.R
import com.example.groovyshopping.data.Address
import com.example.groovyshopping.data.CartProduct
import com.example.groovyshopping.data.order.Order
import com.example.groovyshopping.data.order.OrderStatus
import com.example.groovyshopping.databinding.FragmentBillingBinding
import com.example.groovyshopping.databinding.FragmentSearchBinding
import com.example.groovyshopping.ui.adapter.AddressAdapter
import com.example.groovyshopping.ui.adapter.BillingProductsAdapter
import com.example.groovyshopping.ui.viewmodels.AuthViewModel
import com.example.groovyshopping.ui.viewmodels.BillingViewModel
import com.example.groovyshopping.user.base.BaseFragment
import com.example.groovyshopping.user.data.remote.networkHandling.Resource
import com.example.groovyshopping.utils.HorizontalItemDecoration
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.flow.collectLatest
import kotlin.reflect.KClass

class BillingFragment : BaseFragment<FragmentBillingBinding, BillingViewModel>() {

    private val args by navArgs<BillingFragmentArgs>()
    private val addressAdapter by lazy { AddressAdapter() }
    private val billingProductsAdapter by lazy { BillingProductsAdapter() }

    private var products = emptyList<CartProduct>()
    private var totalPrice = 0f

    private var selectedAddress: Address? = null

    override fun layoutResource(): Int = R.layout.fragment_billing

    override fun viewModelClass(): KClass<BillingViewModel> = BillingViewModel::class

    override fun setUI(savedInstanceState: Bundle?) {
        dataBinding.viewModel = viewModel

        products = args.products.toList()
        totalPrice = products.sumOf { cartProduct ->
            (cartProduct.product.price * cartProduct.quantity).toDouble()
        }.toFloat()

        billingProductsAdapter.differ.submitList(products)
        dataBinding.tvTotalPrice.text = "$ ${"%.2f".format(totalPrice)}"

        dataBinding.rvProducts.apply {
            layoutManager = LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)
            adapter = billingProductsAdapter
            addItemDecoration(HorizontalItemDecoration(0))
        }


        dataBinding.rvAddress.apply {
            layoutManager = LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)
            adapter = addressAdapter
            addItemDecoration(HorizontalItemDecoration(0))
        }

        billingProductsAdapter.differ.submitList(products)
        dataBinding.tvTotalPrice.text = "$ $totalPrice"

        if (!args.payment) {
            dataBinding.apply {
                buttonPlaceOrder.visibility = View.INVISIBLE
                totalBoxContainer.visibility = View.INVISIBLE
                middleLine.visibility = View.INVISIBLE
                bottomLine.visibility = View.INVISIBLE
            }
        }
    }

    override fun observer() {
        lifecycleScope.launchWhenStarted {
            viewModel.address.collectLatest {
                when (it.status) {
                    Resource.Status.LOADING -> {
                        dataBinding.progressbarAddress.visibility = View.VISIBLE
                    }

                    Resource.Status.SUCCESS -> {
                        dataBinding.progressbarAddress.visibility = View.GONE
                        val addresses = it.data ?: emptyList()
                        addressAdapter.differ.submitList(addresses)
                    }

                    Resource.Status.ERROR -> {
                        dataBinding.progressbarAddress.visibility = View.GONE
                        Toast.makeText(requireContext(), "Error ${it.message}", Toast.LENGTH_SHORT).show()
                    }

                    else -> Unit
                }
            }
        }

        lifecycleScope.launchWhenStarted {
            viewModel.order.collectLatest {
                when (it.status) {
                    Resource.Status.LOADING -> {
                        // إخفاء الزر واظهار لودينج
                        dataBinding.buttonPlaceOrder.apply {
                            isEnabled = false
                            alpha = 0.5f
                            text = "Placing Order..."
                        }
                    }

                    Resource.Status.SUCCESS -> {
                        dataBinding.buttonPlaceOrder.apply {
                            isEnabled = true
                            alpha = 1f
                            text = "Place Order"
                        }

                        Snackbar.make(requireView(), "Order placed successfully!", Snackbar.LENGTH_SHORT).show()

                        // ممكن تضيف هنا Navigation لو حابب تروح لصفحة الطلبات مثلاً

                        findNavController().navigate(R.id.action_billingFragment_to_cartFragment)
                    }

                    Resource.Status.ERROR -> {
                        dataBinding.buttonPlaceOrder.apply {
                            isEnabled = true
                            alpha = 1f
                            text = "Place Order"
                        }

                        Toast.makeText(requireContext(), "Error: ${it.message}", Toast.LENGTH_SHORT).show()
                    }

                    else -> Unit
                }
            }
        }

    }

    override fun clicks() {
        dataBinding.imageAddAddress.setOnClickListener {
            val myAddress = Address(/* هنا البيانات */)
            val action = BillingFragmentDirections.actionBillingFragmentToAddressFragment(myAddress)
            findNavController().navigate(action)
        }

        dataBinding.buttonPlaceOrder.setOnClickListener {
            if (selectedAddress == null) {
                Toast.makeText(requireContext(), "Please select an address", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            showOrderConfirmationDialog()
        }

        addressAdapter.onClick = {
            selectedAddress = it
            if (!args.payment) {
                val b = Bundle().apply { putParcelable("address", selectedAddress) }
                findNavController().navigate(R.id.action_billingFragment_to_addressFragment, b)
            }
        }

        dataBinding.imageCloseBilling.setOnClickListener {
            findNavController().navigateUp()
        }

    }

    override fun callApis() {
        viewModel.getUserAddresses()
    }

    private fun showOrderConfirmationDialog() {
        val alertDialog = AlertDialog.Builder(requireContext()).apply {
            setTitle("Order items")
            setMessage("Do you want to order your cart items?")
            setNegativeButton("Cancel") { dialog, _ -> dialog.dismiss() }
            setPositiveButton("Yes") { dialog, _ ->
                val order = Order(
                    OrderStatus.Ordered.status,
                    totalPrice,
                    products,
                    selectedAddress!!
                )
                viewModel.placeOrder(order)
                dialog.dismiss()
            }
        }
        alertDialog.create()
        alertDialog.show()
    }
}
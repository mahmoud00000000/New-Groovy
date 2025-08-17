package com.example.groovyshopping.ui.fragments

import android.app.AlertDialog
import android.icu.lang.UCharacter.VerticalOrientation
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.groovyshopping.R
import com.example.groovyshopping.data.FirebaseCommon
import com.example.groovyshopping.databinding.FragmentCartBinding
import com.example.groovyshopping.ui.adapter.CartProductAdapter
import com.example.groovyshopping.ui.viewmodels.AuthViewModel
import com.example.groovyshopping.ui.viewmodels.CartViewModel
import com.example.groovyshopping.user.base.BaseFragment
import com.example.groovyshopping.user.data.remote.networkHandling.Resource
import com.example.groovyshopping.utils.VerticalItemDecoration
import kotlinx.coroutines.flow.collectLatest
import kotlin.reflect.KClass

class CartFragment : BaseFragment<FragmentCartBinding, CartViewModel>() {

    private val cartAdapter by lazy { CartProductAdapter() }
    private var totalPrice = 0f

    override fun layoutResource(): Int = R.layout.fragment_cart

    override fun viewModelClass(): KClass<CartViewModel> = CartViewModel::class

    override fun setUI(savedInstanceState: Bundle?) {
        dataBinding.viewModel = viewModel
        setupCartRv()
    }

    override fun observer() {
        lifecycleScope.launchWhenStarted {
            viewModel.productsPrice.collectLatest { price ->
                price?.let {
                    totalPrice = it
                    dataBinding.tvTotalPrice.text = "$ $price"
                }
            }
        }

        lifecycleScope.launchWhenStarted {
            viewModel.cartProducts.collectLatest {
                when (it.status) {
                    Resource.Status.LOADING -> {
                        dataBinding.progressbarCart.visibility = View.VISIBLE
                    }

                    Resource.Status.SUCCESS -> {
                        dataBinding.progressbarCart.visibility = View.INVISIBLE
                        val products = it.data ?: emptyList()
                        if (products.isEmpty()) {
                            showEmptyCart()
                            hideOtherViews()
                        } else {
                            hideEmptyCart()
                            showOtherViews()
                            cartAdapter.differ.submitList(products)
                        }
                    }

                    Resource.Status.ERROR -> {
                        dataBinding.progressbarCart.visibility = View.INVISIBLE
                        Toast.makeText(requireContext(), it.message ?: "Unexpected error", Toast.LENGTH_SHORT).show()
                    }

                    else -> Unit
                }
            }
        }

        lifecycleScope.launchWhenStarted {
            viewModel.deleteDialog.collectLatest {
                val alertDialog = AlertDialog.Builder(requireContext()).apply {
                    setTitle("Delete item from cart")
                    setMessage("Do you want to delete this item from your cart?")
                    setNegativeButton("Cancel") { dialog, _ -> dialog.dismiss() }
                    setPositiveButton("Yes") { dialog, _ ->
                        viewModel.deleteCartProduct(it)
                        dialog.dismiss()
                    }
                }
                alertDialog.create().show()
            }
        }
    }

    override fun clicks() {
        cartAdapter.onProductClick = {
            val b = Bundle().apply {
                putParcelable("product", it.product)
            }
            findNavController().navigate(R.id.action_cartFragment_to_productDetailsFragment, b)
        }

        cartAdapter.onPlusClick = {
            viewModel.changeQuantity(it, FirebaseCommon.QuantityChanging.INCREASE)
        }

        cartAdapter.onMinusClick = {
            viewModel.changeQuantity(it, FirebaseCommon.QuantityChanging.DECREASE)
        }

        dataBinding.buttonCheckout.setOnClickListener {
            val action = CartFragmentDirections.actionCartFragmentToBillingFragment(
                totalPrice,
                cartAdapter.differ.currentList.toTypedArray(),
                true
            )
            findNavController().navigate(action)
        }

        dataBinding.imageCloseCart.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    override fun callApis() {
        // لو في API محتاج يتعمل له call لما الشاشة تفتح
        // مفيش حاجة دلوقتي، فنسيبها فاضية
    }

    private fun setupCartRv() {
        dataBinding.rvCart.apply {
            layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
            adapter = cartAdapter
            addItemDecoration(VerticalItemDecoration())
        }
    }

    private fun showOtherViews() {
        dataBinding.apply {
            rvCart.visibility = View.VISIBLE
            totalBoxContainer.visibility = View.VISIBLE
            buttonCheckout.visibility = View.VISIBLE
        }
    }

    private fun hideOtherViews() {
        dataBinding.apply {
            rvCart.visibility = View.GONE
            totalBoxContainer.visibility = View.GONE
            buttonCheckout.visibility = View.GONE
        }
    }

    private fun hideEmptyCart() {
        dataBinding.layoutCartEmpty.visibility = View.GONE
    }

    private fun showEmptyCart() {
        dataBinding.layoutCartEmpty.visibility = View.VISIBLE
    }
}

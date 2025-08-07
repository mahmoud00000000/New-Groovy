package com.example.groovyshopping.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.groovyshopping.R
import com.example.groovyshopping.data.CartProduct
import com.example.groovyshopping.data.Product
import com.example.groovyshopping.databinding.FragmentProductDetailsBinding
import com.example.groovyshopping.ui.viewmodels.AuthViewModel
import com.example.groovyshopping.ui.adapter.ColorsAdapter
import com.example.groovyshopping.ui.adapter.SizesAdapter
import com.example.groovyshopping.ui.adapter.ViewPager2Images
import com.example.groovyshopping.ui.viewmodels.DetailsViewModel
import com.example.groovyshopping.user.base.BaseFragment
import com.example.groovyshopping.user.data.remote.networkHandling.Resource
import kotlinx.coroutines.flow.collectLatest
import kotlin.reflect.KClass

class ProductDetailsFragment : BaseFragment<FragmentProductDetailsBinding, DetailsViewModel>() {

    override fun layoutResource(): Int = R.layout.fragment_product_details
    override fun viewModelClass(): KClass<DetailsViewModel> = DetailsViewModel::class

    private val viewPagerAdapter by lazy { ViewPager2Images() }
    private val colorsAdapter by lazy { ColorsAdapter() }
    private val sizeAdapter by lazy { SizesAdapter() }


    private var selectedColor: Int? = null
    private var selectedSize: String? = null
    private var currentProduct: Product? = null

    override fun setUI(savedInstanceState: Bundle?) {
        currentProduct = arguments?.getParcelable("product")


        currentProduct?.let { product ->
            dataBinding.tvProductName.text = product.name
            dataBinding.tvProductPrice.text = "${product.price} EGP"
            dataBinding.tvProductDescription.text = product.description

            // إعداد ViewPager2 للصور
            dataBinding.viewPagerProductImages.adapter = viewPagerAdapter
            viewPagerAdapter.differ.submitList(product.images)

            // إعداد RecyclerView للألوان
            dataBinding.rvColors.adapter = colorsAdapter
            dataBinding.rvColors.layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            colorsAdapter.differ.submitList(product.colors)

            // إعداد RecyclerView للأحجام
            dataBinding.rvSize.adapter = sizeAdapter
            dataBinding.rvSize.layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            sizeAdapter.differ.submitList(product.sizes)
        }
        Log.d("ProductDetailsFragment", "products colors: ${currentProduct?.colors}")
        Log.d("ProductDetailsFragment", "products sizes: ${currentProduct?.sizes}")

    }



    override fun observer() {
        lifecycleScope.launchWhenStarted {
            viewModel.addToCart.collectLatest {
                when (it.status) {
                    Resource.Status.LOADING -> {

                    }

                    Resource.Status.SUCCESS -> {
                        dataBinding.buttonAddToCart.setBackgroundColor(resources.getColor(R.color.black))
                    }

                    Resource.Status.ERROR -> {
                        Toast.makeText(requireContext(), it.message ?: "حدث خطأ", Toast.LENGTH_SHORT).show()
                    }

                    else -> Unit
                }
            }
        }
    }

    override fun clicks() {
        dataBinding.imageClose.setOnClickListener {
            findNavController().navigateUp()
        }

        sizeAdapter.onItemClick = {
            selectedSize = it
        }

        colorsAdapter.onItemClick = {
            selectedColor = it
        }

        dataBinding.buttonAddToCart.setOnClickListener {
            currentProduct?.let {
                viewModel.addUpdateProductInCart(
                    CartProduct(it, 1, selectedColor, selectedSize)
                )
            }
        }
    }

    override fun callApis() {
        // مفيش API هنا دلوقتي
    }
}
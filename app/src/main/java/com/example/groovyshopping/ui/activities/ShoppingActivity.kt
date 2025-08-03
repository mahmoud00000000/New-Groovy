package com.example.groovyshopping.ui.activities

import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.groovyshopping.R
import com.example.groovyshopping.base.BaseActivity
import com.example.groovyshopping.databinding.ActivityShoppingBinding
import com.example.groovyshopping.ui.viewmodels.AuthViewModel
import com.example.groovyshopping.ui.viewmodels.CartViewModel
import com.example.groovyshopping.user.data.remote.networkHandling.Resource
import com.google.android.material.bottomnavigation.BottomNavigationView
import org.koin.androidx.viewmodel.ext.android.viewModel
import kotlinx.coroutines.flow.collectLatest
import kotlin.reflect.KClass

class ShoppingActivity : BaseActivity<ActivityShoppingBinding, AuthViewModel>() {

    private val cartViewModel: CartViewModel by viewModel()

    override fun resourceId(): Int = R.layout.activity_shopping

    override fun viewModelClass(): KClass<AuthViewModel> = AuthViewModel::class

    override fun setUI(savedInstanceState: Bundle?) {
        dataBinding.viewModel = viewModel

        window.statusBarColor = ContextCompat.getColor(this, R.color.g_blue)

        val navController = findNavController(R.id.shoppingHostFragment)
        dataBinding.bottomNavigation.setupWithNavController(navController)
    }

    override fun observer() {
        lifecycleScope.launchWhenStarted {
            cartViewModel.cartProducts.collectLatest {
                when (it.status) {
                    Resource.Status.SUCCESS -> {
                        val count = it.data?.size ?: 0
                        val bottomNavigation = findViewById<BottomNavigationView>(R.id.bottomNavigation)
                        bottomNavigation.getOrCreateBadge(R.id.cartFragment).apply {
                            number = count
                            backgroundColor = resources.getColor(R.color.g_blue)
                        }
                    }

                    else -> Unit
                }
            }
        }
    }

    override fun clicks() {
        // أي كليك هاندل هنا لو محتاج تضيف
    }

    override fun callApis() {
        // لو محتاج تنادي API في بداية الدخول
    }
}
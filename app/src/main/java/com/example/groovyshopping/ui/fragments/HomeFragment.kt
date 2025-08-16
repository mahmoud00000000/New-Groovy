package com.example.groovyshopping.ui.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.groovyshopping.R
import com.example.groovyshopping.databinding.FragmentHomeBinding
import com.example.groovyshopping.ui.activities.LoginActivity
import com.example.groovyshopping.ui.viewmodels.AuthViewModel
import com.example.groovyshopping.ui.adapter.HomeViewPagerAdapter
import com.example.groovyshopping.ui.categories.AccessoryFragment
import com.example.groovyshopping.ui.categories.ChairFragment
import com.example.groovyshopping.ui.categories.CupboardFragment
import com.example.groovyshopping.ui.categories.FurnitureFragment
import com.example.groovyshopping.ui.categories.MainCategory
import com.example.groovyshopping.ui.categories.TableFragment
import com.google.android.material.tabs.TabLayoutMediator
import com.example.groovyshopping.user.base.BaseFragment
import kotlin.reflect.KClass

class HomeFragment : BaseFragment<FragmentHomeBinding, AuthViewModel>(){
    override fun layoutResource(): Int = R.layout.fragment_home



    override fun viewModelClass(): KClass<AuthViewModel> = AuthViewModel::class



    override fun setUI(savedInstanceState: Bundle?) {
        dataBinding.viewModel = viewModel

        val categoriesFragment = arrayListOf<Fragment>(
            MainCategory(),
            ChairFragment(),
            CupboardFragment(),
            TableFragment(),
            AccessoryFragment(),
            FurnitureFragment()

        )

        val viewPager2Adapter =
            HomeViewPagerAdapter(categoriesFragment, childFragmentManager, lifecycle)
        dataBinding.viewpagerHome.adapter = viewPager2Adapter
        TabLayoutMediator(dataBinding.tabLayout, dataBinding.viewpagerHome){ tab, position ->
            when (position) {
                0 -> tab.text = "Home"
                1 -> tab.text = "Chair"
                2 -> tab.text = "Cupboard"
                3 -> tab.text = "Table"
                4 -> tab.text = "Accessory"
                5 -> tab.text = "Furniture"
            }
        }.attach()

    }

    override fun observer() {



    }

    override fun clicks() {

        dataBinding.screenBar.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_searchFragment)
        }

    }

    override fun callApis() {

    }

}

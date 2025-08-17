package com.example.groovyshopping.ui.fragments

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.groovyshopping.R
import com.example.groovyshopping.data.Address
import com.example.groovyshopping.databinding.FragmentAddressBinding
import com.example.groovyshopping.databinding.FragmentSearchBinding
import com.example.groovyshopping.ui.viewmodels.AddressViewModel
import com.example.groovyshopping.ui.viewmodels.AuthViewModel
import com.example.groovyshopping.user.base.BaseFragment
import com.example.groovyshopping.user.data.remote.networkHandling.Resource
import kotlinx.coroutines.flow.collectLatest
import kotlin.reflect.KClass

class AddressFragment : BaseFragment<FragmentAddressBinding, AddressViewModel>() {

    private val args by navArgs<AddressFragmentArgs>()

    override fun layoutResource(): Int = R.layout.fragment_address

    override fun viewModelClass(): KClass<AddressViewModel> = AddressViewModel::class

    override fun setUI(savedInstanceState: Bundle?) {
        dataBinding.viewModel = viewModel

        val address = args.address
        if (address == null) {
            dataBinding.buttonDelelte.visibility = View.GONE
        } else {
            dataBinding.apply {
                edAddressTitle.setText(address.addressTitle)
                edFullName.setText(address.fullName)
                edStreet.setText(address.street)
                edPhone.setText(address.phone)
                edCity.setText(address.city)
                edState.setText(address.state)
            }
        }
    }

    override fun observer() {
        lifecycleScope.launchWhenStarted {
            viewModel.addNewAddress.collectLatest { result ->
                when (result.status) {
                    Resource.Status.LOADING -> {
                        dataBinding.progressbarAddress.visibility = View.VISIBLE
                    }

                    Resource.Status.SUCCESS -> {
                        dataBinding.progressbarAddress.visibility = View.INVISIBLE
                        findNavController().navigateUp()
                    }

                    Resource.Status.ERROR -> {
                        dataBinding.progressbarAddress.visibility = View.INVISIBLE
                        Toast.makeText(requireContext(), result.message ?: "Unexpected error", Toast.LENGTH_SHORT).show()
                    }

                    else -> Unit
                }
            }
        }

        lifecycleScope.launchWhenStarted {
            viewModel.error.collectLatest {
                Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun clicks() {
        dataBinding.buttonSave.setOnClickListener {
            val addressTitle = dataBinding.edAddressTitle.text.toString()
            val fullName = dataBinding.edFullName.text.toString()
            val street = dataBinding.edStreet.text.toString()
            val phone = dataBinding.edPhone.text.toString()
            val city = dataBinding.edCity.text.toString()
            val state = dataBinding.edState.text.toString()

            val address = Address(addressTitle, fullName, street, phone, city, state)
            viewModel.addAddress(address)
        }

        dataBinding.imageAddressClose.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    override fun callApis() {
        // مش محتاجين ننده أي API دلوقتي
    }
}

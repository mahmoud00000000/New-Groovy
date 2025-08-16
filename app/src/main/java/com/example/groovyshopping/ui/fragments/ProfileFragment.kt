package com.example.groovyshopping.ui.fragments

import android.R.attr.order
import android.content.Intent
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import android.widget.Toast
import android.graphics.Color
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.groovyshopping.R
import com.example.groovyshopping.databinding.FragmentProfileBinding
import com.example.groovyshopping.ui.activities.LoginRegisterActivity
import com.example.groovyshopping.ui.viewmodels.ProfileViewModel
import com.example.groovyshopping.user.base.BaseFragment
import com.example.groovyshopping.user.data.remote.networkHandling.Resource
import com.squareup.picasso.BuildConfig
import kotlinx.coroutines.launch
import kotlin.reflect.KClass

class ProfileFragment : BaseFragment<FragmentProfileBinding, ProfileViewModel>() {

    override fun layoutResource(): Int = R.layout.fragment_profile

    override fun viewModelClass(): KClass<ProfileViewModel> = ProfileViewModel::class

    override fun setUI(savedInstanceState: Bundle?) {
        dataBinding.viewModel = viewModel
        dataBinding.tvVersion.text = "Version ${BuildConfig.VERSION_CODE}"
    }

    override fun clicks() {
        dataBinding.constraintProfile.setOnClickListener {
            findNavController().navigate(R.id.action_profileFragment_to_userAccountFragment)
        }

        dataBinding.linearAllOrders.setOnClickListener {
            findNavController().navigate(R.id.action_profileFragment_to_ordersFragment)
        }

        dataBinding.linearTrackOrder.setOnClickListener {
            viewModel.getLastOrder { lastOrder ->
                if (lastOrder != null) {
                    val action = ProfileFragmentDirections
                        .actionProfileFragmentToOrderDetailFragment(lastOrder)
                    findNavController().navigate(action)
                } else {
                    Toast.makeText(context, "No orders found", Toast.LENGTH_SHORT).show()
                }
            }
        }

        dataBinding.linearBilling.setOnClickListener {
            val action = ProfileFragmentDirections.actionProfileFragmentToBillingFragment(
                0f,
                emptyArray(),
                false
            )
            findNavController().navigate(action)
        }

        dataBinding.linearLogOut.setOnClickListener {
            viewModel.logout()
        }
    }

    override fun observer() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {

                // ✅ مراقبة بيانات المستخدم
                launch {
                    viewModel.user.collect { resource ->
                        when (resource.status) {
                            Resource.Status.LOADING -> {
                                dataBinding.progressbarSettings.visibility = View.VISIBLE
                            }

                            Resource.Status.SUCCESS -> {
                                dataBinding.progressbarSettings.visibility = View.GONE
                                Glide.with(requireView())
                                    .load(resource.data?.imagePath)
                                    .error(ColorDrawable(Color.BLACK))
                                    .into(dataBinding.imageUser)

                                dataBinding.tvUserName.text =
                                    "${resource.data?.firstName} ${resource.data?.lastName}"
                            }

                            Resource.Status.ERROR -> {
                                dataBinding.progressbarSettings.visibility = View.GONE
                                Toast.makeText(
                                    requireContext(),
                                    resource.message ?: "Error occurred",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }

                            Resource.Status.UNSPECIFIED -> {
                                dataBinding.progressbarSettings.visibility = View.GONE
                            }
                        }
                    }
                }

                // ✅ مراقبة نجاح تسجيل الخروج
                launch {
                    viewModel.logoutSuccess.collect { success ->
                        if (success == true) {
                            val intent = Intent(requireContext(), LoginRegisterActivity::class.java)
                            intent.flags =
                                Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                            startActivity(intent)
                            requireActivity().finish()
                            viewModel.setLogoutSuccess(false)
                        }
                    }
                }
            }
        }
    }

    override fun callApis() {
        viewModel.getUser()
    }

    override fun onResume() {
        super.onResume()

    }
}

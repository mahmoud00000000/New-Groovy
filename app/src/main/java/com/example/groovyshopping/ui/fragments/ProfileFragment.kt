package com.example.groovyshopping.ui.fragments

import android.content.Intent
import android.os.Bundle
import com.example.groovyshopping.R
import com.example.groovyshopping.databinding.FragmentProfileBinding
import com.example.groovyshopping.ui.AuthViewModel
import com.example.groovyshopping.ui.activities.LoginActivity
import com.example.groovyshopping.ui.activities.LoginRegisterActivity
import com.example.groovyshopping.user.base.BaseFragment
import kotlin.reflect.KClass

class ProfileFragment : BaseFragment<FragmentProfileBinding, AuthViewModel>(){
    override fun layoutResource(): Int = R.layout.fragment_profile



    override fun viewModelClass(): KClass<AuthViewModel> = AuthViewModel::class



    override fun setUI(savedInstanceState: Bundle?) {
        dataBinding.viewModel = viewModel
    }

    override fun observer() {

        viewModel.logoutSuccess.observe(viewLifecycleOwner) { success ->
            if (success == true) {
                // توجه لشاشة تسجيل الدخول بعد تسجيل الخروج
                val intent = Intent(requireContext(), LoginRegisterActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
                requireActivity().finish()

                viewModel.logoutSuccess.value = false // رجع القيمة عشان ما يعيدش التنقل
            }
        }

    }

    override fun clicks() {

        dataBinding.btnLogout.setOnClickListener {

            viewModel.logoutUser()

        }

    }

    override fun callApis() {

    }

}

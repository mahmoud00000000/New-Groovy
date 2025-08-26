package com.example.groovyshopping.ui.fragments

import android.content.Intent
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import android.graphics.Color
import android.util.Log
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.groovyshopping.R
import com.example.groovyshopping.data.User
import com.example.groovyshopping.databinding.FragmentSearchBinding
import com.example.groovyshopping.databinding.FragmentUserAccountBinding
import com.example.groovyshopping.databinding.LayoutBottomSheetPasswordBinding
import com.example.groovyshopping.ui.viewmodels.AuthViewModel
import com.example.groovyshopping.ui.viewmodels.UserAccountViewModel
import com.example.groovyshopping.user.base.BaseFragment
import com.example.groovyshopping.user.data.remote.networkHandling.Resource
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.collectLatest
import kotlin.reflect.KClass

class UserAccountFragment : BaseFragment<FragmentUserAccountBinding, UserAccountViewModel>() {

    override fun layoutResource(): Int = R.layout.fragment_user_account
    override fun viewModelClass(): KClass<UserAccountViewModel> = UserAccountViewModel::class

    private lateinit var imageActivityResultLauncher: ActivityResultLauncher<Intent>
    private var imageUri: Uri? = null

    override fun setUI(savedInstanceState: Bundle?) {
        dataBinding.lifecycleOwner = viewLifecycleOwner
        dataBinding.viewModel = viewModel

        // Launcher to pick image
        imageActivityResultLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                result.data?.data?.let {
                    imageUri = it
                    Glide.with(requireContext())
                        .load(imageUri)
                        .into(dataBinding.imageUser)
                }
            }

        dataBinding.imageEdit.setOnClickListener {
            val intent = Intent(Intent.ACTION_GET_CONTENT).apply { type = "image/*" }
            imageActivityResultLauncher.launch(intent)
        }

        dataBinding.buttonSave.setOnClickListener {
            val firstName = dataBinding.edFirstName.text.toString().trim()
            val lastName = dataBinding.edLastName.text.toString().trim()
            val email = dataBinding.edEmail.text.toString().trim()
            val uid = FirebaseAuth.getInstance().currentUser?.uid.orEmpty()

            if (uid.isNotEmpty()) {
                val user = User(uid, firstName, lastName, email)
                viewModel.updateUser(user, imageUri, requireContext())
            } else {
                Toast.makeText(requireContext(), "User not authenticated", Toast.LENGTH_SHORT).show()
            }
        }

        dataBinding.tvUpdatePassword.setOnClickListener {
            setupBottomSheetDialog { password ->
                viewModel.updatePassword(password)
            }
        }
    }

    override fun observer() {
        lifecycleScope.launchWhenStarted {
            viewModel.user.collectLatest {
                when (it.status) {
                    Resource.Status.LOADING -> showUserLoading()
                    Resource.Status.SUCCESS -> {
                        hideUserLoading()
                        it.data?.let { user -> showUserInformation(user) }
                    }
                    Resource.Status.ERROR -> {
                        hideUserLoading()
                        Toast.makeText(requireContext(), it.message ?: "Error loading user", Toast.LENGTH_SHORT).show()
                    }
                    else -> Unit
                }
            }
        }

        lifecycleScope.launchWhenStarted {
            viewModel.updateInfo.collectLatest {
                when (it.status) {
                    Resource.Status.LOADING -> {
                        dataBinding.buttonSave.isEnabled = false
                        dataBinding.buttonSave.text = "Saving..."
                    }
                    Resource.Status.SUCCESS -> {
                        dataBinding.buttonSave.isEnabled = true
                        dataBinding.buttonSave.text = "Save"
                        viewModel.getUser() // Refresh UI after save
                        Toast.makeText(requireContext(), "Saved successfully", Toast.LENGTH_SHORT).show()
                    }
                    Resource.Status.ERROR -> {
                        dataBinding.buttonSave.isEnabled = true
                        dataBinding.buttonSave.text = "Save"
                        Toast.makeText(requireContext(), it.message ?: "Failed to save", Toast.LENGTH_SHORT).show()
                    }
                    else -> Unit
                }
            }
        }
    }

    override fun clicks() {
        dataBinding.imageCloseUserAccount.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    override fun callApis() {
        viewModel.getUser()
    }

    private fun showUserInformation(data: User) {
        dataBinding.apply {
            Glide.with(requireContext())
                .load(data.imagePath)
                .placeholder(ColorDrawable(Color.DKGRAY))
                .error(ColorDrawable(Color.BLACK))
                .into(imageUser)

            edFirstName.setText(data.firstName)
            edLastName.setText(data.lastName)
            edEmail.setText(data.email)
        }
    }

    private fun hideUserLoading() {
        with(dataBinding) {
            progressbarAccount.visibility = View.GONE
            imageUser.visibility = View.VISIBLE
            imageEdit.visibility = View.VISIBLE
            edFirstName.visibility = View.VISIBLE
            edLastName.visibility = View.VISIBLE
            edEmail.visibility = View.VISIBLE
            tvUpdatePassword.visibility = View.VISIBLE
            buttonSave.visibility = View.VISIBLE
        }
    }

    private fun showUserLoading() {
        with(dataBinding) {
            progressbarAccount.visibility = View.VISIBLE
            imageUser.visibility = View.INVISIBLE
            imageEdit.visibility = View.INVISIBLE
            edFirstName.visibility = View.INVISIBLE
            edLastName.visibility = View.INVISIBLE
            edEmail.visibility = View.INVISIBLE
            tvUpdatePassword.visibility = View.INVISIBLE
            buttonSave.visibility = View.INVISIBLE
        }
    }

    private fun setupBottomSheetDialog(onConfirm: (String) -> Unit) {
        val dialog = BottomSheetDialog(requireContext())
        val dialogBinding: LayoutBottomSheetPasswordBinding = DataBindingUtil.inflate(
            LayoutInflater.from(requireContext()),
            R.layout.layout_bottom_sheet_password,
            null,
            false
        )

        dialogBinding.lifecycleOwner = viewLifecycleOwner
        dialog.setContentView(dialogBinding.root)
        dialog.show()

        dialogBinding.buttonConfirm.setOnClickListener {
            val password = dialogBinding.edPassword.text.toString().trim()
            if (password.isNotEmpty()) {
                onConfirm(password)
                dialog.dismiss()
            } else {
                Toast.makeText(requireContext(), "Please enter a password", Toast.LENGTH_SHORT).show()
            }
        }
    }
}

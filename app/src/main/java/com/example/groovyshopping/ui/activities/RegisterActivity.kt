package com.example.groovyshopping.ui.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.example.groovyshopping.base.BaseActivity
import kotlin.reflect.KClass
import com.example.groovyshopping.R
import com.example.groovyshopping.data.User
import com.example.groovyshopping.databinding.ActivityRegisterBinding
import com.example.groovyshopping.ui.viewmodels.AuthViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore


class RegisterActivity : BaseActivity<ActivityRegisterBinding, AuthViewModel>() {
    override fun resourceId(): Int = R.layout.activity_register

    val auth: FirebaseAuth = FirebaseAuth.getInstance()



    override fun viewModelClass(): KClass<AuthViewModel> = AuthViewModel::class


    override fun setUI(savedInstanceState: Bundle?) {
        dataBinding.viewModel = viewModel
        window.statusBarColor = ContextCompat.getColor(this, R.color.g_blue)
    }

    override fun observer() {



    }

    override fun clicks() {

        dataBinding.DoYouHaveAccount.setOnClickListener{
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }

        dataBinding.btnRegister.setOnClickListener{
            val firstName = dataBinding.edFirstNameRegister.text.toString()
            val lastName = dataBinding.edLastNameRegister.text.toString()
            val email = dataBinding.edEmailRegister.text.toString()
            val password = dataBinding.edPasswordRegister.text.toString()
            if (firstName.isBlank() || lastName.isBlank() || email.isBlank() || password.isBlank())
                Toast.makeText(this, "Missing Required Fields!", Toast.LENGTH_SHORT).show()
            else if (password.length < 6)
                Toast.makeText(this, "Password is too short", Toast.LENGTH_SHORT).show()
            else {
                addUser(email,password)
            }
        }

        val viewModel = ViewModelProvider(this)[AuthViewModel::class.java]
        dataBinding.viewModel = viewModel
        dataBinding.lifecycleOwner = this

    }

    override fun callApis() {

    }



     fun verifyEmail() {
        val user = auth.currentUser

        user!!.sendEmailVerification()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(this, "Check your email", Toast.LENGTH_SHORT).show()
                }
            }
    }

    fun addUser(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val firebaseUser = auth.currentUser
                    val uid = firebaseUser?.uid ?: return@addOnCompleteListener

                    val firstName = dataBinding.edFirstNameRegister.text.toString()
                    val lastName = dataBinding.edLastNameRegister.text.toString()

                    val user = User(
                        uid = uid,
                        firstName = firstName,
                        lastName = lastName,
                        email = email
                    )

                    FirebaseFirestore.getInstance()
                        .collection("user")
                        .document(uid)
                        .set(user)
                        .addOnSuccessListener {
                            Toast.makeText(this, "User Registered Successfully", Toast.LENGTH_SHORT).show()
                            verifyEmail()
                        }
                        .addOnFailureListener { e ->
                            Toast.makeText(this, "Failed to save user: ${e.message}", Toast.LENGTH_SHORT).show()
                        }
                } else {
                    Toast.makeText(this, task.exception?.localizedMessage, Toast.LENGTH_SHORT).show()
                }
            }
    }

}
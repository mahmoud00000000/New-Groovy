package com.example.groovyshopping.di.modules




import com.example.groovyshopping.data.FirebaseCommon
import com.example.groovyshopping.ui.viewmodels.AddressViewModel
import com.example.groovyshopping.ui.viewmodels.AuthViewModel
import com.example.groovyshopping.ui.viewmodels.BillingViewModel
import com.example.groovyshopping.ui.viewmodels.CartViewModel
import com.example.groovyshopping.ui.viewmodels.DetailsViewModel
import com.example.groovyshopping.ui.viewmodels.ProductViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import org.koin.dsl.module
import org.koin.androidx.viewmodel.dsl.viewModel



val viewModelModule = module {
    viewModel { AuthViewModel(get(),get()) }
    viewModel { ProductViewModel(get(), get()) }
    viewModel { DetailsViewModel(get(), get(), get(), get(), get()) }
    viewModel { CartViewModel(get(), get(), get(), get(), get()) }
    viewModel { AddressViewModel(get(),get(),get(),get()) }
    viewModel { BillingViewModel(get(), get(),get(), get()) }
    //viewModel { SearchViewModel(get(),get()) }
    //viewModel { HomeViewModel(get(),get()) }
    //viewModel { AddressViewModel(get(),get()) }
    //viewModel { ProductDetailsViewModel(get(),get()) }
    //viewModel { CartViewModel(get(),get()) }
    //viewModel { OrderViewModel(get(),get()) }

    single { FirebaseFirestore.getInstance() }
    single { FirebaseAuth.getInstance() }
    single { FirebaseCommon(get(), get()) }

}

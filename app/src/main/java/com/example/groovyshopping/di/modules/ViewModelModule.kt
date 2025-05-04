package com.example.groovyshopping.di.modules




import androidx.appcompat.widget.AppCompatDrawableManager.get
import com.example.groovyshopping.ui.AuthViewModel
import com.example.groovyshopping.user.data.reporsitory.MainRepository
import com.homecookapp.user.utils.AppManger
import org.koin.dsl.module
import org.koin.androidx.viewmodel.dsl.viewModel



val viewModelModule = module {
    viewModel { AuthViewModel(get(),get()) }
    //viewModel { SearchViewModel(get(),get()) }
    //viewModel { HomeViewModel(get(),get()) }
    //viewModel { AddressViewModel(get(),get()) }
    //viewModel { ProductDetailsViewModel(get(),get()) }
    //viewModel { CartViewModel(get(),get()) }
    //viewModel { OrderViewModel(get(),get()) }

}

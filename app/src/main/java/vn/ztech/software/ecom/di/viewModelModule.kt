package vn.ztech.software.ecom.di

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import vn.ztech.software.ecom.ui.auth.login.LogInViewModel
import vn.ztech.software.ecom.ui.auth.otp.OtpViewModel
import vn.ztech.software.ecom.ui.auth.signup.SignUpViewModel
import vn.ztech.software.ecom.ui.home.HomeViewModel
import vn.ztech.software.ecom.ui.product_details.ProductDetailsViewModel
import vn.ztech.software.ecom.ui.splash.SplashViewModel

fun viewModelModule() = module {
    viewModel { SplashViewModel(get()) }
    viewModel { SignUpViewModel(get()) }
    viewModel { LogInViewModel(get()) }
    viewModel { HomeViewModel(get())}
    viewModel { ProductDetailsViewModel(get()) }
    viewModel { OtpViewModel(get()) }

}
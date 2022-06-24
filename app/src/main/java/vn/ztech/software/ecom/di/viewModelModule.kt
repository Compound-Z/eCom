package vn.ztech.software.ecom.di

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import vn.ztech.software.ecom.ui.account.logout.AccountViewModel
import vn.ztech.software.ecom.ui.address.AddressViewModel
import vn.ztech.software.ecom.ui.auth.login.ForgotPasswordViewModel
import vn.ztech.software.ecom.ui.auth.login.LogInViewModel
import vn.ztech.software.ecom.ui.auth.otp.OtpViewModel
import vn.ztech.software.ecom.ui.auth.signup.SignUpViewModel
import vn.ztech.software.ecom.ui.cart.CartViewModel
import vn.ztech.software.ecom.ui.category.CategoryViewModel
import vn.ztech.software.ecom.ui.home.HomeViewModel
import vn.ztech.software.ecom.ui.order.OrderViewModel
import vn.ztech.software.ecom.ui.product_details.ProductDetailsViewModel
import vn.ztech.software.ecom.ui.splash.SplashViewModel

fun viewModelModule() = module {
    viewModel { SplashViewModel(get()) }
    viewModel { SignUpViewModel(get()) }
    viewModel { LogInViewModel(get()) }
    viewModel { HomeViewModel(get())}
    viewModel { ProductDetailsViewModel(get()) }
    viewModel { OtpViewModel(get()) }
    viewModel { ForgotPasswordViewModel(get()) }
    viewModel { AccountViewModel(get()) }
    viewModel { CategoryViewModel(get(), get()) }
    viewModel { CartViewModel(get()) }
    viewModel { AddressViewModel(get()) }
    viewModel { OrderViewModel(get()) }

}
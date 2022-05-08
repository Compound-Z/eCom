package vn.ztech.software.ecom.di

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import vn.ztech.software.ecom.ui.home.HomeViewModel
import vn.ztech.software.ecom.ui.product_details.ProductDetailsViewModel

fun viewModelModule() = module {
    viewModel { HomeViewModel(get())}
    viewModel { ProductDetailsViewModel(get()) }
}
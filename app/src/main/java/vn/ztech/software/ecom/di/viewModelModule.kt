package vn.ztech.software.ecom.di

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import vn.ztech.software.ecom.presentation.home.HomeViewModel

fun viewModelModule() = module {
    viewModel { HomeViewModel(get())}
}
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
import vn.ztech.software.ecom.ui.order.order.OrderViewModel
import vn.ztech.software.ecom.ui.order.order_details.OrderDetailsViewModel
import vn.ztech.software.ecom.ui.order.order_history.ListOrdersViewModel
import vn.ztech.software.ecom.ui.product_details.ListReviewOfProductViewModel
import vn.ztech.software.ecom.ui.product_details.ProductDetailsViewModel
import vn.ztech.software.ecom.ui.review.ListReviewQueueViewModel
import vn.ztech.software.ecom.ui.review.ListReviewReviewedViewModel
import vn.ztech.software.ecom.ui.review.create_review.CreateReviewViewModel
import vn.ztech.software.ecom.ui.review.update_review.UpdateReviewViewModel
import vn.ztech.software.ecom.ui.splash.SplashViewModel
import vn.ztech.software.ecom.ui.main.MainViewModel
import vn.ztech.software.ecom.ui.shop.ShopUseCase
import vn.ztech.software.ecom.ui.shop.ShopViewModel
import vn.ztech.software.ecom.ui.shop.categories.ListCategoriesInShopViewModel
import vn.ztech.software.ecom.ui.shop.products.ListProductsInShopViewModel

fun viewModelModule() = module {
    viewModel { SplashViewModel(get()) }
    viewModel { SignUpViewModel(get()) }
    viewModel { LogInViewModel(get()) }
    viewModel { HomeViewModel(get())}
    viewModel { ProductDetailsViewModel(get(), get(), get()) }
    viewModel { OtpViewModel(get()) }
    viewModel { ForgotPasswordViewModel(get()) }
    viewModel { AccountViewModel(get()) }
    viewModel { CategoryViewModel(get(), get()) }
    viewModel { CartViewModel(get()) }
    viewModel { AddressViewModel(get()) }
    viewModel { OrderViewModel(get(), get()) }
    viewModel { OrderDetailsViewModel(get())}
    viewModel { ListOrdersViewModel(get()) }
    viewModel { MainViewModel(get()) }
    viewModel { ListReviewOfProductViewModel(get()) }
    viewModel { ListReviewQueueViewModel(get()) }
    viewModel { ListReviewReviewedViewModel(get()) }
    viewModel { CreateReviewViewModel(get()) }
    viewModel { UpdateReviewViewModel(get()) }
    viewModel { ShopViewModel(get()) }
    viewModel { ListProductsInShopViewModel(get()) }
    viewModel { ListCategoriesInShopViewModel(get()) }

}
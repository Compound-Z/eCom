package vn.ztech.software.ecom.di

import org.koin.dsl.module
import vn.ztech.software.ecom.ui.home.IListProductUseCase
import vn.ztech.software.ecom.ui.home.ListProductsUseCase
import vn.ztech.software.ecom.domain.use_case.get_product_details.IProductDetailsUseCase
import vn.ztech.software.ecom.domain.use_case.get_product_details.ProductDetailsUseCase
import vn.ztech.software.ecom.ui.account.logout.AccountUseCase
import vn.ztech.software.ecom.ui.account.logout.IAccountUseCase
import vn.ztech.software.ecom.ui.address.AddressUseCase
import vn.ztech.software.ecom.ui.address.IAddressUseCase
import vn.ztech.software.ecom.ui.auth.forgot_password.IResetPasswordUseCase
import vn.ztech.software.ecom.ui.auth.forgot_password.ResetPasswordUseCase
import vn.ztech.software.ecom.ui.auth.login.ILogInUseCase
import vn.ztech.software.ecom.ui.auth.login.LogInUseCase
import vn.ztech.software.ecom.ui.auth.otp.IOtpUseCase
import vn.ztech.software.ecom.ui.auth.otp.OtpUseCase
import vn.ztech.software.ecom.ui.auth.signup.ISignUpUseCase
import vn.ztech.software.ecom.ui.auth.signup.SignUpUseCase
import vn.ztech.software.ecom.ui.cart.CartUseCase
import vn.ztech.software.ecom.ui.cart.ICartUseCase
import vn.ztech.software.ecom.ui.category.IListCategoriesUseCase
import vn.ztech.software.ecom.ui.category.ListCategoriesUseCase
import vn.ztech.software.ecom.ui.order.IOrderUserCase
import vn.ztech.software.ecom.ui.order.order.IShippingUserCase
import vn.ztech.software.ecom.ui.order.OrderUseCase
import vn.ztech.software.ecom.ui.order.order.ShippingUseCase
import vn.ztech.software.ecom.ui.review.IReviewUseCase
import vn.ztech.software.ecom.ui.review.ReviewUseCase
import vn.ztech.software.ecom.ui.shop.IShopUseCase
import vn.ztech.software.ecom.ui.shop.ShopUseCase
import vn.ztech.software.ecom.ui.shop.categories.IListCategoriesInShopUseCase
import vn.ztech.software.ecom.ui.shop.categories.ListCategoriesInShopUseCase
import vn.ztech.software.ecom.ui.shop.products.IListProductsInShopUseCase
import vn.ztech.software.ecom.ui.shop.products.ListProductsInShopUseCase
import vn.ztech.software.ecom.ui.splash.ISplashUseCase
import vn.ztech.software.ecom.ui.splash.SplashUseCase
import vn.ztech.software.ecomSeller.ui.main.IMainUseCase
import vn.ztech.software.ecomSeller.ui.main.MainUseCase

fun useCaseModule() = module {
    factory<ISplashUseCase> { SplashUseCase(get(), get()) }
    factory<ISignUpUseCase> { SignUpUseCase(get()) }
    factory<ILogInUseCase> { LogInUseCase(get(), get()) }
    factory<IResetPasswordUseCase> { ResetPasswordUseCase(get())}
    factory<IListProductUseCase> { ListProductsUseCase(get()) }
    factory<IProductDetailsUseCase> { ProductDetailsUseCase(get()) }
    factory<IOtpUseCase> { OtpUseCase(get()) }
    factory<IAccountUseCase> { AccountUseCase(get(), get()) }
    factory<IListCategoriesUseCase> { ListCategoriesUseCase(get()) }
    factory<ICartUseCase>{CartUseCase(get())}
    factory<IAddressUseCase> { AddressUseCase(get()) }
    factory<IShippingUserCase> { ShippingUseCase(get()) }
    factory<IOrderUserCase> { OrderUseCase(get()) }
    factory<IMainUseCase> { MainUseCase(get()) }
    factory<IReviewUseCase> { ReviewUseCase(get()) }
    factory<IShopUseCase> { ShopUseCase(get()) }
    factory<IListProductsInShopUseCase> { ListProductsInShopUseCase(get()) }
    factory<IListCategoriesInShopUseCase> { ListCategoriesInShopUseCase(get()) }

}
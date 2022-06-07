package vn.ztech.software.ecom.di

import org.koin.dsl.module
import vn.ztech.software.ecom.domain.use_case.get_list_product.IListProductUseCase
import vn.ztech.software.ecom.domain.use_case.get_list_product.ListProductsUseCase
import vn.ztech.software.ecom.domain.use_case.get_product_details.IProductDetailsUseCase
import vn.ztech.software.ecom.domain.use_case.get_product_details.ProductDetailsUseCase
import vn.ztech.software.ecom.ui.auth.login.ILogInUseCase
import vn.ztech.software.ecom.ui.auth.login.LogInUseCase
import vn.ztech.software.ecom.ui.auth.otp.IOtpUseCase
import vn.ztech.software.ecom.ui.auth.otp.OtpUseCase
import vn.ztech.software.ecom.ui.auth.signup.ISignUpUseCase
import vn.ztech.software.ecom.ui.auth.signup.SignUpUseCase
import vn.ztech.software.ecom.ui.splash.ISplashUseCase
import vn.ztech.software.ecom.ui.splash.SplashUseCase

fun useCaseModule() = module {
    factory<ISplashUseCase> { SplashUseCase(get(), get()) }
    factory<ISignUpUseCase> { SignUpUseCase(get()) }
    factory<ILogInUseCase> { LogInUseCase(get(), get()) }
    factory<IListProductUseCase> { ListProductsUseCase(get()) }
    factory<IProductDetailsUseCase> { ProductDetailsUseCase(get()) }
    factory<IOtpUseCase> { OtpUseCase(get()) }

}
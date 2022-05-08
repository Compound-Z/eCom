package vn.ztech.software.ecom.di

import org.koin.dsl.module
import vn.ztech.software.ecom.domain.use_case.cart.CartUseCase
import vn.ztech.software.ecom.domain.use_case.cart.ICartUseCase
import vn.ztech.software.ecom.domain.use_case.get_list_product.IListProductUseCase
import vn.ztech.software.ecom.domain.use_case.get_list_product.ListProductsUseCase
import vn.ztech.software.ecom.domain.use_case.get_product_details.IProductDetailsUseCase
import vn.ztech.software.ecom.domain.use_case.get_product_details.ProductDetailsUseCase

fun useCaseModule() = module {
    factory<IListProductUseCase> { ListProductsUseCase(get()) }
    factory<IProductDetailsUseCase> { ProductDetailsUseCase(get()) }
    factory<ICartUseCase> {CartUseCase(get())}
}
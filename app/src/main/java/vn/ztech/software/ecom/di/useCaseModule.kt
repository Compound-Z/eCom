package vn.ztech.software.ecom.di

import org.koin.dsl.module
import vn.ztech.software.ecom.domain.use_case.get_list_product.IListProductUseCase
import vn.ztech.software.ecom.domain.use_case.get_list_product.ListProductsUseCase

fun useCaseModule() = module {
    factory<IListProductUseCase> { ListProductsUseCase(get()) }
}
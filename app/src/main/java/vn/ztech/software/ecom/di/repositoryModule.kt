package vn.ztech.software.ecom.di

import org.koin.dsl.module
import vn.ztech.software.ecom.data.repository.IProductRepository
import vn.ztech.software.ecom.data.repository.ProductRepository

fun repositoryModule() = module {
    single<IProductRepository> {ProductRepository(get())}
}
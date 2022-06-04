package vn.ztech.software.ecom.di

import org.koin.dsl.module
import vn.ztech.software.ecom.repository.AuthRepository
import vn.ztech.software.ecom.repository.IAuthRepository
import vn.ztech.software.ecom.repository.IProductRepository
import vn.ztech.software.ecom.repository.ProductRepository

fun repositoryModule() = module {
    single<IProductRepository> {ProductRepository(get())}
    single<IAuthRepository> {AuthRepository(get(), get())}
}
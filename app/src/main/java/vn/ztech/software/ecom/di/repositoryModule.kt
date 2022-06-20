package vn.ztech.software.ecom.di

import org.koin.dsl.module
import vn.ztech.software.ecom.repository.*

fun repositoryModule() = module {
    single<IProductRepository> {ProductRepository(get())}
    single<IAuthRepository> {AuthRepository(get(), get())}
    single<ICategoryRepository> {CategoryRepository(get())}
    single<ICartRepository> {CartRepository(get())}
}
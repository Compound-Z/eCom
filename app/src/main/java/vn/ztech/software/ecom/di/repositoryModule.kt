package vn.ztech.software.ecom.di

import org.koin.dsl.module
import vn.ztech.software.ecom.repository.*
import vn.ztech.software.ecom.repository.IReviewRepository
import vn.ztech.software.ecom.repository.ReviewRepository

fun repositoryModule() = module {
    single<IProductRepository> {ProductRepository(get())}
    single<IAuthRepository> {AuthRepository(get(), get())}
    single<ICategoryRepository> {CategoryRepository(get())}
    single<ICartRepository> {CartRepository(get())}
    single<IAddressRepository> { AddressRepository(get()) }
    single<IShippingRepository> { ShippingRepository(get()) }
    single<IOrderRepository> { OrderRepository(get()) }
    single<IReviewRepository> { ReviewRepository(get()) }

}
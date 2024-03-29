package vn.ztech.software.ecom.di

import org.koin.core.scope.Scope
import org.koin.dsl.module
import vn.ztech.software.ecom.api.*
import vn.ztech.software.ecom.network.IApiGenerator

fun apiModule() = module {
    fun <T> Scope.createApi(clazz: Class<T>): T = get<IApiGenerator>().api(clazz)

    single {createApi(IProductApi::class.java)}
    single {createApi(IAuthApi::class.java)}
    single {createApi(ICategoryApi::class.java)}
    single {createApi(ICartApi::class.java)}
    single {createApi(IAddressApi::class.java)}
    single {createApi(IShippingApi::class.java)}
    single {createApi(IOrderApi::class.java)}
    single {createApi(IReviewApi::class.java)}
    single {createApi(IShopApi::class.java)}

}
package vn.ztech.software.ecom.di

import org.koin.core.scope.Scope
import org.koin.dsl.module
import vn.ztech.software.ecom.api.IAuthApi
import vn.ztech.software.ecom.api.ICategoryApi
import vn.ztech.software.ecom.api.IProductApi
import vn.ztech.software.ecom.network.IApiGenerator

fun apiModule() = module {
    fun <T> Scope.createApi(clazz: Class<T>): T = get<IApiGenerator>().api(clazz)

    single {createApi(IProductApi::class.java)}
    single {createApi(IAuthApi::class.java)}
    single {createApi(ICategoryApi::class.java)}

}
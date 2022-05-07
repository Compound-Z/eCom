package vn.ztech.software.ecom.di

import org.koin.core.scope.Scope
import org.koin.dsl.module
import vn.ztech.software.ecom.data.remote.api.IProductApi
import vn.ztech.software.ecom.network.IApiGenerator

fun apiModule() = module {
    fun <T> Scope.createApi(clazz: Class<T>): T = get<IApiGenerator>().api(clazz)

    single {createApi(IProductApi::class.java)}
}
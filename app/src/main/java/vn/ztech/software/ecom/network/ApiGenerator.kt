package vn.ztech.software.ecom.network

import retrofit2.Retrofit
import vn.ztech.software.ecom.common.Constants

interface IApiGenerator {
    fun <T> api(clazz: Class<T>): T
}

class ApiGenerator(val apiBuilder: Retrofit.Builder) : IApiGenerator {

    override fun <T> api(clazz: Class<T>) =
        apiBuilder.baseUrl(Constants.getBaseUrl()).build().create(clazz)

}

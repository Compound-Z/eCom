package vn.ztech.software.ecom.di

fun allModule() = listOf(
    viewModelModule(),
    useCaseModule(),
    repositoryModule(),
    networkModule(),
    apiModule()
)
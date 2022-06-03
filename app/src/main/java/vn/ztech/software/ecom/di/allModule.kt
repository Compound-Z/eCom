package vn.ztech.software.ecom.di

fun allModule() = listOf(
    applicationModule(),
    viewModelModule(),
    useCaseModule(),
    repositoryModule(),
    networkModule(),
    apiModule()
)
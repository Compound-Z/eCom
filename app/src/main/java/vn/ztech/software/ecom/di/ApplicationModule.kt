package vn.ztech.software.ecom.di

import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module
import vn.ztech.software.ecom.database.user.UserManager

fun applicationModule() = module {

//    single {
//        FirebaseAnalytics.getInstance(androidApplication())
//    }

//    single {
//        AuthStateManager.getInstance(androidApplication())
//    }

    single {
        UserManager.getInstance(androidApplication())
    }

//    single {
//        AuthorizationService(androidApplication())
//    }
//
//    single<IAnalyticsClient> {
//        AnalyticsClient(get(), get())
//    }

}
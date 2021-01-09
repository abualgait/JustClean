/*
 * Created by  Muhammad Sayed  on 1/9/21 9:37 AM
 * Copyright (c) JustClean. All rights reserved.
 */

package com.justclean.app.shared.koin


import androidx.preference.PreferenceManager
import androidx.room.Room
import com.justclean.app.BuildConfig
import com.justclean.app.shared.data.DataManager
import com.justclean.app.shared.databases.AppDatabase
import com.justclean.app.shared.databases.DBRepository
import com.justclean.app.shared.factory.FavRepositoryFactory
import com.justclean.app.shared.factory.LocaleFavRepository
import com.justclean.app.shared.factory.RemoteFavRepository
import com.justclean.app.shared.network.ApiInterface
import com.justclean.app.shared.network.ApiRepository
import com.justclean.app.shared.network.HeadersInterceptor
import com.justclean.app.shared.network.NetworkHelper
import com.justclean.app.shared.rx.SchedulerProvider
import com.justclean.app.shared.rx.SchedulerProviderImpl
import com.justclean.app.shared.util.BuildUtil
import com.justclean.app.shared.util.SharedPref
import com.justclean.app.shared.util.io.app.MyApp.Companion.applicationContext
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit


val appModule = module {

    single {
        DataManager(
            get(),
            get(),
            get(),
            get(),
            get()
        )
    }
    single { ApiRepository(get()) }
    single { NetworkHelper(androidContext()) }
    single { DBRepository(get()) }
    single { SharedPref(get()) }
   // single { HeadersInterceptor(get()) }
    single { PreferenceManager.getDefaultSharedPreferences(androidContext()) }
    single<SchedulerProvider> { SchedulerProviderImpl() }

    single {
        RemoteFavRepository(get(), get(), get())
    }
    single { LocaleFavRepository(get(), get()) }
    single { FavRepositoryFactory(get(), get(), get(), get()) }


    // OkHttpClient
    single {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY


        val builder = OkHttpClient.Builder().apply {

            //addInterceptor(get<HeadersInterceptor>())
            connectTimeout(60, TimeUnit.SECONDS)
            readTimeout(60, TimeUnit.SECONDS)
            writeTimeout(60, TimeUnit.SECONDS)
            if (BuildUtil.isDebug()) {
                addInterceptor(loggingInterceptor)
            }
        }

        builder.build()
    }

    // ApiInterface
    single {

        val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
        Retrofit.Builder()
            .baseUrl(BuildConfig.API_BASE_URL)
            .client(get())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
            .create(ApiInterface::class.java)
    }


    single { Room.databaseBuilder(get(), AppDatabase::class.java, "justclean-db").build() }


}
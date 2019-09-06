package com.lyasin.eventlist.di

import android.content.Context
import com.bumptech.glide.BuildConfig
import com.lyasin.eventlist.api.ApiConfig
import com.lyasin.eventlist.api.EventApi
import com.lyasin.eventlist.util.hasNetwork
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

val remoteModule = module {
    single { createOkHttpClient(androidContext()) }

    single { createWebService<EventApi>(get(), ApiConfig.URL) }
}

fun createOkHttpClient(context: Context): OkHttpClient {
    val cacheSize = (50 * 1024 * 1024).toLong()
    val myCache = Cache(context.cacheDir, cacheSize)

    val httpLoggingInterceptor = HttpLoggingInterceptor()
    if (BuildConfig.DEBUG) {
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
    } else {
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BASIC
    }
    return OkHttpClient.Builder()
        .cache(myCache)
        .connectTimeout(60L, TimeUnit.SECONDS)
        .readTimeout(60L, TimeUnit.SECONDS)
        .addInterceptor { chain ->
            var request = chain.request()

            request = if (hasNetwork(context)!!)
                request.newBuilder().header("Cache-Control", "public, max-age=" + 5).build()
            else
                request.newBuilder().header(
                    "Cache-Control",
                    "public, only-if-cached, max-stale=" + 60 * 60 * 24 * 7
                ).build()
            chain.proceed(request)
        }
        .addInterceptor(httpLoggingInterceptor).build()
}

inline fun <reified T> createWebService(okHttpClient: OkHttpClient, url: String): T {
    val retrofit = Retrofit.Builder()
        .baseUrl(url)
        .client(okHttpClient)
        .addConverterFactory(MoshiConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create()).build()
    return retrofit.create(T::class.java)
}
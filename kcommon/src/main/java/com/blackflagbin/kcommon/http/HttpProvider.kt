package com.blackflagbin.kcommon.http

import com.blackflagbin.kcommon.facade.CommonLibrary
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import io.rx_cache2.internal.RxCache
import io.victoralbertos.jolyglot.GsonSpeaker
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * Created by blackflagbin on 2017/9/11.
 */

class HttpProvider private constructor() {

    fun <P> provideApiService(
            baseUrl: String = CommonLibrary.instance.baseUrl,
            headerMap: Map<String, String>? = CommonLibrary.instance.headerMap): P {

        return Retrofit.Builder().client(provideOkHttpClient(headerMap)).baseUrl(baseUrl).addConverterFactory(
                GsonConverterFactory.create()).addCallAdapterFactory(
                RxJava2CallAdapterFactory.create()).build().create(CommonLibrary.instance.apiClass) as P
    }

    fun <T> provideCacheService(): T {
        return RxCache.Builder().persistence(
                CommonLibrary.instance.context.cacheDir, GsonSpeaker()).using(
                CommonLibrary.instance.cacheClass) as T
    }

    private fun provideOkHttpClient(headerMap: Map<String, String>? = CommonLibrary.instance.headerMap): OkHttpClient {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.level = when (CommonLibrary.instance.isDebug) {
            true -> HttpLoggingInterceptor.Level.BODY
            else -> HttpLoggingInterceptor.Level.NONE
        }
        return OkHttpClient.Builder().connectTimeout(
                DEFAULT_MILLISECONDS, TimeUnit.SECONDS).readTimeout(
                DEFAULT_MILLISECONDS, TimeUnit.SECONDS).addInterceptor { chain ->
            val builder = chain.request().newBuilder()
            headerMap?.forEach {
                builder.addHeader(it.key, it.value)
            }
            val request = builder.build()
            chain.proceed(request)
        }.addInterceptor(httpLoggingInterceptor).build()
    }

    private object InnerClass {
        internal var instance = HttpProvider()
    }

    companion object {

        //默认超时时间
        const val DEFAULT_MILLISECONDS: Long = 10

        val instance: HttpProvider
            get() = InnerClass.instance
    }

}

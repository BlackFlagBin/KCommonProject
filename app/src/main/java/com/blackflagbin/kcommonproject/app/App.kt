package com.blackflagbin.kcommonproject.app

import android.app.Application
import android.content.Context
import android.support.multidex.MultiDex
import com.blackflagbin.kcommon.facade.CommonLibrary
import com.blackflagbin.kcommonproject.common.http.ApiService
import com.blackflagbin.kcommonproject.common.http.CacheService

/**
 * Created by blackflagbin on 2018/3/19.
 */
class App : Application() {

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }

    override fun onCreate() {
        super.onCreate()
        CommonLibrary.instance.initLibrary(
                this,
                "http://gank.io",
                ApiService::class.java,
                CacheService::class.java,
                headerMap = hashMapOf(),
                errorHandleMap = hashMapOf())
    }
}
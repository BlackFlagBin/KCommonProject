package com.blackflagbin.kcommonproject.app

import android.app.Application
import android.content.Context
import android.content.Intent
import android.support.multidex.MultiDex
import com.blackflagbin.kcommon.entity.net.IApiException
import com.blackflagbin.kcommon.facade.CommonLibrary
import com.blackflagbin.kcommonproject.BuildConfig
import com.blackflagbin.kcommonproject.common.http.ApiService
import com.blackflagbin.kcommonproject.common.http.CacheService
import com.blankj.utilcode.util.SPUtils
import com.squareup.leakcanary.LeakCanary

/**
 * Created by blackflagbin on 2018/3/19.
 */
class App : Application() {

    companion object {
        fun startLoginActivity(context: Context, loginClazz: Class<*>) {
            CommonLibrary.instance.headerMap = hashMapOf(
                    "token" to SPUtils.getInstance("KCommonDemo").getString("token", "123"))
            context.startActivity(
                    Intent(
                            context,
                            loginClazz).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK))
        }
    }

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)
        MultiDex.install(this)

    }

    override fun onCreate() {
        super.onCreate()
        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }
        LeakCanary.install(this);
        CommonLibrary.instance.initLibrary(this,
                BuildConfig.APP_URL,
                ApiService::class.java,
                CacheService::class.java,
                spName = "KCommonDemo",
                errorHandleMap = hashMapOf<Int, (exception: IApiException) -> Unit>(401 to { exception ->

                }, 402 to { exception ->

                }, 403 to { exception ->

                }),
                isDebug = BuildConfig.DEBUG)
    }
}
package com.blackflagbin.kcommon.facade

import android.app.Application
import android.content.Context
import com.blackflagbin.kcommon.entity.net.IApiException
import com.blackflagbin.kcommon.listener.OnPageCreateListener
import com.blackflagbin.kcommon.listener.OnPageDestroyListener
import com.blackflagbin.kcommon.listener.OnPagePauseListener
import com.blackflagbin.kcommon.listener.OnPageResumeListener
import com.blankj.utilcode.util.Utils

class CommonLibrary private constructor() {

    lateinit var context: Context
    lateinit var baseUrl: String
    lateinit var apiClass: Class<*>
    lateinit var cacheClass: Class<*>
    var spName: String = "kcommon"
    var isDebug: Boolean = true
    var startPage: Int = 1
    var pageSize: Int = 20
    var headerMap: Map<String, String>? = null
    var errorHandleMap: Map<Int, (exception: IApiException) -> Unit>? = null
    var onPageCreateListener: OnPageCreateListener? = null
    var onPageDestroyListener: OnPageDestroyListener? = null
    var onPageResumeListener: OnPageResumeListener? = null
    var onPagePauseListener: OnPagePauseListener? = null


    /**
     * 初始化
     *
     * @param context Application
     * @param baseUrl retrofit所需的baseUrl
     * @param apiClass retrofit使用的ApisService::Class.java
     * @param cacheClass rxcache使用的CacheService::Class.java
     * @param spName Sharedpreference文件名称
     * @param isDebug 是debug环境还是release环境。debug环境有网络请求的日志，release反之
     * @param startPage 分页列表的起始页，有可能是0，或者是2，这个看后台
     * @param pageSize 分页大小
     * @param headerMap 网络请求头的map集合，便于在网络请求添加统一的请求头，比如token之类
     * @param errorHandleMap 错误处理的map集合，便于针对相关网络请求返回的错误码来做相应的处理，比如错误码401，token失效需要重新登录
     * @param onPageCreateListener 对应页面activity或fragment相关生命周期的回调，便于在页面相关时机做一些统一处理，比如加入友盟统计需要在所有页面的相关生命周期加入一些处理
     * @param onPageDestroyListener 对应页面activity或fragment相关生命周期的回调，便于在页面相关时机做一些统一处理，比如加入友盟统计需要在所有页面的相关生命周期加入一些处理
     * @param onPageResumeListener 对应页面activity或fragment相关生命周期的回调，便于在页面相关时机做一些统一处理，比如加入友盟统计需要在所有页面的相关生命周期加入一些处理
     * @param onPagePauseListener 对应页面activity或fragment相关生命周期的回调，便于在页面相关时机做一些统一处理，比如加入友盟统计需要在所有页面的相关生命周期加入一些处理
     *
     */
    fun initLibrary(
            context: Application,
            baseUrl: String,
            apiClass: Class<*>,
            cacheClass: Class<*>,
            spName: String = "kcommon",
            isDebug: Boolean = true,
            startPage: Int = 1,
            pageSize: Int = 20,
            headerMap: Map<String, String>? = null,
            errorHandleMap: Map<Int, (exception: IApiException) -> Unit>? = null,
            onPageCreateListener: OnPageCreateListener? = null,
            onPageDestroyListener: OnPageDestroyListener? = null,
            onPageResumeListener: OnPageResumeListener? = null,
            onPagePauseListener: OnPagePauseListener? = null) {
        this.context = context
        Utils.init(context)
        this.baseUrl = baseUrl
        this.apiClass = apiClass
        this.cacheClass = cacheClass
        this.spName = spName
        this.isDebug = isDebug
        this.startPage = startPage
        this.pageSize = pageSize
        this.headerMap = headerMap
        this.errorHandleMap = errorHandleMap
        this.onPageCreateListener = onPageCreateListener
        this.onPageDestroyListener = onPageDestroyListener
        this.onPageResumeListener = onPageResumeListener
        this.onPagePauseListener = onPagePauseListener
    }


    private object InnerClass {
        internal var instance = CommonLibrary()
    }

    companion object {
        val instance: CommonLibrary
            get() = InnerClass.instance
    }


}

package com.blackflagbin.kcommon.facade

import android.app.Application
import android.content.Context
import com.blackflagbin.kcommon.listener.*
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
    var errorHandleMap: Map<Int, ErrorHandleCallBack>? = null
    var onPageCreateListener: OnPageCreateListener? = null
    var onPageDestroyListener: OnPageDestroyListener? = null
    var onPageResumeListener: OnPageResumeListener? = null
    var onPagePauseListener: OnPagePauseListener? = null

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
            errorHandleMap: Map<Int, ErrorHandleCallBack>? = null,
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

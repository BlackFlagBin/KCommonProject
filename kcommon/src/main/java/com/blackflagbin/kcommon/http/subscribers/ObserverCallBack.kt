package com.blackflagbin.kcommon.http.subscribers

interface ObserverCallBack<in T> {
    fun onNext(t: T)

    fun onError(e: Throwable)

}

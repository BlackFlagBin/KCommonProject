package com.blackflagbin.kcommon.http.subscribers


import com.blackflagbin.kcommon.base.IBaseRefreshAndLoadMoreView
import com.blackflagbin.kcommon.base.IBaseView
import com.blackflagbin.kcommon.base.ILoadMoreData
import com.blackflagbin.kcommon.http.ErrorHandler

import io.reactivex.observers.ResourceObserver

class NoProgressObserver<T,V>(
        private val mBaseView: IBaseView<V>,
        private val mCallBack: ObserverCallBack<T> = object : ObserverCallBack<T> {
            override fun onNext(t: T) {
            }

            override fun onError(e: Throwable) {
            }

        },
        private val mIsLoadMore: Boolean = false) : ResourceObserver<T>() {

    override fun onNext(t: T) {
        if (mIsLoadMore) {
            if (mBaseView is IBaseRefreshAndLoadMoreView) {
                mBaseView.afterLoadMore(t as V)
            }
        }
        mCallBack.onNext(t)
    }

    override fun onError(e: Throwable) {
        ErrorHandler.handleError(e, mBaseView)
        if (mIsLoadMore) {
            if (mBaseView is IBaseRefreshAndLoadMoreView<*>) {
                mBaseView.afterLoadMoreError(e)
            }
        } else {
            mCallBack.onError(e)
        }

    }

    override fun onComplete() {

    }


}
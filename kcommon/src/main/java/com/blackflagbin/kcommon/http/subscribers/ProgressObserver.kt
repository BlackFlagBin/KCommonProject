package com.blackflagbin.kcommon.http.subscribers

import android.app.Activity
import android.content.Context
import android.support.v4.app.Fragment

import com.blackflagbin.kcommon.base.IBaseRefreshAndLoadMoreView
import com.blackflagbin.kcommon.base.IBaseView
import com.blackflagbin.kcommon.base.ILoadMoreData
import com.blackflagbin.kcommon.http.ErrorHandler
import com.blackflagbin.kcommon.http.progress.ProgressCancelListener
import com.blackflagbin.kcommon.http.progress.ProgressDialogHandler

import io.reactivex.observers.ResourceObserver

class ProgressObserver<T>(
        private val mBaseView: IBaseView<T>,
        private val mCallBack: ObserverCallBack<T>,
        private val mIsLoadMore: Boolean = false) : ResourceObserver<T>(), ProgressCancelListener {
    private lateinit var mProgressDialogHandler: ProgressDialogHandler

    private lateinit var mContext: Context

    init {
        if (mBaseView is Activity) {
            mContext = mBaseView
        }
        if (mBaseView is Fragment) {
            mContext = (mBaseView as Fragment).activity!!
        }
        mProgressDialogHandler = ProgressDialogHandler(mContext, this, true)
    }

    public override fun onStart() {
        showProgressDialog()
    }

    override fun onNext(t: T) {
        if (mIsLoadMore) {
            if (mBaseView is IBaseRefreshAndLoadMoreView<*>) {
                mBaseView.afterLoadMore((t as ILoadMoreData).list)
            }
        }
        mCallBack.onNext(t)
    }

    override fun onError(e: Throwable) {
        ErrorHandler.handleError(e, mBaseView)
        if (mIsLoadMore) {
            if (mBaseView is IBaseRefreshAndLoadMoreView<*>) {
                mBaseView.showTip("无网络")
                mBaseView.afterLoadMoreError(e)
            }
        } else {
            mCallBack.onError(e)
        }
        dismissProgressDialog()
    }

    override fun onComplete() {
        dismissProgressDialog()
    }

    override fun onCancelProgress() {
        if (!this.isDisposed) {
            this.dispose()
        }
    }

    private fun showProgressDialog() {
        mProgressDialogHandler.obtainMessage(ProgressDialogHandler.SHOW_PROGRESS_DIALOG).sendToTarget()
    }

    private fun dismissProgressDialog() {
        mProgressDialogHandler.obtainMessage(ProgressDialogHandler.DISMISS_PROGRESS_DIALOG).sendToTarget()
    }

}
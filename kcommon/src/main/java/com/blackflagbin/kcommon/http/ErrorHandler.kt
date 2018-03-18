package com.blackflagbin.kcommon.http

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.support.v4.app.Fragment
import android.widget.Toast
import com.blackflagbin.kcommon.base.IBaseView
import com.blackflagbin.kcommon.entity.net.IApiException
import com.blackflagbin.kcommon.facade.CommonLibrary
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

object ErrorHandler {

    fun handleError(e: Throwable, baseView: IBaseView<*>) {
        var context: Activity? = null
        if (baseView is Activity) {
            context = baseView
        }
        if (baseView is Fragment) {
            context = (baseView as Fragment).activity
        }
        if (e is IApiException) {
            val finalContext = context
            Observable.just(1).observeOn(AndroidSchedulers.mainThread()).subscribe {
                Toast.makeText(
                        finalContext, e.message, Toast.LENGTH_SHORT).show()
            }

            val resultCode = (e as IApiException).resultCode
            CommonLibrary.instance.errorHandleMap?.filter { it.key == resultCode }?.get(0)?.handleError(
                    e)
        } else {
            when (e) {
                is SocketTimeoutException -> Observable.just(1).observeOn(AndroidSchedulers.mainThread()).subscribe {
                    baseView.showTip(
                            "网络请求超时，请检查您的网络状态")
                }
                is ConnectException -> Observable.just(1).observeOn(AndroidSchedulers.mainThread()).subscribe {
                    baseView.showTip(
                            "网络中断，请检查您的网络状态")
                }
                is UnknownHostException -> Observable.just(1).observeOn(AndroidSchedulers.mainThread()).subscribe {
                    baseView.showTip(
                            "网络异常，请检查您的网络状态")
                }
                else -> {
                    Observable.just(1).observeOn(AndroidSchedulers.mainThread()).subscribe {
                        baseView.showTip(
                                "服务器出小差了")
                    }
                    e.printStackTrace()
                }
            }
        }
    }

    /**
     * 跳转到登录页面，同时清空之前的任务栈
     *
     * @param context    context
     * @param loginClazz 登录页面Activity的Class类
     */
    private fun startLoginActivity(context: Context, loginClazz: Class<*>) {
        context.startActivity(
                Intent(
                        context,
                        loginClazz).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK))
    }
}

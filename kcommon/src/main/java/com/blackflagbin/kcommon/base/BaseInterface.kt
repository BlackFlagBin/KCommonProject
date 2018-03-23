package com.blackflagbin.kcommon.base

import android.os.Bundle
import org.jetbrains.anko.bundleOf


interface IBaseApiService

interface IBasePresenter {
    fun initData(dataMap: Map<String, String>?)
}

interface IBaseRefreshAndLoadMorePresenter : IBasePresenter {
    fun initData(dataMap: Map<String, String>?, pageNo: Int)
}

interface IBaseView<in T> {
    fun startActivity(claz: Class<*>, bundle: Bundle? = bundleOf())

    fun finishActivity()

    fun showTip(tipMsg: String)

    fun showLoading()

    fun dismissLoading()

    /**
     * 数据加载完毕后设置view
     *
     * @param data 从网络或本地获取的数据，通常不会是一个接口返回的数据，我们可以把多个接口返回的数据封装成一个包含多种数据的数据类
     */
    fun showSuccessView(data: T)

    fun showEmptyView()

    fun showErrorView(errorMsg: String)
}

interface ILoadMoreData {
    val list: List<*>
}

interface IBaseRefreshAndLoadMoreView<T> : IBaseView<T> {
    fun beforeInitData()
    fun afterLoadMore(data: T)
    fun afterLoadMoreError(e: Throwable)

}

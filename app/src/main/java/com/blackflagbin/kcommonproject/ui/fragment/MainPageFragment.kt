package com.blackflagbin.kcommonproject.ui.fragment

import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.RecyclerView
import com.blackflagbin.kcommon.base.BaseRefreshAndLoadMoreFragment
import com.blackflagbin.kcommon.base.ILoadMoreData
import com.blackflagbin.kcommonproject.R
import com.blackflagbin.kcommonproject.common.http.ApiService
import com.blackflagbin.kcommonproject.common.http.CacheService
import com.blackflagbin.kcommonproject.mvp.contract.MainPageContract
import com.blackflagbin.kcommonproject.mvp.presenter.MainPagePresenter
import com.blackflagbin.kcommonproject.ui.adapter.MainPageAdapter
import com.chad.library.adapter.base.BaseQuickAdapter
import com.kennyc.view.MultiStateView

/**
 * Created by blackflagbin on 2018/3/20.
 */
class MainPageFragment :
        BaseRefreshAndLoadMoreFragment<ApiService, CacheService, MainPageContract.IMainPagePresenter, ILoadMoreData>(),
        MainPageContract.IMainPageView {
    override val adapter: BaseQuickAdapter<*, *>
        get() = MainPageAdapter(arrayListOf())

    override val recyclerView: RecyclerView?
        get() = null

    override val layoutManager: RecyclerView.LayoutManager?
        get() = null

    override val swipeRefreshView: SwipeRefreshLayout?
        get() = null

    override val multiStateView: MultiStateView?
        get() = null

    override val layoutResId: Int
        get() = R.layout.activity_main

    override val presenter: MainPageContract.IMainPagePresenter
        get() = MainPagePresenter(this)

    override fun initData() {
    }

    override fun showContentView(data: ILoadMoreData) {
    }
}
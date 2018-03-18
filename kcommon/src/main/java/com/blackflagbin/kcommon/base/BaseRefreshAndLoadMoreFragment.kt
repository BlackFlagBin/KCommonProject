package com.blackflagbin.kcommon.base

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import com.blackflagbin.kcommon.R
import com.blackflagbin.kcommon.facade.CommonLibrary
import com.blackflagbin.kcommon.widget.CustomLoadMoreView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.kennyc.view.MultiStateView

/**
 * Created by blackflagbin on 2017/9/22.
 */

abstract class BaseRefreshAndLoadMoreFragment<out A, out C, P : IBaseRefreshAndLoadMorePresenter, in D : ILoadMoreData> :
        BaseFragment<A, C, P, D>(), BaseQuickAdapter.RequestLoadMoreListener,
        IBaseRefreshAndLoadMoreView<D> {
    protected var mAdapter: BaseQuickAdapter<*, *>? = null
    private var mIsLoadComplete = false
    private var mCurPage = CommonLibrary.instance.startPage
    private val PAGE_SIZE by lazy { CommonLibrary.instance.pageSize }
    private lateinit var mRecyclerView: RecyclerView
    private lateinit var mLayoutManager: RecyclerView.LayoutManager

    protected val emptyLayoutId: Int
        get() = R.layout.layout_empty

    protected abstract val adapter: BaseQuickAdapter<*, *>

    protected abstract val recyclerView: RecyclerView


    protected abstract val layoutManager: RecyclerView.LayoutManager

    override fun initView() {
        super.initView()
        mRecyclerView = recyclerView
        mLayoutManager = layoutManager
        mRecyclerView.layoutManager = mLayoutManager
        mAdapter = adapter
        val noDataView = layoutInflater.inflate(
                emptyLayoutId, mRecyclerView.parent as ViewGroup, false)
        mAdapter?.emptyView = noDataView
        mAdapter?.setLoadMoreView(CustomLoadMoreView())
        mAdapter?.setOnLoadMoreListener(this, mRecyclerView)
        mRecyclerView.adapter = mAdapter
        mAdapter?.disableLoadMoreIfNotFullPage()
    }

    override fun showSuccessView(data: D) {
        mMultiStateView?.viewState = MultiStateView.VIEW_STATE_CONTENT
        mAdapter?.setNewData(data.list)
        showContentView(data)
    }

    override fun beforeInitData() {
        mAdapter?.setEnableLoadMore(false)
        mIsLoadComplete = false
        mCurPage = 1
    }

    override fun afterLoadMore(data: List<Nothing>) {
        mIsLoadComplete = data.size < PAGE_SIZE
        mAdapter?.addData(data)
        mCurPage++
        mAdapter?.loadMoreComplete()
        mSwipeRefresh?.isEnabled = true
    }

    override fun afterLoadMoreError(e: Throwable) {
        mAdapter?.loadMoreFail()
        mSwipeRefresh?.isEnabled = true
    }

    override fun onLoadMoreRequested() {
        mSwipeRefresh?.isEnabled = false
        mAdapter?.let {
            if (it.data.size < PAGE_SIZE) {
                it.loadMoreEnd(false)
                mSwipeRefresh?.isEnabled = true
            } else {
                if (mIsLoadComplete) {
                    it.loadMoreEnd()
                    mSwipeRefresh?.isEnabled = true
                } else {
                    mPresenter.initData(mDataMap, mCurPage + 1)
                }
            }
        }
    }

}

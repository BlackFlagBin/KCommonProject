package com.blackflagbin.kcommon.base

import android.content.Intent
import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.blackflagbin.kcommon.R
import com.blackflagbin.kcommon.facade.CommonLibrary
import com.blackflagbin.kcommon.http.HttpProvider
import com.blankj.utilcode.util.SPUtils
import com.kennyc.view.MultiStateView
import com.trello.rxlifecycle2.components.support.RxFragment
import org.jetbrains.anko.find
import org.jetbrains.anko.sdk25.coroutines.onClick
import org.jetbrains.anko.support.v4.toast

/**
 * Created by blackflagbin on 2017/6/28.
 */

abstract class BaseFragment<out A, out C, P : IBasePresenter, in D> : RxFragment(), IBaseView<D>,
        SwipeRefreshLayout.OnRefreshListener {


    protected lateinit var mPresenter: P
    protected val mSPUtils: SPUtils by lazy { SPUtils.getInstance(CommonLibrary.instance.spName) }
    protected val mApiService: A by lazy { HttpProvider.instance.provideApiService<A>() }
    protected val mCacheService: C by lazy { HttpProvider.instance.provideCacheService<C>() }
    protected val mDataMap: HashMap<String, String> by lazy { hashMapOf<String, String>() }
    protected var mSwipeRefresh: SwipeRefreshLayout? = null
    protected var mMultiStateView: MultiStateView? = null
    protected var mErrorView: View? = null
    protected var mTvErrorMsg: TextView? = null
    protected var mBtErrorRetry: Button? = null
    protected var mEmptyView: View? = null
    protected var mTvEmptyMsg: TextView? = null
    protected var mBtEmptyRetry: Button? = null
    protected lateinit var mRootView: View

    private var isWaitingForOnFragmentResume = false

    protected abstract val swipeRefreshView: SwipeRefreshLayout

    protected abstract val multiStateView: MultiStateView

    protected abstract val layoutResId: Int

    protected abstract val presenter: P

    override fun onResume() {
        super.onResume()
        if (isWaitingForOnFragmentResume) {
            isWaitingForOnFragmentResume = false
            onFragmentVisible()
        }
        activity?.let {
            CommonLibrary.instance.onPageResumeListener?.onPageResume(it)
        }
    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mRootView = inflater.inflate(layoutResId, container, false)
        mPresenter = presenter
        return mRootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity?.let {
            CommonLibrary.instance.onPageCreateListener?.onPageCreate(it)
        }
        initView()
        initData()
    }

    override fun onPause() {
        super.onPause()
        activity?.let {
            CommonLibrary.instance.onPagePauseListener?.onPagePause(it)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        activity?.let {
            CommonLibrary.instance.onPageDestroyListener?.onPageDestroy(it)
        }
    }

    override fun onRefresh() {
        mPresenter.initData(mDataMap)
    }

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        if (hidden) {
            onFragmentHide()
        } else {
            onFragmentShow()
        }
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)

        if (isVisibleToUser) {
            if (isResumed) {
                onFragmentVisible()
            } else {
                isWaitingForOnFragmentResume = true
            }

        } else {
            if (isResumed) {
                onFragmentInvisible()
            }
        }
    }

    protected open fun initView() {
        mPresenter = presenter
        mSwipeRefresh = swipeRefreshView
        mSwipeRefresh?.setOnRefreshListener(this)
        mMultiStateView = multiStateView
        mMultiStateView?.let {
            mEmptyView = it.getView(MultiStateView.VIEW_STATE_EMPTY)
            mTvEmptyMsg = mEmptyView?.find(R.id.tv_empty_msg)
            mBtEmptyRetry = mEmptyView?.find(R.id.bt_retry)
            mBtEmptyRetry?.onClick {
                mMultiStateView?.viewState = MultiStateView.VIEW_STATE_LOADING
                mPresenter.initData(mDataMap)
            }

            mErrorView = it.getView(MultiStateView.VIEW_STATE_ERROR)
            mTvErrorMsg = mErrorView?.find(R.id.tv_error_msg)
            mBtErrorRetry = mErrorView?.find(R.id.bt_retry)
            mBtErrorRetry?.onClick {
                mMultiStateView?.viewState = MultiStateView.VIEW_STATE_LOADING
                mPresenter.initData(mDataMap)
            }
        }
    }

    override fun startActivity(claz: Class<*>, bundle: Bundle?) {
        val intent = Intent(activity, claz)
        if (bundle != null) {
            intent.putExtras(bundle)
        }
        startActivity(intent)
    }

    override fun finishActivity() {
        activity?.finish()
    }

    override fun showTip(tipMsg: String) {
        toast(tipMsg)
    }

    override fun showLoading() {
        mSwipeRefresh?.isRefreshing = true
    }

    override fun dismissLoading() {
        mSwipeRefresh?.isRefreshing = false
    }

    override fun showSuccessView(data: D) {
        mMultiStateView?.viewState = MultiStateView.VIEW_STATE_CONTENT
        showContentView(data)
    }

    override fun showEmptyView() {
        mMultiStateView?.viewState = MultiStateView.VIEW_STATE_EMPTY
    }

    override fun showErrorView(errorMsg: String) {
        mMultiStateView?.viewState = MultiStateView.VIEW_STATE_ERROR
    }

    // FragmentTransaction 调用show 回调
    protected open fun onFragmentShow() {

    }

    // FragmentTransaction 调用hide 回调
    protected open fun onFragmentHide() {}

    /**
     * viewPager中界面每次可见调用；
     */
    protected open fun onFragmentVisible() {}

    /**
     * viewPager中界面每次不可见调用；
     */
    protected open fun onFragmentInvisible() {}

    protected abstract fun initData()

    protected abstract fun showContentView(data: D)
}

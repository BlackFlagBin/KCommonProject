package com.blackflagbin.kcommonproject.ui.fragment

import android.annotation.SuppressLint
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.RecyclerView
import com.blackflagbin.kcommon.base.BaseRefreshAndLoadMoreFragment
import com.blackflagbin.kcommon.entity.net.Optional
import com.blackflagbin.kcommon.widget.FixedLinearLayoutManager
import com.blackflagbin.kcommonproject.R
import com.blackflagbin.kcommonproject.common.entity.net.DataItem
import com.blackflagbin.kcommonproject.common.http.ApiService
import com.blackflagbin.kcommonproject.common.http.CacheService
import com.blackflagbin.kcommonproject.mvp.contract.MainPageContract
import com.blackflagbin.kcommonproject.mvp.presenter.MainPagePresenter
import com.blackflagbin.kcommonproject.ui.activity.WebActivity
import com.blackflagbin.kcommonproject.ui.adapter.listadapter.MainPageAdapter
import com.chad.library.adapter.base.BaseQuickAdapter
import com.kennyc.view.MultiStateView
import kotlinx.android.synthetic.main.fragment_main_page.*
import org.jetbrains.anko.bundleOf

/**
 * Created by blackflagbin on 2018/3/20.
 */
@SuppressLint("ValidFragment")
class MainPageFragment() :
        BaseRefreshAndLoadMoreFragment<ApiService, CacheService, MainPageContract.IMainPagePresenter, Optional<List<DataItem>>>(),
        MainPageContract.IMainPageView {
    private val mTypeArray: Array<String> by lazy {
        arrayOf("all", "Android", "iOS", "休息视频", "福利", "拓展资源", "前端", "瞎推荐", "App")
    }

    private lateinit var mType: String

    override val adapter: BaseQuickAdapter<*, *>?
        get() = MainPageAdapter(arrayListOf())

    override val recyclerView: RecyclerView?
        get() = rv_list

    override val layoutManager: RecyclerView.LayoutManager?
        get() = FixedLinearLayoutManager(activity)

    override val swipeRefreshView: SwipeRefreshLayout?
        get() = swipe_refresh

    override val multiStateView: MultiStateView?
        get() = multi_state_view

    override val layoutResId: Int
        get() = R.layout.fragment_main_page

    override val presenter: MainPageContract.IMainPagePresenter
        get() = MainPagePresenter(this)

    constructor(position: Int) : this() {
        mType = mTypeArray[position]
    }

    override fun initData() {
        mDataMap["type"] = mType
        mPresenter.initData(mDataMap)
    }

    override fun showContentView(data: Optional<List<DataItem>>) {
        mAdapter?.onItemClickListener = BaseQuickAdapter.OnItemClickListener { adapter, view, position ->
            startActivity(
                    WebActivity::class.java,
                    bundleOf(
                            "url" to (mAdapter?.data!![position] as DataItem).url,
                            "title" to (mAdapter?.data!![position] as DataItem).desc))
        }
    }
}
package com.blackflagbin.kcommon.widget

import com.blackflagbin.kcommon.R
import com.chad.library.adapter.base.loadmore.LoadMoreView

class CustomLoadMoreView : LoadMoreView() {

    /**
     * 如果返回true，数据全部加载完毕后会隐藏加载更多
     * 如果返回false，数据全部加载完毕后会显示getLoadEndViewId()布局
     */
    override fun isLoadEndGone(): Boolean {
        return true
    }

    override fun getLayoutId(): Int {
        return R.layout.quick_view_load_more
    }

    override fun getLoadingViewId(): Int {
        return R.id.load_more_loading_view
    }

    override fun getLoadFailViewId(): Int {
        return R.id.load_more_load_fail_view
    }

    /**
     * isLoadEndGone()为true，可以返回0
     * isLoadEndGone()为false，不能返回0
     */
    override fun getLoadEndViewId(): Int {
        return 0
    }
}

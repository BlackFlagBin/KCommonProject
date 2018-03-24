package com.blackflagbin.kcommonproject.mvp.contract

import com.blackflagbin.kcommon.base.IBaseRefreshAndLoadMorePresenter
import com.blackflagbin.kcommon.base.IBaseRefreshAndLoadMoreView
import com.blackflagbin.kcommonproject.common.entity.net.DataItem
import io.reactivex.Observable

/**
 * Created by blackflagbin on 2018/3/20.
 */
interface MainPageContract {
    interface IMainPageModel {
        fun getData(type: String, pageNo: Int, limit: Int): Observable<List<DataItem>>
    }

    interface IMainPagePresenter : IBaseRefreshAndLoadMorePresenter

    interface IMainPageView : IBaseRefreshAndLoadMoreView<List<DataItem>>
}
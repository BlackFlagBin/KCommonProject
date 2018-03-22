package com.blackflagbin.kcommonproject.mvp.contract

import com.blackflagbin.kcommon.base.*
import com.blackflagbin.kcommonproject.common.entity.net.DataItem
import io.reactivex.Observable

/**
 * Created by blackflagbin on 2018/3/20.
 */
interface WebContract {
    interface IWebModel

    interface IWebPresenter : IBasePresenter

    interface IWebView : IBaseView<Any?>
}
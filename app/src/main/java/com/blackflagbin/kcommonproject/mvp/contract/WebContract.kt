package com.blackflagbin.kcommonproject.mvp.contract

import com.blackflagbin.kcommon.base.IBasePresenter
import com.blackflagbin.kcommon.base.IBaseView

/**
 * Created by blackflagbin on 2018/3/20.
 */
interface WebContract {
    interface IWebModel

    interface IWebPresenter : IBasePresenter

    interface IWebView : IBaseView<Any?>
}
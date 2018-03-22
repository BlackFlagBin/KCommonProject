package com.blackflagbin.kcommonproject.mvp.presenter

import com.blackflagbin.kcommon.base.BasePresenter
import com.blackflagbin.kcommonproject.mvp.contract.WebContract
import com.blackflagbin.kcommonproject.mvp.model.WebModel

class WebPresenter(iWebView: WebContract.IWebView) :
        BasePresenter<WebContract.IWebModel, WebContract.IWebView>(iWebView),
        WebContract.IWebPresenter {
    override val model: WebContract.IWebModel
        get() = WebModel()

    override fun initData(dataMap: Map<String, String>?) {
    }

}


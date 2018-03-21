package com.blackflagbin.kcommonproject.mvp.presenter

import com.blackflagbin.kcommon.base.BasePresenter
import com.blackflagbin.kcommonproject.mvp.contract.MainContract
import com.blackflagbin.kcommonproject.mvp.model.MainModel

class MainPresenter(iMainView: MainContract.IMainView) :
        BasePresenter<MainContract.IMainModel, MainContract.IMainView>(iMainView),
        MainContract.IMainPresenter {
    override val model: MainContract.IMainModel
        get() = MainModel()

    override fun initData(dataMap: Map<String, String>?) {
    }

}


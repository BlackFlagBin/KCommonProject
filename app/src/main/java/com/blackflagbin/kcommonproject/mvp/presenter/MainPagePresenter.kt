package com.blackflagbin.kcommonproject.mvp.presenter

import com.blackflagbin.kcommon.base.BasePresenter
import com.blackflagbin.kcommon.facade.CommonLibrary
import com.blackflagbin.kcommonproject.mvp.contract.MainPageContract
import com.blackflagbin.kcommonproject.mvp.model.MainPageModel

class MainPagePresenter(iMainPageView: MainPageContract.IMainPageView) :
        BasePresenter<MainPageContract.IMainPageModel, MainPageContract.IMainPageView>(iMainPageView),
        MainPageContract.IMainPagePresenter {
    override val model: MainPageContract.IMainPageModel
        get() = MainPageModel()

    override fun initData(dataMap: Map<String, String>?) {
        initData(dataMap, CommonLibrary.instance.startPage)
    }

    override fun initData(dataMap: Map<String, String>?, pageNo: Int) {
        if (pageNo == CommonLibrary.instance.startPage) {

        }else{

        }
    }
}


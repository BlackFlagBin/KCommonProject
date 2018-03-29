package com.blackflagbin.kcommonproject.mvp.model

import com.blackflagbin.kcommon.base.BaseModel
import com.blackflagbin.kcommon.entity.net.Optional
import com.blackflagbin.kcommon.http.transformer.DefaultTransformer
import com.blackflagbin.kcommonproject.common.entity.net.DataItem
import com.blackflagbin.kcommonproject.common.http.ApiService
import com.blackflagbin.kcommonproject.common.http.CacheService
import com.blackflagbin.kcommonproject.mvp.contract.MainPageContract
import com.blankj.utilcode.util.NetworkUtils
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.rx_cache2.DynamicKeyGroup
import io.rx_cache2.EvictDynamicKeyGroup

class MainPageModel : BaseModel<ApiService, CacheService>(), MainPageContract.IMainPageModel {
    override fun getData(type: String, pageNo: Int, limit: Int): Observable<Optional<List<DataItem>>> {
        return if (NetworkUtils.isConnected()) {
            mCacheService.getMainDataList(
                    mApiService.getMainDataList(
                            type, limit, pageNo).compose(DefaultTransformer()),
                    DynamicKeyGroup(type, pageNo),
                    EvictDynamicKeyGroup(true)).subscribeOn(Schedulers.io()).observeOn(
                    AndroidSchedulers.mainThread())
        } else {
            mCacheService.getMainDataList(
                    mApiService.getMainDataList(
                            type, limit, pageNo).compose(DefaultTransformer()),
                    DynamicKeyGroup(type, pageNo),
                    EvictDynamicKeyGroup(false)).subscribeOn(Schedulers.io()).observeOn(
                    AndroidSchedulers.mainThread())
        }
    }
}

package com.blackflagbin.kcommonproject.mvp.model

import com.blackflagbin.kcommon.base.BaseModel
import com.blackflagbin.kcommon.http.transformer.DefaultTransformer
import com.blackflagbin.kcommonproject.common.entity.net.DataItem
import com.blackflagbin.kcommonproject.common.http.ApiService
import com.blackflagbin.kcommonproject.common.http.CacheService
import com.blackflagbin.kcommonproject.mvp.contract.MainPageContract
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.rx_cache2.DynamicKeyGroup

class MainPageModel : BaseModel<ApiService, CacheService>(), MainPageContract.IMainPageModel {
    override fun getData(type: String, pageNo: Int, limit: Int): Observable<List<DataItem>> {
        return mCacheService.getMainDataList(
                mApiService.getMainDataList(
                        type, limit, pageNo).compose(DefaultTransformer<List<DataItem>>()),
                DynamicKeyGroup(type, pageNo)).subscribeOn(Schedulers.io()).observeOn(
                AndroidSchedulers.mainThread())
    }
}

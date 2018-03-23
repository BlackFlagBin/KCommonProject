package com.blackflagbin.kcommonproject.common.http

import com.blackflagbin.kcommonproject.common.entity.net.DataItem
import com.blackflagbin.kcommonproject.common.entity.net.HttpResultEntity
import io.reactivex.Observable
import io.rx_cache2.DynamicKey
import io.rx_cache2.DynamicKeyGroup
import io.rx_cache2.LifeCache
import java.util.concurrent.TimeUnit

/**
 * Created by blackflagbin on 2018/3/19.
 */
interface CacheService {
    @LifeCache(duration = 1, timeUnit = TimeUnit.HOURS)
    fun getMainDataList(
            observable: Observable<List<DataItem>>,
            keyGroup: DynamicKeyGroup): Observable<List<DataItem>>
}
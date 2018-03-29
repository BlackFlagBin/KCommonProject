package com.blackflagbin.kcommonproject.common.http

import com.blackflagbin.kcommon.entity.net.Optional
import com.blackflagbin.kcommonproject.common.entity.net.DataItem
import io.reactivex.Observable
import io.rx_cache2.DynamicKeyGroup
import io.rx_cache2.EvictDynamicKeyGroup
import io.rx_cache2.LifeCache
import java.util.concurrent.TimeUnit

/**
 * Created by blackflagbin on 2018/3/19.
 */
interface CacheService {
    @LifeCache(duration = 10, timeUnit = TimeUnit.MINUTES)
    fun getMainDataList(
            observable: Observable<Optional<List<DataItem>>>,
            keyGroup: DynamicKeyGroup,
            evictDynamicKeyGroup: EvictDynamicKeyGroup): Observable<Optional<List<DataItem>>>
}
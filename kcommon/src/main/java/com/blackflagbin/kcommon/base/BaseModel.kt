package com.blackflagbin.kcommon.base

import com.blackflagbin.kcommon.facade.CommonLibrary
import com.blackflagbin.kcommon.http.HttpProvider
import com.blankj.utilcode.util.SPUtils

/**
 * Created by blackflagbin on 2017/9/11.
 */

class BaseModel<A, C> {
    protected var mApiService = HttpProvider.instance.provideApiService<A>()
    protected var mCacheService = HttpProvider.instance.provideCacheService<C>()
    protected var mSPUtils = SPUtils.getInstance(CommonLibrary.instance.spName)
}

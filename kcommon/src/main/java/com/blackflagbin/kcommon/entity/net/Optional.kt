package com.blackflagbin.kcommon.entity.net

import com.blackflagbin.kcommon.base.ILoadMoreData

/**
 * Created by blackflagbin on 2018/3/29.
 * 解决rxjava2不能处理null的问题，我们把所有返回的有效数据包一层Optional，通过isEmpty判断是否为空
 */
data class Optional<T>(var data: T)  {

    fun isEmpty(): Boolean {
        return data == null
    }
}
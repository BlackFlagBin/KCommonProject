package com.blackflagbin.kcommon.listener

import com.blackflagbin.kcommon.entity.net.IApiException

interface ErrorHandleCallBack {
    fun handleError(exception: IApiException)
}
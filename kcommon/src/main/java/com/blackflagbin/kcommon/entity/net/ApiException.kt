package com.blackflagbin.kcommon.entity.net


data class ApiException(override val resultCode: Int, override val msg: String) :
        RuntimeException(), IApiException


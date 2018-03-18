package com.blackflagbin.kcommon.entity.net

interface IApiException {
    val resultCode: Int

    val msg: String
}
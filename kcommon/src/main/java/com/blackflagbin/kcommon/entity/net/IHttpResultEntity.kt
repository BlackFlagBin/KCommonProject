package com.blackflagbin.kcommon.entity.net

/**
 * Created by blackflagbin on 2017/9/25.
 */

interface IHttpResultEntity<T> {
    val isSuccess: Boolean

    val errorCode: Int

    val errorMessage: String

    val result: T
}

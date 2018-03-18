package com.blackflagbin.kcommon.entity.net

/**
 * Created by blackflagbin on 2018/3/15.
 */
interface IHttpResultEntity<out T> {
    val isSuccess: Boolean

    val errorCode: Int

    val errorMessage: String

    val result: T
}
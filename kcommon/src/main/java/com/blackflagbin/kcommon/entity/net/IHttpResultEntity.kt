package com.blackflagbin.kcommon.entity.net

/**
 * Created by blackflagbin on 2017/9/25.
 */

interface IHttpResultEntity<T> {
    //网络请求结果是否成功
    val isSuccess: Boolean

    //错误码
    val errorCode: Int

    //错误信息
    val errorMessage: String

    //返回的有效数据
    val result: T
}

package com.blackflagbin.kcommonproject.common.entity.net

import com.blackflagbin.kcommon.entity.net.IHttpResultEntity
import com.google.gson.annotations.SerializedName


/**
 * Created by blackflagbin on 2018/3/20.
 */

//最外层数据类
data class HttpResultEntity<T>(
        private var code: Int = 0,
        private var message: String = "",
        private var error: Boolean = false,
        private var results: T) : IHttpResultEntity<T> {
    override val isSuccess: Boolean
        get() = !error
    override val errorCode: Int
        get() = code
    override val errorMessage: String
        get() = message
    override val result: T
        get() = results
}


data class DataItem(
		@SerializedName("desc") var desc: String = "",
		@SerializedName("ganhuo_id") var ganhuoId: String = "",
		@SerializedName("publishedAt") var publishedAt: String = "",
		@SerializedName("readability") var readability: String = "",
		@SerializedName("type") var type: String = "",
		@SerializedName("url") var url: String = "",
		@SerializedName("who") var who: String = ""
)

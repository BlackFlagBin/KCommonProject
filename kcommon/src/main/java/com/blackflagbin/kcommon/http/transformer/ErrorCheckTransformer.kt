package com.blackflagbin.kcommon.http.transformer


import com.blackflagbin.kcommon.entity.net.ApiException
import com.blackflagbin.kcommon.entity.net.IHttpResultEntity
import com.blackflagbin.kcommon.entity.net.Optional
import io.reactivex.Observable
import io.reactivex.ObservableTransformer

class ErrorCheckTransformer<T> : ObservableTransformer<IHttpResultEntity<T>, Optional<T>> {

    override fun apply(httpResultObservable: Observable<IHttpResultEntity<T>>): Observable<Optional<T>> {
        return httpResultObservable.map { httpResult ->
            if (!httpResult.isSuccess) {
                throw ApiException(httpResult.errorCode, httpResult.errorMessage)
            }
            //将返回的有效数据包装成Optional,避免rxjava2数据流中不能传递null的问题
            Optional(httpResult.result)
        }
    }
}
